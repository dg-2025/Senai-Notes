package com.senai_notes.senai_notes.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ArmazenamentoService {

    private final AmazonS3 s3Client;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    // Injeção de dependência do S3 Client
    public ArmazenamentoService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    // 1. SALVAR ARQUIVO NO S3
    public String salvarArquivo(MultipartFile arquivo) {
        String extensao = arquivo.getOriginalFilename().substring(arquivo.getOriginalFilename().lastIndexOf("."));
        String nomeArquivo = UUID.randomUUID().toString() + extensao; // Nome único

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(arquivo.getSize());
            metadata.setContentType(arquivo.getContentType());

            s3Client.putObject(new PutObjectRequest(
                    bucketName,
                    nomeArquivo,
                    arquivo.getInputStream(), // Stream do arquivo
                    metadata
            ));

            return nomeArquivo; // Retorna o nome do arquivo para salvar no DB

        } catch (IOException e) {
            // Se falhar o upload para o S3, o Spring deve parar
            throw new RuntimeException("Falha ao salvar arquivo no Amazon S3", e);
        }
    }

    // 2. CARREGAR ARQUIVO DO S3 (Retorna a URL pública para o Controller redirecionar)
    public String carregarArquivo(String nomeDoArquivo) {
        // A imagem é publicamente acessível (read-only) via policy no bucket
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, nomeDoArquivo);
    }
}