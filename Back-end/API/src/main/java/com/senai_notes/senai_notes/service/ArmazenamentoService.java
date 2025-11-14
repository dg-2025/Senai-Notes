package com.senai_notes.senai_notes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ArmazenamentoService{

    private final Path localDeArmazenamento;

    public ArmazenamentoService(@Value("${file.upload-dir}") String uploadDir) {
        this.localDeArmazenamento = Paths.get(uploadDir);
        try {
            Files.createDirectories(localDeArmazenamento);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar a pasta de uploads.", e);
        }
    }

    public String salvarArquivo(MultipartFile arquivo) {
        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeUnico = UUID.randomUUID().toString() + extensao;
            Path caminhoDestino = localDeArmazenamento.resolve(nomeUnico);
            try (InputStream inputStream = arquivo.getInputStream()) {
                Files.copy(inputStream, caminhoDestino, StandardCopyOption.REPLACE_EXISTING);
            }
            return nomeUnico;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo.", e);
        }
    }

    public Resource carregarArquivo(String nomeDoArquivo) {
        try {
            Path arquivo = localDeArmazenamento.resolve(nomeDoArquivo);
            Resource resource = new UrlResource(arquivo.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo: " + nomeDoArquivo);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao tentar carregar o arquivo: " + nomeDoArquivo, e);
        }
    }
}
