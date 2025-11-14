// imports principais
import React, { useState, useEffect } from 'react'
import './style.css'
import {
  Sun, Moon, Monitor, Type, Lock, LogOut, ChevronRight, ArrowLeft,
  Eye, EyeOff, Info, NotebookPen, ChevronLeft
} from 'lucide-react'
import { useNavigate } from 'react-router-dom'

// componentes
import MenuLateral from '../../componentes/menu-lateral'
import MenuMobile from '../../componentes/menu-mobile'

export default function TelaConfig() {

  // navegação
  const navigate = useNavigate()

  // abas e comportamento do mobile
  const [abaAtiva, setAbaAtiva] = useState('color')
  const [mobileView, setMobileView] = useState('menu')

  // tema e fonte
  const [temaSelecionado, setTemaSelecionado] = useState('light')
  const [fontSelecionada, setFontSelecionada] = useState('sans')

  // estados de senha
  const [senhas, setSenhas] = useState({ old: '', new: '', confirm: '' })
  const [mostrarSenha, setMostrarSenha] = useState({ old: false, new: false, confirm: false })

  // voltar dashboard
  const handleVoltarDashboard = () => navigate('/dashboard')

  // mobile: voltar menu
  const voltarParaMenuMobile = () => setMobileView('menu')

  // mobile: abrir aba
  const irParaOpcao = (opcao) => {
    setAbaAtiva(opcao)
    setMobileView('content')
  }

  // exibir senha
  const toggleMostrarSenha = (campo) => {
    setMostrarSenha(prev => ({ ...prev, [campo]: !prev[campo] }))
  }

  // aplicar tema
  const aplicarTema = (novoTema) => {
    setTemaSelecionado(novoTema)
    localStorage.setItem('tema', novoTema)

    if (novoTema === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark')
    } else {
      document.documentElement.removeAttribute('data-theme')
    }
  }

  // carregar tema salvo
  useEffect(() => {
    const temaSalvo = localStorage.getItem('tema')

    if (temaSalvo) {
      setTemaSelecionado(temaSalvo)

      if (temaSalvo === 'dark') {
        document.documentElement.setAttribute('data-theme', 'dark')
      }
    }
  }, [])

  // conteúdo de cada aba
  const renderConteudo = () => {
    switch (abaAtiva) {

      // tema de cor
      case 'color':
        return (
          <div className="config-secao">
            <h2 className="mobile-hidden">Color Theme</h2>
            <p className="subtitulo">Choose your color theme:</p>

            <div className="opcoes-tema">
              <div className={`card-opcao ${temaSelecionado === 'light' ? 'selecionado' : ''}`} onClick={() => aplicarTema('light')}>
                <div className="info-opcao">
                  <div className="icone-bg"><Sun size={24} /></div>
                  <div className="texto-opcao">
                    <h3>Light Mode</h3>
                    <p>Pick a clean and classic light theme</p>
                  </div>
                </div>
                <div className="radio-custom">{temaSelecionado === 'light' && <div className="radio-inner" />}</div>
              </div>

              <div className={`card-opcao ${temaSelecionado === 'dark' ? 'selecionado' : ''}`} onClick={() => aplicarTema('dark')}>
                <div className="info-opcao">
                  <div className="icone-bg"><Moon size={24} /></div>
                  <div className="texto-opcao">
                    <h3>Dark Mode</h3>
                    <p>Select a sleek and modern dark theme</p>
                  </div>
                </div>
                <div className="radio-custom">{temaSelecionado === 'dark' && <div className="radio-inner" />}</div>
              </div>

              <div className={`card-opcao ${temaSelecionado === 'system' ? 'selecionado' : ''}`} onClick={() => aplicarTema('system')}>
                <div className="info-opcao">
                  <div className="icone-bg"><Monitor size={24} /></div>
                  <div className="texto-opcao">
                    <h3>System</h3>
                    <p>Adapts to your device's theme</p>
                  </div>
                </div>
                <div className="radio-custom">{temaSelecionado === 'system' && <div className="radio-inner" />}</div>
              </div>
            </div>

            <div className="botao-salvar-container">
              <button className="btn-salvar-config">Apply Changes</button>
            </div>
          </div>
        )

      // tema de fonte
      case 'font':
        return (
          <div className="config-secao">
            <h2 className="mobile-hidden">Font Theme</h2>
            <p className="subtitulo">Choose your font theme:</p>

            <div className="opcoes-tema">
              <div className={`card-opcao ${fontSelecionada === 'sans' ? 'selecionado' : ''}`} onClick={() => setFontSelecionada('sans')}>
                <div className="info-opcao">
                  <div className="icone-bg letra" style={{ fontFamily: 'sans-serif' }}>Aa</div>
                  <div className="texto-opcao">
                    <h3>Sans-serif</h3>
                    <p>Clean and modern.</p>
                  </div>
                </div>
                <div className="radio-custom">{fontSelecionada === 'sans' && <div className="radio-inner" />}</div>
              </div>

              <div className={`card-opcao ${fontSelecionada === 'serif' ? 'selecionado' : ''}`} onClick={() => setFontSelecionada('serif')}>
                <div className="info-opcao">
                  <div className="icone-bg letra" style={{ fontFamily: 'serif' }}>Aa</div>
                  <div className="texto-opcao">
                    <h3>Serif</h3>
                    <p>Classic and elegant.</p>
                  </div>
                </div>
                <div className="radio-custom">{fontSelecionada === 'serif' && <div className="radio-inner" />}</div>
              </div>

              <div className={`card-opcao ${fontSelecionada === 'mono' ? 'selecionado' : ''}`} onClick={() => setFontSelecionada('mono')}>
                <div className="info-opcao">
                  <div className="icone-bg letra" style={{ fontFamily: 'monospace' }}>Aa</div>
                  <div className="texto-opcao">
                    <h3>Monospace</h3>
                    <p>Code-like vibe.</p>
                  </div>
                </div>
                <div className="radio-custom">{fontSelecionada === 'mono' && <div className="radio-inner" />}</div>
              </div>
            </div>

            <div className="botao-salvar-container">
              <button className="btn-salvar-config">Apply Changes</button>
            </div>
          </div>
        )

      // alterar senha
      case 'password':
        return (
          <div className="config-secao">
            <h2 className="mobile-hidden">Change Password</h2>

            <form className="form-senha-config" onSubmit={(e) => e.preventDefault()}>

              <div className="campo-config">
                <label>Old Password</label>
                <div className="input-wrapper">
                  <input
                    type={mostrarSenha.old ? "text" : "password"}
                    value={senhas.old}
                    onChange={e => setSenhas({ ...senhas, old: e.target.value })}
                  />
                  <button className="btn-olho-config" onClick={() => toggleMostrarSenha('old')}>
                    {mostrarSenha.old ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                </div>
              </div>

              <div className="campo-config">
                <label>New Password</label>
                <div className="input-wrapper">
                  <input
                    type={mostrarSenha.new ? "text" : "password"}
                    value={senhas.new}
                    onChange={e => setSenhas({ ...senhas, new: e.target.value })}
                  />
                  <button className="btn-olho-config" onClick={() => toggleMostrarSenha('new')}>
                    {mostrarSenha.new ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                </div>
                <div className="helper-text"><Info size={14} /> At least 8 characters</div>
              </div>

              <div className="campo-config">
                <label>Confirm New Password</label>
                <div className="input-wrapper">
                  <input
                    type={mostrarSenha.confirm ? "text" : "password"}
                    value={senhas.confirm}
                    onChange={e => setSenhas({ ...senhas, confirm: e.target.value })}
                  />
                  <button className="btn-olho-config" onClick={() => toggleMostrarSenha('confirm')}>
                    {mostrarSenha.confirm ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                </div>
              </div>

              <div className="botao-salvar-container">
                <button className="btn-salvar-config">Save Password</button>
              </div>
            </form>
          </div>
        )

      default:
        return null
    }
  }

  return (
    <div className="global-tela">
      <div className="layout-grid">

        {/* sidebar desktop */}
        <aside className="area-menu mobile-hidden">
          <MenuLateral
            filtroAtivo="settings"
            setFiltro={handleVoltarDashboard}
            tagsDisponiveis={[]}
          />
        </aside>

        <main className="area-conteudo">

          {/* header desktop */}
          <header className="cabecalho-container mobile-hidden" style={{ justifyContent: 'flex-end' }}>
            <div className="icones-config">
              <button className="btn-icon" onClick={handleVoltarDashboard}>
                <ArrowLeft size={20} /> Voltar
              </button>
            </div>
          </header>

          {/* header mobile */}
          <div className="header-config-mobile mobile-only" style={{ display: window.innerWidth > 768 ? 'none' : 'flex' }}>
            {mobileView === 'menu' ? (
              <div className="logo-mobile-config">
                <NotebookPen color="#2563EB" size={24} /> Senai Notes
              </div>
            ) : (
              <button className="btn-voltar-config" onClick={voltarParaMenuMobile}>
                <ChevronLeft size={20} /> Settings
              </button>
            )}
          </div>

          {/* conteúdo */}
          <div className="layout-config">

            {/* opções */}
            <aside className={`sidebar-config ${mobileView === 'content' ? 'mobile-hidden' : ''}`}>
              <h2
                className="mobile-only"
                style={{
                  marginBottom: 20,
                  fontSize: 24,
                  fontWeight: 700,
                  color: 'var(--text-primary)',
                  display: window.innerWidth > 768 ? 'none' : 'block'
                }}
              >
                Settings
              </h2>

              <button className={`item-config ${abaAtiva === 'color' ? 'ativo' : ''}`} onClick={() => irParaOpcao('color')}>
                <Sun size={18} /> <span className="label-config">Color Theme</span> <ChevronRight size={16} />
              </button>

              <button className={`item-config ${abaAtiva === 'font' ? 'ativo' : ''}`} onClick={() => irParaOpcao('font')}>
                <Type size={18} /> <span className="label-config">Font Theme</span> <ChevronRight size={16} />
              </button>

              <button className={`item-config ${abaAtiva === 'password' ? 'ativo' : ''}`} onClick={() => irParaOpcao('password')}>
                <Lock size={18} /> <span className="label-config">Change Password</span> <ChevronRight size={16} />
              </button>

              <button className="item-config logout" onClick={() => navigate('/login')}>
                <LogOut size={18} /> <span>Logout</span>
              </button>
            </aside>

            {/* conteúdo da aba */}
            <div className={`conteudo-config ${mobileView === 'menu' ? 'mobile-hidden' : ''}`}>
              <h2
                className="mobile-only"
                style={{
                  marginBottom: 20,
                  fontWeight: 700,
                  color: 'var(--text-primary)',
                  display: window.innerWidth > 768 ? 'none' : 'block'
                }}
              >
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

      {/* menu mobile inferior */}
      <MenuMobile
        filtroAtivo="settings"
        setFiltro={(f) => { if (f !== 'settings') navigate('/dashboard') }}
        aoCriarNova={() => navigate('/dashboard')}
        showFab={false}
      />
    </div>
  )
}
