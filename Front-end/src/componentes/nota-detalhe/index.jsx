import React, { useState, useEffect, useRef } from 'react'
import './style.css'
import { Tag, Clock, Loader, CheckCircle, Upload } from 'lucide-react'
import AcoesNota from '../acoes-nota'

function NotaDetalhe({ nota, aoSalvar, aoDeletar, aoArquivar }) {
  const [titulo, setTitulo] = useState('')
  const [descricao, setDescricao] = useState('')
  const [tagsTexto, setTagsTexto] = useState('')
  const [imagemPreview, setImagemPreview] = useState(null)
  const [arquivoReal, setArquivoReal] = useState(null)

  const fileInputRef = useRef(null)

  useEffect(() => {
    if (nota) {
      setTitulo(nota.titulo || '')
      setDescricao(nota.descricao || '')
      setTagsTexto(nota.tags ? nota.tags.join(', ') : '')
      setImagemPreview(nota.imagem || null) // aqui vem URL do S3 ou null
      setArquivoReal(null)
    }
  }, [nota])

  if (!nota) {
    return (
      <div className="detalhe-vazio">
        <p>Selecione uma nota ou crie uma nova</p>
      </div>
    )
  }

  const handleImageUpload = e => {
    const file = e.target.files[0]
    if (file) {
      const imageUrl = URL.createObjectURL(file)
      setImagemPreview(imageUrl)
      setArquivoReal(file)
    }
  }

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

        <div className="conteudo-principal">

          <div
            className="capa-nota clicavel"
            onClick={() => fileInputRef.current && fileInputRef.current.click()}
            title="Clique para alterar a imagem"
          >
            {imagemPreview ? (
              <img
                src={imagemPreview}
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

            <input
              type="file"
              ref={fileInputRef}
              style={{ display: 'none' }}
              accept="image/*"
              onChange={handleImageUpload}
            />
          </div>

          <div className="cabecalho-detalhe">
            <input
              className="input-titulo"
              value={titulo}
              onChange={e => setTitulo(e.target.value)}
              placeholder="Título da Nota..."
            />

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
                    placeholder="Ex: mercado, estudo, viagem"
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

          <div className="corpo-texto">
            <textarea
              className="textarea-descricao"
              value={descricao}
              onChange={e => setDescricao(e.target.value)}
              placeholder="Comece a escrever sua nota aqui..."
            />
          </div>

          <div className="botoes-inferiores">
            <button className="btn-primary" onClick={handleSalvarClick}>
              Save Note
            </button>

            <button
              className="btn-secondary"
              onClick={() => {
                setTitulo(nota.titulo || '')
                setDescricao(nota.descricao || '')
                setTagsTexto(nota.tags ? nota.tags.join(', ') : '')
                setImagemPreview(nota.imagem || null)
                setArquivoReal(null)
              }}
            >
              Cancel
            </button>
          </div>
        </div>

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
