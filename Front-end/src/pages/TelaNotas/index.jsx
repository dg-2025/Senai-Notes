import React, { useState, useEffect } from 'react'
import './style.css'
import { ArrowLeft, Archive, Trash2, Tag, NotebookPen } from 'lucide-react'

import MenuLateral from '../../componentes/menu-lateral'
import CabecalhoTopo from '../../componentes/cabecalho-topo'
import ListaNotas from '../../componentes/lista-notas'
import NotaDetalhe from '../../componentes/nota-detalhe'
import ModalConfirmacao from '../../componentes/modal-confirmacao'
import ToastNotificacao from '../../componentes/toast-notificacao'
import MenuMobile from '../../componentes/menu-mobile'

import api from '../../pages/services/api'

function TelaNotas() {
  const [notas, setNotas] = useState([])
  const [loading, setLoading] = useState(true)

  const [notaSelecionada, setNotaSelecionada] = useState(null)
  const [termoBusca, setTermoBusca] = useState('')
  const [filtroAtivo, setFiltroAtivo] = useState('all')

  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [showArchiveModal, setShowArchiveModal] = useState(false)
  const [notaParaAcao, setNotaParaAcao] = useState(null)
  const [toast, setToast] = useState({ isOpen: false, message: '', type: 'success' })

  const userId = localStorage.getItem('usuarioId')

  const showToast = (message, type = 'success') => {
    setToast({ isOpen: true, message, type })
  }

// O URL base do seu bucket S3 (substitua a região se for diferente)
const S3_BASE_URL = 'https://senai-notes-meus-arquivos-01.s3.us-east-2.amazonaws.com/';


// Busca as notas do usuário
const carregarNotas = async () => {
  if (!userId) return
  try {
    setLoading(true)

    const response = await api.get(`/api/notas/buscarporid/${userId}`)

    const dadosFormatados = response.data.map(item => ({
      id: item.idNota,
      titulo: item.titulo,
      descricao: item.descricao,
      tags: item.tags || [],
      data: new Date(item.ultimaEdicao || item.dataCriacao).toLocaleDateString('pt-BR'),
      arquivado: false,
      imagem: item.imagem
        ? `${S3_BASE_URL}${item.imagem}` 
        : null
    }))

    setNotas(dadosFormatados)
  } catch (error) {
    console.error('Erro ao buscar:', error)
    showToast('Erro ao carregar notas', 'error')
  } finally {
    setLoading(false)
  }
}

  useEffect(() => {
    carregarNotas()
  }, [])

  // Garante que as tags existem antes de salvar
  const garantirTagsNoBanco = async listaTags => {
    if (!listaTags || listaTags.length === 0) return

    await Promise.all(
      listaTags.map(async nomeTag => {
        try {
          await api.post('/api/tag', {
            usuarioId: parseInt(userId),
            nome: nomeTag
          })
        } catch (error) {
          // Ignora erro se a tag já existir
        }
      })
    )
  }

  // Cria ou atualiza uma nota
  const handleSalvar = async notaEditada => {
    try {
      if (notaEditada.tags && notaEditada.tags.length > 0) {
        await garantirTagsNoBanco(notaEditada.tags)
      }

      const formData = new FormData()
      formData.append('idUsuario', userId)
      formData.append('titulo', notaEditada.titulo)
      formData.append('descricao', notaEditada.descricao)

      if (notaEditada.tags && notaEditada.tags.length > 0) {
        notaEditada.tags.forEach(tag => formData.append('tags', tag))
      } else {
        formData.append('tags', '')
      }

      if (notaEditada.imagemArquivo instanceof File) {
        formData.append('imagem', notaEditada.imagemArquivo)
      }

      if (notaEditada.id && notaEditada.id !== 'temp') {
        await api.put(`/api/notas/${notaEditada.id}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })
        showToast('Nota atualizada com sucesso!')
      } else {
        await api.post('/api/notas', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })
        showToast('Nota criada com sucesso!')
      }

      carregarNotas()
      setNotaSelecionada(null)
    } catch (error) {
      console.error('Erro ao salvar:', error)
      showToast('Erro ao salvar. Verifique os dados.', 'error')
    }
  }

  // Executa exclusão da nota
  const executarDeletar = async () => {
    if (!notaParaAcao) return
    try {
      await api.delete(`/api/notas/${notaParaAcao.id}`)
      setNotas(notas.filter(n => n.id !== notaParaAcao.id))
      setNotaSelecionada(null)
      showToast('Nota excluída.')
    } catch (error) {
      console.error('Erro deletar:', error)
      showToast('Erro ao excluir.', 'error')
    } finally {
      setShowDeleteModal(false)
      setNotaParaAcao(null)
    }
  }

  // Arquiva visualmente a nota
  const executarArquivar = async () => {
    if (!notaParaAcao) return
    try {
      const novaSituacao = !notaParaAcao.arquivado
      const notaAtualizada = { ...notaParaAcao, arquivado: novaSituacao }

      setNotas(notas.map(n => (n.id === notaParaAcao.id ? notaAtualizada : n)))
      setNotaSelecionada(null)
      showToast(novaSituacao ? 'Nota arquivada!' : 'Nota restaurada!')
    } catch (error) {
      console.error('Erro:', error)
    } finally {
      setShowArchiveModal(false)
      setNotaParaAcao(null)
    }
  }

  // Cria uma nova nota temporária
  const handleCriarNova = () => {
    if (filtroAtivo === 'LISTA_TAGS') setFiltroAtivo('all')

    setNotaSelecionada({
      id: 'temp',
      titulo: '',
      descricao: '',
      tags: [],
      arquivado: false,
      imagem: null,
      imagemArquivo: null
    })
  }

  const confirmarDeletar = n => {
    setNotaParaAcao(n)
    setShowDeleteModal(true)
  }

  const confirmarArquivar = n => {
    setNotaParaAcao(n)
    setShowArchiveModal(true)
  }

  const handleVoltarLista = () => {
    setNotaSelecionada(null)
  }

  // Filtra notas por tags, arquivamento e busca
  const notasFiltradas = notas.filter(nota => {
    if (filtroAtivo === 'LISTA_TAGS') return false

    if (filtroAtivo === 'archive') {
      if (!nota.arquivado) return false
    } else {
      if (nota.arquivado) return false
    }

    if (filtroAtivo !== 'all' && filtroAtivo !== 'archive') {
      if (!nota.tags || !nota.tags.includes(filtroAtivo)) return false
    }

    if (termoBusca !== '') {
      const conteudo = (
        (nota.titulo || '') +
        ' ' +
        (nota.descricao || '') +
        ' ' +
        (nota.tags || []).join(' ')
      ).toLowerCase()

      if (!conteudo.includes(termoBusca.toLowerCase())) return false
    }

    return true
  })

  const todasTags = [...new Set(notas.flatMap(n => n.tags || []))].sort()

  // Renderiza conteúdo principal dependendo da tela mobile
  const renderConteudoPrincipal = () => {
    if (filtroAtivo === 'LISTA_TAGS') {
      return (
        <div className="container-tags-mobile">
          <h2 className="titulo-pagina-mobile">Tags</h2>

          <div className="lista-tags-full">
            {todasTags.length > 0
              ? todasTags.map(tag => (
                  <button
                    key={tag}
                    className="item-tag-full"
                    onClick={() => setFiltroAtivo(tag)}
                  >
                    <Tag size={18} /> {tag}
                  </button>
                ))
              : (
                <p style={{ padding: 20, color: 'var(--text-secondary)' }}>
                  Nenhuma tag.
                </p>
              )}
          </div>
        </div>
      )
    }

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
        <aside className="area-menu">
          <MenuLateral
            filtroAtivo={filtroAtivo}
            setFiltro={setFiltroAtivo}
            tagsDisponiveis={todasTags}
          />
        </aside>

        <main className="area-conteudo">
          <div
            className={`mobile-header-control ${
              notaSelecionada ? 'esconder-mobile' : ''
            }`}
          >
            {filtroAtivo === 'LISTA_TAGS'
              ? (
                <div className="header-mobile-simples">
                  <NotebookPen color="#2563EB" size={24} /> Senai Notes
                </div>
              )
              : (
                <CabecalhoTopo
                  termoBusca={termoBusca}
                  setBusca={setTermoBusca}
                />
              )}
          </div>

          <div className="conteudo-dividido">
            {renderConteudoPrincipal()}

            <div
              className={`area-detalhe-wrapper ${
                !notaSelecionada ? 'esconder-mobile' : 'mostrar-mobile'
              }`}
            >
              {notaSelecionada && (
                <div className="mobile-back-header">
                  <button
                    onClick={handleVoltarLista}
                    className="btn-voltar-mobile"
                  >
                    <ArrowLeft size={20} /> Go Back
                  </button>

                  <div className="mobile-action-icons">
                    <button
                      onClick={() => confirmarArquivar(notaSelecionada)}
                    >
                      <Archive size={20} color="#6B7280" />
                    </button>

                    <button
                      onClick={() => confirmarDeletar(notaSelecionada)}
                    >
                      <Trash2 size={20} color="#EF4444" />
                    </button>
                  </div>
                </div>
              )}

              {loading && !notas.length && !notaSelecionada
                ? (
                  <div className="detalhe-vazio">
                    <p>Carregando...</p>
                  </div>
                )
                : (
                  <NotaDetalhe
                    nota={notaSelecionada}
                    aoSalvar={handleSalvar}
                    aoDeletar={confirmarDeletar}
                    aoArquivar={confirmarArquivar}
                  />
                )}
            </div>
          </div>
        </main>
      </div>

      <MenuMobile
        filtroAtivo={filtroAtivo}
        setFiltro={setFiltroAtivo}
        aoCriarNova={handleCriarNova}
      />

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
        onConfirm={executarArquivar}
        title="Archive Note"
        message="Archive note?"
        confirmButtonText={notaParaAcao?.arquivado ? 'Restore' : 'Archive'}
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
