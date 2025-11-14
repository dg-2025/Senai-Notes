// imports principais
import React, { useState } from 'react'
import '../Login/style.css' // reaproveitando o CSS do login
import { Eye, EyeOff } from 'lucide-react'
import { Link, useNavigate } from 'react-router-dom'
import LogoFeather from '../../assets/Feather.svg'

export default function Signup() {

  // navegação
  const navigate = useNavigate()

  // estados do formulário
  const [mostrarSenha, setMostrarSenha] = useState(false)
  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')

  // envio do cadastro
  const handleSignup = (e) => {
    e.preventDefault()
    console.log("Cadastrando:", nome, email, senha)
    alert("Conta criada com sucesso!")
    navigate('/login')
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

          <h2>Create Your Account</h2>
          <p>Sign up to start organizing your notes.</p>
        </div>

        {/* formulário */}
        <form className="login-form" onSubmit={handleSignup}>

          {/* campo nome */}
          <div className="grupo-input">
            <label>Full Name</label>
            <input
              type="text"
              placeholder="Ex: Daniel Galdino"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
            />
          </div>

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
            <label>Password</label>

            <div className="input-senha-wrapper">
              <input
                type={mostrarSenha ? "text" : "password"}
                placeholder="At least 8 characters"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
                minLength={8}
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

          <button type="submit" className="btn-login">Sign Up</button>
        </form>

        {/* footer */}
        <div className="login-footer">
          <p>
            Already have an account? <Link to="/login">Login</Link>
          </p>
        </div>

      </div>
    </div>
  )
}
