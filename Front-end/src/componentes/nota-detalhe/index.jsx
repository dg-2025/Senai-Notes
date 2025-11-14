// imports principais
import React, { useState, useEffect, useRef } from 'react'
import './style.css'
import { Tag, Clock, Loader, CheckCircle, Upload } from 'lucide-react'
import AcoesNota from '../acoes-nota'

function NotaDetalhe({ nota, aoSalvar, aoDeletar, aoArquivar }) {

  // estados do conteúdo
  const [titulo, setTitulo] = useState('')
  const [descricao, setDescricao] = useState('')
  const [tagsTexto, setTagsTexto] = useState('')
  const [imagemPreview, setImagemPreview] = useState(null)

  // input de arquivo escondido
  const fileInputRef = useRef(null)

  // carrega dados da nota quando muda
  useEffect(() => {
    if (nota) {
      setTitulo(nota.titulo || '')
      setDescricao(nota.descricao || '')
      setTagsTexto(nota.tags ? nota.tags.join(', ') : '')
      setImagemPreview(nota.imagem || null)
    }
  }, [nota])

  // quando não tem nota selecionada
  if (!nota) {
    return (
      <div className="detalhe-vazio">
        <p>Selecione uma nota ou crie uma nova</p>
      </div>
    )
  }

  // upload de imagem
  const handleImageUpload = (e) => {
    const file = e.target.files[0]
    if (file) {
      const imageUrl = URL.createObjectURL(file)
      setImagemPreview(imageUrl)
    }
  }

  // salvar alterações
  const handleSalvarClick = () => {
    const tagsArray = tagsTexto
      .split(',')
      .map(tag => tag.trim())
      .filter(tag => tag !== "")

    aoSalvar({
      ...nota,
      titulo,
      descricao,
      tags: tagsArray,
      imagem: imagemPreview
    })
  }

  return (
    <div className="detalhe-container">
      <div className="detalhe-grid">

        {/* parte principal da nota */}
        <div className="conteudo-principal">

          {/* capa clicável */}
          <div
            className="capa-nota clicavel"
            onClick={() => fileInputRef.current.click()}
            title="Clique para alterar a imagem"
          >
            {imagemPreview ? (
              <img src={imagemPreview} alt="Capa" />
            ) : (
              <div className="placeholder-upload">
                <Upload size={40} color="#9CA3AF" />
                <span>Click to add cover image</span>
              </div>
            )}

            {/* input escondido */}
            <input
              type="file"
              ref={fileInputRef}
              style={{ display: 'none' }}
              accept="image/*"
              onChange={handleImageUpload}
            />
          </div>

          {/* cabeçalho da nota */}
          <div className="cabecalho-detalhe">

            <input
              className="input-titulo"
              value={titulo}
              onChange={(e) => setTitulo(e.target.value)}
              placeholder="Título da Nota..."
            />

            {/* tabela de informações */}
            <div className="tabela-meta">

              {/* tags */}
              <div className="linha-meta">
                <div className="meta-label">
                  <Tag size={18} />
                  <span>Tags</span>
                </div>

                <div className="meta-valor" style={{ width: '100%' }}>
                  <input
                    className="input-tags"
                    value={tagsTexto}
                    onChange={(e) => setTagsTexto(e.target.value)}
                    placeholder="Ex: praia, viagem, lazer"
                  />
                </div>
              </div>

              {/* status */}
              <div className="linha-meta">
                <div className="meta-label">
                  {nota.arquivado ? <CheckCircle size={18} /> : <Loader size={18} />}
                  <span>Status</span>
                </div>

                <div className="meta-valor">
                  <span className="status-texto">
                    {nota.arquivado ? "Archived" : "Active"}
                  </span>
                </div>
              </div>

              {/* data */}
              <div className="linha-meta">
                <div className="meta-label">
                  <Clock size={18} />
                  <span>Last edited</span>
                </div>

                <div className="meta-valor">{nota.data}</div>
              </div>

            </div>
          </div>

          <hr className="divisor" />

          {/* texto da nota */}
          <div className="corpo-texto">
            <textarea
              className="textarea-descricao"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
              placeholder="Comece a escrever sua nota aqui..."
            />
          </div>

          {/* botões inferior */}
          <div className="botoes-inferiores">
            <button className="btn-primary" onClick={handleSalvarClick}>
              Save Note
            </button>

            <button className="btn-secondary" onClick={() => setTitulo(nota.titulo)}>
              Cancel
            </button>
          </div>
        </div>

        {/* coluna direita (botões de ação) */}
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
