import React from 'react'
import './style.css'
import { Plus, Image as ImageIcon } from 'lucide-react'

function ListaNotas({ notas, vizualisarNota, aoCriarNova }) {

  return (
    <div className="lista-notas-container">

      {/* botão criar e texto introdutório */}
      <div className="area-cabecalho-lista">
        <button className="btn-criar" onClick={aoCriarNova}>
          <Plus size={20} />
          Create New Note
        </button>

        <p className="texto-explicativo">
          Suas notas salvas aparecem aqui. Clique para editar.
        </p>
      </div>

      {/* lista com rolagem */}
      <div className="lista-scroll">

        {/* lista vazia */}
        {notas.length === 0 && (
          <p className="lista-vazia">Nenhuma nota criada.</p>
        )}

        {/* notas */}
        {notas.map((nota) => (
          <div
            key={nota.id}
            className="card-nota"
            onClick={() => vizualisarNota(nota)}
          >

            {/* miniatura da nota */}
            <div className="area-img-miniatura">
              {nota.imagem ? (
                <img src={nota.imagem} alt="" className="miniatura-nota" />
              ) : (
                <div className="miniatura-placeholder">
                  <ImageIcon size={24} color="#ccc" />
                </div>
              )}
            </div>

            {/* texto e informações */}
            <div className="info-nota">

              <h3 className="titulo-nota">
                {nota.titulo || "Sem Título"}
              </h3>

              <div className="tags-nota">
                {/* tags */}
                {nota.tags.map((tag, index) => (
                  <span key={index} className="tag-pill">
                    {tag}
                  </span>
                ))}
              </div>

              <span className="data-nota">{nota.data}</span>
            </div>
          </div>
        ))}

      </div>
    </div>
  )
}

export default ListaNotas
