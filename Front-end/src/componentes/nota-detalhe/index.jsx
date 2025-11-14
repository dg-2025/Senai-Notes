// Importações principais do React
import React, { useState, useEffect, useRef } from 'react'

// Estilos específicos da área de detalhe da nota
import './style.css'

// Ícones utilizados no painel lateral e nos metadados
import { Tag, Clock, Loader, CheckCircle, Upload } from 'lucide-react'

// Componente com botões de arquivar, excluir, etc.
import AcoesNota from '../acoes-nota'

// Componente que exibe imagens com fallback seguro
import ImagemSegura from '../imagem-segura'

function NotaDetalhe({ nota, aoSalvar, aoDeletar, aoArquivar }) {
  // Estados controlando os campos editáveis
  const [titulo, setTitulo] = useState('')
  const [descricao, setDescricao] = useState('')
  const [tagsTexto, setTagsTexto] = useState('')
  const [imagemPreview, setImagemPreview] = useState(null)

  // Guarda o arquivo real selecionado pelo usuário
  const [arquivoReal, setArquivoReal] = useState(null)

  // Referência para o input escondido de upload
  const fileInputRef = useRef(null)

  // Preenche os campos sempre que uma nova nota for selecionada
  useEffect(() => {
    if (nota) {
      setTitulo(nota.titulo || '')
      setDescricao(nota.descricao || '')
      setTagsTexto(nota.tags ? nota.tags.join(', ') : '')
      setImagemPreview(nota.imagem || null)
      setArquivoReal(null)
    }
  }, [nota])

  // Caso nenhuma nota esteja selecionada, mostra mensagem padrão
  if (!nota) {
    return (
      <div className="detalhe-vazio">
        <p>Selecione uma nota ou crie uma nova</p>
      </div>
    )
  }

  // Ao escolher uma imagem, cria preview e guarda o arquivo real
  const handleImageUpload = e => {
    const file = e.target.files[0]
    if (file) {
      const imageUrl = URL.createObjectURL(file)
      setImagemPreview(imageUrl)
      setArquivoReal(file)
    }
  }

  // Quando clica em salvar, envia título, texto, tags e imagem para o React pai
  const handleSalvarClick = () => {
    const tagsArray = tagsTexto
      .split(',')
      .map(tag => tag.trim())
      .filter(tag => tag !== '')

    aoSalvar({
      ...nota,
      titulo,
      descricao,
      tags: tagsArray,
      imagemArquivo: arquivoReal
    })
  }

  return (
    <div className="detalhe-container">
      <div className="detalhe-grid">

        {/* Área principal onde ficam capa, título, texto e metadados */}
        <div className="conteudo-principal">

          {/* Área da imagem, clicável para trocar a capa */}
          <div
            className="capa-nota clicavel"
            onClick={() => fileInputRef.current.click()}
            title="Clique para alterar a imagem"
          >
            {imagemPreview ? (
              <ImagemSegura
                url={imagemPreview}
                alt="Capa"
                style={{
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover'
                }}
              />
            ) : (
              <div className="placeholder-upload">
                <Upload size={40} color="#9CA3AF" />
                <span>Click to add cover image</span>
              </div>
            )}

            {/* Input escondido que recebe o arquivo */}
            <input
              type="file"
              ref={fileInputRef}
              style={{ display: 'none' }}
              accept="image/*"
              onChange={handleImageUpload}
            />
          </div>

          <div className="cabecalho-detalhe">
            {/* Campo do título */}
            <input
              className="input-titulo"
              value={titulo}
              onChange={e => setTitulo(e.target.value)}
              placeholder="Título da Nota..."
            />

            {/* Metadados como tags, status e data */}
            <div className="tabela-meta">

              <div className="linha-meta">
                <div className="meta-label">
                  <Tag size={18} />
                  <span>Tags</span>
                </div>

                <div className="meta-valor" style={{ width: '100%' }}>
                  <input
                    className="input-tags"
                    value={tagsTexto}
                    onChange={e => setTagsTexto(e.target.value)}
                    placeholder="Ex: praia, viagem, lazer"
                  />
                </div>
              </div>

              <div className="linha-meta">
                <div className="meta-label">
                  {nota.arquivado ? (
                    <CheckCircle size={18} />
                  ) : (
                    <Loader size={18} />
                  )}
                  <span>Status</span>
                </div>

                <div className="meta-valor">
                  <span className="status-texto">
                    {nota.arquivado ? 'Archived' : 'Active'}
                  </span>
                </div>
              </div>

              <div className="linha-meta">
                <div className="meta-label">
                  <Clock size={18} />
                  <span>Last edited</span>
                </div>

                <div className="meta-valor">
                  {nota.data || 'Hoje'}
                </div>
              </div>

            </div>
          </div>

          <hr className="divisor" />

          {/* Texto principal da nota */}
          <div className="corpo-texto">
            <textarea
              className="textarea-descricao"
              value={descricao}
              onChange={e => setDescricao(e.target.value)}
              placeholder="Comece a escrever sua nota aqui..."
            />
          </div>

          {/* Botões de ação inferior */}
          <div className="botoes-inferiores">
            <button className="btn-primary" onClick={handleSalvarClick}>
              Save Note
            </button>

            <button
              className="btn-secondary"
              onClick={() => setTitulo(nota.titulo)}
            >
              Cancel
            </button>
          </div>
        </div>

        {/* Coluna da direita com botões de arquivar e deletar */}
        <aside className="coluna-acoes">
          <AcoesNota
            nota={nota}
            aoArquivar={aoArquivar}
            aoDeletar={aoDeletar}
          />
        </aside>

      </div>
    </div>
  )
}

export default NotaDetalhe
