// imports principais
import React, { useState } from 'react'
import './style.css'

// ícones
import { ArrowLeft, Archive, Trash2, Tag, NotebookPen } from 'lucide-react'

// componentes do sistema
import MenuLateral from '../../componentes/menu-lateral'
import CabecalhoTopo from '../../componentes/cabecalho-topo'
import ListaNotas from '../../componentes/lista-notas'
import NotaDetalhe from '../../componentes/nota-detalhe'
import ModalConfirmacao from '../../componentes/modal-confirmacao'
import ToastNotificacao from '../../componentes/toast-notificacao'
import MenuMobile from '../../componentes/menu-mobile'

function TelaNotas() {

  // estados principais da aplicação
  const [notas, setNotas] = useState([
    {
      id: 1,
      titulo: "Bem-vindo ao Senai Notes",
      descricao: "Crie notas, adicione tags e filtre tudo.",
      tags: ["Tutorial", "Senai"],
      data: "Hoje",
      arquivado: false,
      imagem: null
    }
  ])

  // estados de controle
  const [notaSelecionada, setNotaSelecionada] = useState(null)
  const [termoBusca, setTermoBusca] = useState('')
  const [filtroAtivo, setFiltroAtivo] = useState('all') // all, archive, LISTA_TAGS ou NomeTag

  // estados dos modais
  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [showArchiveModal, setShowArchiveModal] = useState(false)
  const [notaParaAcao, setNotaParaAcao] = useState(null)

  // estado dos toasts
  const [toast, setToast] = useState({ isOpen: false, message: '', type: 'success' })
  const showToast = (message, type = 'success') => setToast({ isOpen: true, message, type })

  // filtro de notas
  const notasFiltradas = notas.filter(nota => {
    // se for tela de tags, não lista notas
    if (filtroAtivo === 'LISTA_TAGS') return false

    // filtro de notas arquivadas
    if (filtroAtivo === 'archive') {
      if (!nota.arquivado) return false
    } else {
      if (nota.arquivado) return false
    }

    // filtro por tag específica
    if (filtroAtivo !== 'all' && filtroAtivo !== 'archive') {
      if (!nota.tags.includes(filtroAtivo)) return false
    }

    // filtro por busca
    if (termoBusca !== '') {
      const conteudo = (nota.titulo + " " + nota.descricao + " " + nota.tags.join(" ")).toLowerCase()
      if (!conteudo.includes(termoBusca.toLowerCase())) return false
    }

    return true
  })

  // lista de tags única
  const todasTags = [...new Set(notas.flatMap(n => n.tags))].sort()

  // ação: arquivar ou restaurar
  const handleArquivar = (notaParaArquivar) => {
    const alvo = notaParaAcao || notaParaArquivar
    if (!alvo) return

    const atualizado = { ...alvo, arquivado: !alvo.arquivado }
    setNotas(notas.map(n => (n.id === atualizado.id ? atualizado : n)))

    setNotaSelecionada(null)
    setNotaParaAcao(null)
    setShowArchiveModal(false)

    showToast(atualizado.arquivado ? "Nota arquivada!" : "Nota restaurada!")
  }

  // ação: salvar nota
  const handleSalvar = (notaEditada) => {
    const existe = notas.some(n => n.id === notaEditada.id)

    if (existe) {
      setNotas(notas.map(n => (n.id === notaEditada.id ? notaEditada : n)))
      showToast("Nota salva com sucesso!")
    } else {
      setNotas([notaEditada, ...notas])
      showToast("Nota criada com sucesso!")
    }

    setNotaSelecionada(notaEditada)
  }

  // confirmação delete
  const confirmarDeletar = (n) => {
    setNotaParaAcao(n)
    setShowDeleteModal(true)
  }

  // ação: deletar nota
  const executarDeletar = () => {
    if (notaParaAcao) {
      setNotas(notas.filter(n => n.id !== notaParaAcao.id))
      setNotaSelecionada(null)
      showToast("Nota excluída permanentemente.")
    }
    setShowDeleteModal(false)
    setNotaParaAcao(null)
  }

  // ação: criar nova nota
  const handleCriarNova = () => {
    if (filtroAtivo === 'LISTA_TAGS') setFiltroAtivo('all')

    setNotaSelecionada({
      id: Date.now(),
      titulo: "",
      descricao: "",
      tags: [],
      data: "Hoje",
      arquivado: false,
      imagem: null
    })
  }

  // decide o que aparece no conteúdo principal
  const renderConteudoPrincipal = () => {

    // modo: lista de tags (mobile)
    if (filtroAtivo === 'LISTA_TAGS') {
      return (
        <div className="container-tags-mobile">
          <h2 className="titulo-pagina-mobile">Tags</h2>

          <div className="lista-tags-full">
            {todasTags.length > 0 ? (
              todasTags.map(tag => (
                <button
                  key={tag}
                  className="item-tag-full"
                  onClick={() => setFiltroAtivo(tag)}
                >
                  <Tag size={18} />
                  {tag}
                </button>
              ))
            ) : (
              <p style={{ padding: 20, color: 'var(--text-secondary)' }}>Nenhuma tag criada ainda.</p>
            )}
          </div>
        </div>
      )
    }

    // modo: lista normal de notas
    return (
      <div className={`wrapper-lista ${notaSelecionada ? 'esconder-mobile' : ''}`}>
        <ListaNotas
          notas={notasFiltradas}
          vizualisarNota={setNotaSelecionada}
          aoCriarNova={handleCriarNova}
          filtroAtivo={filtroAtivo}
          termoBusca={termoBusca}
        />
      </div>
    )
  }

  return (
    <div className="global-tela">
      <div className="layout-grid">

        {/* menu lateral desktop */}
        <aside className="area-menu">
          <MenuLateral
            filtroAtivo={filtroAtivo}
            setFiltro={setFiltroAtivo}
            tagsDisponiveis={todasTags}
          />
        </aside>

        {/* área central */}
        <main className="area-conteudo">

          {/* header mobile */}
          <div className={`mobile-header-control ${notaSelecionada ? 'esconder-mobile' : ''}`}>
            {filtroAtivo === 'LISTA_TAGS' ? (
              <div className="header-mobile-simples">
                <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
                  <NotebookPen color="#2563EB" size={24} />
                  <span>Senai Notes</span>
                </div>
              </div>
            ) : (
              <CabecalhoTopo termoBusca={termoBusca} setBusca={setTermoBusca} />
            )}
          </div>

          {/* conteúdo lado a lado */}
          <div className="conteudo-dividido">

            {/* parte da esquerda */}
            {renderConteudoPrincipal()}

            {/* detalhe da nota */}
            <div className={`area-detalhe-wrapper ${!notaSelecionada ? 'esconder-mobile' : 'mostrar-mobile'}`}>
              {notaSelecionada && (
                <div className="mobile-back-header">
                  <button onClick={() => setNotaSelecionada(null)} className="btn-voltar-mobile">
                    <ArrowLeft size={20} /> Go Back
                  </button>

                  <div className="mobile-action-icons">
                    <button onClick={() => { setNotaParaAcao(notaSelecionada); setShowArchiveModal(true) }}>
                      <Archive size={20} color="#6B7280" />
                    </button>

                    <button onClick={() => { setNotaParaAcao(notaSelecionada); setShowDeleteModal(true) }}>
                      <Trash2 size={20} color="#EF4444" />
                    </button>
                  </div>
                </div>
              )}

              <NotaDetalhe
                nota={notaSelecionada}
                aoSalvar={handleSalvar}
                aoDeletar={confirmarDeletar}
                aoArquivar={(n) => { setNotaParaAcao(n); setShowArchiveModal(true) }}
              />
            </div>
          </div>
        </main>
      </div>

      {/* menu mobile inferior */}
      <MenuMobile
        filtroAtivo={filtroAtivo}
        setFiltro={setFiltroAtivo}
        aoCriarNova={handleCriarNova}
      />

      {/* modais */}
      <ModalConfirmacao
        isOpen={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        onConfirm={executarDeletar}
        title="Delete Note"
        message="Are you sure?"
        confirmButtonText="Delete"
        confirmButtonColor="danger"
        iconType="delete"
      />

      <ModalConfirmacao
        isOpen={showArchiveModal}
        onClose={() => setShowArchiveModal(false)}
        onConfirm={() => handleArquivar(notaParaAcao)}
        title="Archive Note"
        message="Archive note?"
        confirmButtonText="Archive"
        confirmButtonColor="primary"
        iconType="archive"
      />

      <ToastNotificacao
        isOpen={toast.isOpen}
        onClose={() => setToast({ ...toast, isOpen: false })}
        message={toast.message}
        type={toast.type}
      />
    </div>
  )
}

export default TelaNotas
