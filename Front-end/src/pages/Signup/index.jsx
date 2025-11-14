// Importações principais do React e Hooks
import React, { useState } from 'react'

// Reaproveita o CSS da página de Login
import '../Login/style.css'

// Ícones usados na interface
import { NotebookPen, Eye, EyeOff, Linkedin, Github } from 'lucide-react'

// Navegação entre páginas
import { Link, useNavigate } from 'react-router-dom'

// API utilizada para cadastrar usuário
import api from '../../pages/services/api'

// Logo da aplicação
import LogoFeather from '../../assets/Feather.svg'

export default function Signup() {
  // Função para redirecionar após criar a conta
  const navigate = useNavigate()

  // Controle de mostrar/ocultar senha
  const [mostrarSenha, setMostrarSenha] = useState(false)

  // Campos do formulário
  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [loading, setLoading] = useState(false)

  // Função executada ao enviar o formulário
  const handleSignup = async e => {
    e.preventDefault()
    setLoading(true)

    try {
      // Envia os dados para o backend
      await api.post('/api/usuarios/cadastrar', {
        nome: nome,
        email: email,
        senha: senha
      })

      alert('Conta criada com sucesso! Faça login.')
      navigate('/login') // redireciona após o cadastro

    } catch (error) {
      // Caso o backend retorne alguma mensagem, ela será exibida
      const mensagemErro = error.response?.data || 'Erro ao criar conta.'
      alert(mensagemErro)

    } finally {
      setLoading(false) // libera o botão independente do resultado
    }
  }

  return (
    <div className="login-container">
      <div className="login-card">

        {/* Cabeçalho da tela */}
        <div className="login-header">
          <div className="logo-marca">
            <img src={LogoFeather} alt="Logo" style={{ width: 30, height: 30 }} />
            <h1>Senai Notes</h1>
          </div>

          <h2>Create Your Account</h2>
          <p>Sign up to start organizing your notes.</p>
        </div>

        {/* Formulário de cadastro */}
        <form className="login-form" onSubmit={handleSignup}>

          {/* Campo Nome */}
          <div className="grupo-input">
            <label>Full Name</label>
            <input
              type="text"
              placeholder="Ex: Daniel Galdino"
              value={nome}
              onChange={e => setNome(e.target.value)}
              required
            />
          </div>

          {/* Campo Email */}
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

          {/* Campo Senha */}
          <div className="grupo-input">
            <label>Password</label>

            <div className="input-senha-wrapper">
              <input
                type={mostrarSenha ? 'text' : 'password'}
                placeholder="At least 8 characters"
                value={senha}
                onChange={e => setSenha(e.target.value)}
                required
                minLength={8}
              />

              {/* Botão do olho para mostrar/ocultar senha */}
              <button
                type="button"
                className="btn-olho"
                onClick={() => setMostrarSenha(!mostrarSenha)}
              >
                {mostrarSenha ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          {/* Botão enviar */}
          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? 'Creating...' : 'Sign Up'}
          </button>
        </form>

        {/* Links de rodapé */}
        <div className="login-footer">
          <p>
            Already have an account?
            <Link to="/login"> Login</Link>
          </p>
        </div>

        {/* Rodapé com créditos e redes sociais */}
        <div className="menu-footer">
          <p>Developed by <strong>Daniel</strong></p>

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
