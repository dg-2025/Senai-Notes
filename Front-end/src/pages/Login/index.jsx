// imports principais
import React, { useState } from 'react'
import './style.css'
import LogoFeather from '../../assets/Feather.svg'
import { Eye, EyeOff } from 'lucide-react'
import { useNavigate, Link } from 'react-router-dom'

export default function Login() {

  // navegação
  const navigate = useNavigate()

  // estados
  const [mostrarSenha, setMostrarSenha] = useState(false)
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')

  // envio do formulário
  const handleLogin = (e) => {
    e.preventDefault()
    console.log("Logando:", email)
    navigate('/dashboard')
  }

  return (
    <div className="login-container">
      <div className="login-card">

        {/* cabeçalho */}
        <div className="login-header">
          <div className="logo-marca">
            <img src={LogoFeather} alt="Logo" style={{ width: 30, height: 30 }} />
            <h1>Senai Notes</h1>
          </div>

          <h2>Welcome to Note</h2>
          <p>Please log in to continue</p>
        </div>

        {/* formulário */}
        <form className="login-form" onSubmit={handleLogin}>

          {/* email */}
          <div className="grupo-input">
            <label>Email Address</label>
            <input
              type="email"
              placeholder="email@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          {/* senha */}
          <div className="grupo-input">

            <div className="label-row">
              <label>Password</label>
              <Link to="/forgot-password" className="link-esqueci">
                Forgot
              </Link>
            </div>

            <div className="input-senha-wrapper">
              <input
                type={mostrarSenha ? "text" : "password"}
                placeholder="••••••••"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
              />

              <button
                type="button"
                className="btn-olho"
                onClick={() => setMostrarSenha(!mostrarSenha)}
              >
                {mostrarSenha ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          <button type="submit" className="btn-login">
            Login
          </button>
        </form>

        {/* rodapé */}
        <div className="login-footer">
          <p>
            No account yet? <Link to="/signup">Sign Up</Link>
          </p>
        </div>

      </div>
    </div>
  )
}
