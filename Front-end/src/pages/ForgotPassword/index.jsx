// Importações principais do React
import React, { useState } from 'react'

// Reaproveita o CSS da página de Login
import '../Login/style.css'

// Ícones utilizados na interface
import { NotebookPen, ArrowLeft, Linkedin, Github } from 'lucide-react'

// Link para navegar entre telas
import { Link } from 'react-router-dom'

// API configurada para chamada no backend
import api from '../../pages/services/api'

// Logo da aplicação
import LogoFeather from '../../assets/Feather.svg'

export default function ForgotPassword() {
  // Campo do email digitado
  const [email, setEmail] = useState('')

  // Estado usado para alterar texto do botão enquanto envia
  const [loading, setLoading] = useState(false)

  // Quando o usuário envia o formulário, dispara a requisição
  const handleReset = async e => {
    e.preventDefault()
    setLoading(true)

    try {
      // Envia o e-mail para o backend gerar o link de recuperação
      await api.post('/api/usuarios/forgot-password', {
        email: email
      })

      alert(`E-mail de recuperação enviado para ${email}`)

    } catch (error) {
      console.error('Erro ao recuperar:', error)
      alert('Erro ao enviar e-mail. Verifique se o endereço está correto.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-container">
      <div className="login-card">

        {/* Cabeçalho com logo e título */}
        <div className="login-header">
          <div className="logo-marca">
            <img src={LogoFeather} alt="Logo" style={{ width: 30, height: 30 }} />
            <h1>Senai Notes</h1>
          </div>

          <h2>Forgotten your password?</h2>
          <p>Enter your email below, and we’ll send you a link to reset it.</p>
        </div>

        {/* Formulário para enviar e-mail */}
        <form className="login-form" onSubmit={handleReset}>
          <div className="grupo-input">
            <label>Email Address</label>

            <input
              type="email"
              placeholder="email@example.com"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? 'Sending...' : 'Send Reset Link'}
          </button>
        </form>

        {/* Link de voltar para a página de Login */}
        <div className="login-footer">
          <Link
            to="/login"
            style={{
              display: 'flex',
              alignItems: 'center',
              gap: 5,
              justifyContent: 'center',
              textDecoration: 'none'
            }}
          >
            <ArrowLeft size={16} /> Back to Login
          </Link>
        </div>

        {/* Rodapé com assinatura e ícones sociais */}
        <div className="menu-footer">
          <p>
            Developed by <strong>Daniel</strong>
          </p>

          <div className="social-icons">
            <a
              href="https://www.linkedin.com/in/daniel-gomes-fullstack"
              target="_blank"
              rel="noopener noreferrer"
              title="LinkedIn"
            >
              <Linkedin size={20} />
            </a>

            <a
              href="https://github.com/dg-2025"
              target="_blank"
              rel="noopener noreferrer"
              title="GitHub"
            >
              <Github size={20} />
            </a>
          </div>
        </div>

      </div>
    </div>
  )
}
