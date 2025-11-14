// Importações principais do React e Hooks
import React, { useState } from 'react'

// Estilos específicos da página de Login
import './style.css'

// Ícones utilizados na interface
import { NotebookPen, Eye, EyeOff, Linkedin, Github } from 'lucide-react'

// Navegação entre páginas
import { useNavigate, Link } from 'react-router-dom'

// API configurada para comunicação com o backend
import api from '../../pages/services/api'

// Logo do Senai Notes
import LogoFeather from '../../assets/Feather.svg'

export default function Login() {
  // Função usada para redirecionar o usuário após o login
  const navigate = useNavigate()

  // Estados dos campos de entrada
  const [mostrarSenha, setMostrarSenha] = useState(false)
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [loading, setLoading] = useState(false) // controla o botão enquanto a requisição acontece

  // Função executada quando o usuário envia o formulário
  const handleLogin = async e => {
    e.preventDefault()
    setLoading(true) // trava o botão de login

    try {
      // Envia os dados para o backend exatamente como definido no Swagger
      const response = await api.post('/api/login', {
        email: email,
        senha: senha
      })

      // O backend retorna token + dados do usuário
      const { token, usuario } = response.data

      // Salva no navegador para manter o usuário autenticado
      localStorage.setItem('token', token)
      localStorage.setItem('usuarioId', usuario.id)
      localStorage.setItem('usuarioNome', usuario.nome)
      localStorage.setItem('usuarioEmail', usuario.email)

      // Redireciona o usuário para o dashboard
      navigate('/dashboard')

    } catch (error) {
      console.error('Erro no login:', error)
      alert('Falha no login! Verifique seu e-mail e senha.')
    } finally {
      setLoading(false) // libera o botão
    }
  }

  return (
    <div className="login-container">
      <div className="login-card">

        {/* Cabeçalho com logo e texto de boas-vindas */}
        <div className="login-header">
          <div className="logo-marca">
            <img src={LogoFeather} alt="Logo" style={{ width: 30, height: 30 }} />
            <h1>Senai Notes</h1>
          </div>

          <h2>Welcome to Note</h2>
          <p>Please log in to continue</p>
        </div>

        {/* Formulário de login */}
        <form className="login-form" onSubmit={handleLogin}>

          {/* Campo de email */}
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

          {/* Campo de senha */}
          <div className="grupo-input">
            <div className="label-row">
              <label>Password</label>

              {/* Link para recuperação de senha */}
              <Link to="/forgot-password" className="link-esqueci">
                Forgot
              </Link>
            </div>

            <div className="input-senha-wrapper">
              <input
                type={mostrarSenha ? 'text' : 'password'}
                placeholder="••••••••"
                value={senha}
                onChange={e => setSenha(e.target.value)}
                required
              />

              {/* Botão para mostrar/ocultar senha */}
              <button
                type="button"
                className="btn-olho"
                onClick={() => setMostrarSenha(!mostrarSenha)}
              >
                {mostrarSenha ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          {/* Botão principal */}
          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? 'Logging in...' : 'Login'}
          </button>

        </form>

        {/* Rodapé com link para criar conta */}
        <div className="login-footer">
          <p>
            No account yet?
            <Link to="/signup"> Sign Up</Link>
          </p>
        </div>

        {/* Rodapé com créditos e redes sociais */}
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
