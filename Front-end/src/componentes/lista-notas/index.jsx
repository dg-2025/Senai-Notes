import React from 'react'
import './style.css'
import { Plus } from 'lucide-react'

function ListaNotas({ notas, vizualisarNota, aoCriarNova }) {

  return (
    <div className="lista-notas-container">

      <div className="area-cabecalho-lista">
        <button className="btn-criar" onClick={aoCriarNova}>
          <Plus size={20} />
          Create New Note
        </button>

        <p className="texto-explicativo">
          Suas notas salvas aparecem aqui. Clique para editar.
        </p>
      </div>

      <div className="lista-scroll">

        {notas.length === 0 && (
          <p className="lista-vazia">Nenhuma nota criada.</p>
        )}

        {notas.map((nota) => (
          <div
            key={nota.id}
            className="card-nota"
            onClick={() => vizualisarNota(nota)}
          >
            <div className="area-img-miniatura">
              {nota.imagem ? (
                <img
                  src={nota.imagem}
                  className="miniatura-nota"
                  alt=""
                />
              ) : (
                <div className="miniatura-nota miniatura-sem-imagem" />
              )}
            </div>

            <div className="info-nota">
              <h3 className="titulo-nota">
                {nota.titulo || 'Sem Título'}
              </h3>

              <div className="tags-nota">
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
