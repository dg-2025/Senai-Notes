// Importações principais do React
import React, { useState, useEffect } from 'react'

// Estilos desta tela
import './style.css'

// Ícones
import {
  Sun, Moon, Monitor, Type, Lock, LogOut, ChevronRight, ArrowLeft,
  Eye, EyeOff, Info, NotebookPen, ChevronLeft,
  Linkedin, Github
} from 'lucide-react'

// Navegação entre rotas
import { useNavigate } from 'react-router-dom'

// Componentes reutilizados
import MenuLateral from '../../componentes/menu-lateral'
import MenuMobile from '../../componentes/menu-mobile'

// API para integração com backend
import api from '../../pages/services/api'

export default function TelaConfig() {

  // Usado para navegação entre telas
  const navigate = useNavigate()

  // Aba atual selecionada
  const [abaAtiva, setAbaAtiva] = useState('color')

  // Controle de visualização no mobile
  const [mobileView, setMobileView] = useState('menu')

  // Estados de configurações
  const [temaSelecionado, setTemaSelecionado] = useState('light')
  const [fontSelecionada, setFontSelecionada] = useState('sans')

  // Controle do formulário de senha
  const [senhas, setSenhas] = useState({ old: '', new: '', confirm: '' })

  // Controle de mostrar/ocultar senha
  const [mostrarSenha, setMostrarSenha] = useState({
    old: false,
    new: false,
    confirm: false
  })

  // Voltar para dashboard
  const handleVoltarDashboard = () => navigate('/dashboard')

  // Mobile: voltar para o menu lateral
  const voltarParaMenuMobile = () => setMobileView('menu')

  // Mobile: abrir área de conteúdo
  const irParaOpcao = opcao => {
    setAbaAtiva(opcao)
    setMobileView('content')
  }

  // Alterna visibilidade da senha
  const toggleMostrarSenha = campo => {
    setMostrarSenha(prev => ({ ...prev, [campo]: !prev[campo] }))
  }

  // Aplica tema claro/escuro/sistema
  const aplicarTema = novoTema => {
    setTemaSelecionado(novoTema)
    localStorage.setItem('tema', novoTema)

    if (novoTema === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark')
    } else {
      document.documentElement.removeAttribute('data-theme')
    }
  }

  // Carrega tema salvo ao abrir a tela
  useEffect(() => {
    const temaSalvo = localStorage.getItem('tema')
    if (temaSalvo) {
      setTemaSelecionado(temaSalvo)
      if (temaSalvo === 'dark') {
        document.documentElement.setAttribute('data-theme', 'dark')
      }
    }
  }, [])

  // Renderização das opções dependendo da aba
  const renderConteudo = () => {

    // Aba: Tema de cores
    if (abaAtiva === 'color') {
      return (
        <div className="config-secao">
          <h2 className="mobile-hidden">Color Theme</h2>
          <p className="subtitulo">Choose your color theme:</p>

          {/* Lista de temas */}
          <div className="opcoes-tema">

            {/* Tema claro */}
            <div
              className={`card-opcao ${temaSelecionado === 'light' ? 'selecionado' : ''}`}
              onClick={() => aplicarTema('light')}
            >
              <div className="info-opcao">
                <div className="icone-bg"><Sun size={24} /></div>
                <div className="texto-opcao">
                  <h3>Light Mode</h3>
                  <p>Pick a clean and classic light theme</p>
                </div>
              </div>
              <div className="radio-custom">
                {temaSelecionado === 'light' && <div className="radio-inner" />}
              </div>
            </div>

            {/* Tema escuro */}
            <div
              className={`card-opcao ${temaSelecionado === 'dark' ? 'selecionado' : ''}`}
              onClick={() => aplicarTema('dark')}
            >
              <div className="info-opcao">
                <div className="icone-bg"><Moon size={24} /></div>
                <div className="texto-opcao">
                  <h3>Dark Mode</h3>
                  <p>Select a sleek and modern dark theme</p>
                </div>
              </div>
              <div className="radio-custom">
                {temaSelecionado === 'dark' && <div className="radio-inner" />}
              </div>
            </div>

            {/* Tema do sistema */}
            <div
              className={`card-opcao ${temaSelecionado === 'system' ? 'selecionado' : ''}`}
              onClick={() => aplicarTema('system')}
            >
              <div className="info-opcao">
                <div className="icone-bg"><Monitor size={24} /></div>
                <div className="texto-opcao">
                  <h3>System</h3>
                  <p>Adapts to your device's theme</p>
                </div>
              </div>
              <div className="radio-custom">
                {temaSelecionado === 'system' && <div className="radio-inner" />}
              </div>
            </div>
          </div>

          <div className="botao-salvar-container">
            <button className="btn-salvar-config">Apply Changes</button>
          </div>
        </div>
      )
    }

    // Aba: Tema de fontes
    if (abaAtiva === 'font') {
      return (
        <div className="config-secao">
          <h2 className="mobile-hidden">Font Theme</h2>
          <p className="subtitulo">Choose your font theme:</p>

          <div className="opcoes-tema">

            {/* Sans */}
            <div
              className={`card-opcao ${fontSelecionada === 'sans' ? 'selecionado' : ''}`}
              onClick={() => setFontSelecionada('sans')}
            >
              <div className="info-opcao">
                <div className="icone-bg letra" style={{ fontFamily: 'sans-serif' }}>Aa</div>
                <div className="texto-opcao">
                  <h3>Sans-serif</h3>
                  <p>Clean and modern.</p>
                </div>
              </div>
              <div className="radio-custom">
                {fontSelecionada === 'sans' && <div className="radio-inner" />}
              </div>
            </div>

            {/* Serif */}
            <div
              className={`card-opcao ${fontSelecionada === 'serif' ? 'selecionado' : ''}`}
              onClick={() => setFontSelecionada('serif')}
            >
              <div className="info-opcao">
                <div className="icone-bg letra" style={{ fontFamily: 'serif' }}>Aa</div>
                <div className="texto-opcao">
                  <h3>Serif</h3>
                  <p>Classic and elegant.</p>
                </div>
              </div>
              <div className="radio-custom">
                {fontSelecionada === 'serif' && <div className="radio-inner" />}
              </div>
            </div>

            {/* Monospace */}
            <div
              className={`card-opcao ${fontSelecionada === 'mono' ? 'selecionado' : ''}`}
              onClick={() => setFontSelecionada('mono')}
            >
              <div className="info-opcao">
                <div className="icone-bg letra" style={{ fontFamily: 'monospace' }}>Aa</div>
                <div className="texto-opcao">
                  <h3>Monospace</h3>
                  <p>Code-like vibe.</p>
                </div>
              </div>
              <div className="radio-custom">
                {fontSelecionada === 'mono' && <div className="radio-inner" />}
              </div>
            </div>

          </div>

          <div className="botao-salvar-container">
            <button className="btn-salvar-config">Apply Changes</button>
          </div>
        </div>
      )
    }

    // Aba: Alteração de senha
    if (abaAtiva === 'password') {

      // Função de salvar senha
      const handleSavePassword = async () => {

        // Validação simples dos campos
        if (!senhas.old || !senhas.new || !senhas.confirm) {
          alert('Preencha todos os campos.')
          return
        }

        if (senhas.new !== senhas.confirm) {
          alert('A nova senha e a confirmação não batem.')
          return
        }

        if (senhas.new.length < 8) {
          alert('A senha deve ter pelo menos 8 caracteres.')
          return
        }

        try {
          const userId = localStorage.getItem('usuarioId')

          await api.put(`/api/usuarios/${userId}/alterar-senha`, {
            senhaAntiga: senhas.old,
            novaSenha: senhas.new
          })

          alert('Senha alterada com sucesso.')
          setSenhas({ old: '', new: '', confirm: '' })

        } catch (error) {
          const mensagem = error.response?.data || 'Erro ao alterar senha.'
          alert(mensagem)
        }
      }

      return (
        <div className="config-secao">
          <h2 className="mobile-hidden">Change Password</h2>

          <form className="form-senha-config" onSubmit={e => e.preventDefault()}>

            {/* Senha antiga */}
            <div className="campo-config">
              <label>Old Password</label>
              <div className="input-wrapper">
                <input
                  type={mostrarSenha.old ? 'text' : 'password'}
                  value={senhas.old}
                  onChange={e => setSenhas({ ...senhas, old: e.target.value })}
                />
                <button className="btn-olho-config" onClick={() => toggleMostrarSenha('old')}>
                  {mostrarSenha.old ? <EyeOff size={18} /> : <Eye size={18} />}
                </button>
              </div>
            </div>

            {/* Nova senha */}
            <div className="campo-config">
              <label>New Password</label>
              <div className="input-wrapper">
                <input
                  type={mostrarSenha.new ? 'text' : 'password'}
                  value={senhas.new}
                  onChange={e => setSenhas({ ...senhas, new: e.target.value })}
                />
                <button className="btn-olho-config" onClick={() => toggleMostrarSenha('new')}>
                  {mostrarSenha.new ? <EyeOff size={18} /> : <Eye size={18} />}
                </button>
              </div>

              <div className="helper-text">
                <Info size={14} /> At least 8 characters
              </div>
            </div>

            {/* Confirmar nova senha */}
            <div className="campo-config">
              <label>Confirm New Password</label>
              <div className="input-wrapper">
                <input
                  type={mostrarSenha.confirm ? 'text' : 'password'}
                  value={senhas.confirm}
                  onChange={e => setSenhas({ ...senhas, confirm: e.target.value })}
                />
                <button className="btn-olho-config" onClick={() => toggleMostrarSenha('confirm')}>
                  {mostrarSenha.confirm ? <EyeOff size={18} /> : <Eye size={18} />}
                </button>
              </div>
            </div>

            <div className="botao-salvar-container">
              <button className="btn-salvar-config" onClick={handleSavePassword}>
                Save Password
              </button>
            </div>

          </form>
        </div>
      )
    }

    return null
  }

  return (
    <div className="global-tela">
      <div className="layout-grid">

        {/* Menu lateral desktop */}
        <aside className="area-menu mobile-hidden">
          <MenuLateral
            filtroAtivo="settings"
            setFiltro={handleVoltarDashboard}
            tagsDisponiveis={[]}
          />
        </aside>

        {/* Área principal */}
        <main className="area-conteudo">

          {/* Header desktop com botão voltar */}
          <header
            className="cabecalho-container mobile-hidden"
            style={{ justifyContent: 'flex-end' }}
          >
            <div className="icones-config">
              <button className="btn-icon" onClick={handleVoltarDashboard}>
                <ArrowLeft size={20} /> Voltar
              </button>
            </div>
          </header>

          {/* Header mobile */}
          <div className="header-config-mobile mobile-only">
            {mobileView === 'menu'
              ? (
                <div className="logo-mobile-config">
                  <NotebookPen color="#2563EB" size={24} /> Senai Notes
                </div>
              )
              : (
                <button className="btn-voltar-config" onClick={voltarParaMenuMobile}>
                  <ChevronLeft size={20} /> Settings
                </button>
              )}
          </div>

          {/* Layout principal da página de configurações */}
          <div className="layout-config">

            {/* Sidebar de opções (desktop e mobile menu) */}
            <aside
              className={`sidebar-config ${mobileView === 'content' ? 'mobile-hidden' : ''}`}
            >
              <h2 className="mobile-only" style={{ marginBottom: 20, fontSize: 24, fontWeight: 700 }}>
                Settings
              </h2>

              {/* Botão tema */}
              <button
                className={`item-config ${abaAtiva === 'color' ? 'ativo' : ''}`}
                onClick={() => irParaOpcao('color')}
              >
                <Sun size={18} />
                <span className="label-config">Color Theme</span>
                <ChevronRight size={16} className="seta-dir" />
              </button>

              {/* Botão fonte */}
              <button
                className={`item-config ${abaAtiva === 'font' ? 'ativo' : ''}`}
                onClick={() => irParaOpcao('font')}
              >
                <Type size={18} />
                <span className="label-config">Font Theme</span>
                <ChevronRight size={16} className="seta-dir" />
              </button>

              {/* Botão senha */}
              <button
                className={`item-config ${abaAtiva === 'password' ? 'ativo' : ''}`}
                onClick={() => irParaOpcao('password')}
              >
                <Lock size={18} />
                <span className="label-config">Change Password</span>
                <ChevronRight size={16} className="seta-dir" />
              </button>

              {/* Logout */}
              <button
                className="item-config logout"
                onClick={() => navigate('/login')}
              >
                <LogOut size={18} />
                <span>Logout</span>
              </button>

              {/* Rodapé mobile */}
              <div className="mobile-footer-config mobile-only">
                <div className="social-icons-mobile">
                  <a href="https://www.linkedin.com/in/daniel-gomes-fullstack" target="_blank">
                    <Linkedin size={24} />
                  </a>
                  <a href="https://github.com/dg-2025" target="_blank">
                    <Github size={24} />
                  </a>
                </div>
                <p>Developed by <strong>Daniel</strong></p>
              </div>
            </aside>

            {/* Conteúdo da aba ativa */}
            <div
              className={`conteudo-config ${
                mobileView === 'menu' ? 'mobile-hidden' : ''
              }`}
            >
              {/* Título mobile */}
              <h2 className="mobile-only" style={{ marginBottom: 20, fontWeight: 700 }}>
                {abaAtiva === 'color'
                  ? 'Color Theme'
                  : abaAtiva === 'font'
                    ? 'Font Theme'
                    : 'Change Password'}
              </h2>

              {renderConteudo()}
            </div>

          </div>
        </main>
      </div>

      {/* Menu inferior mobile */}
      <MenuMobile
        filtroAtivo="settings"
        setFiltro={f => {
          if (f !== 'settings') navigate('/dashboard')
        }}
        aoCriarNova={() => navigate('/dashboard')}
        showFab={false}
      />
    </div>
  )
}
