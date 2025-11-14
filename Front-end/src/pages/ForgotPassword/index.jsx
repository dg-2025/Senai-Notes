// imports principais
import React, { useState } from 'react'
import '../Login/style.css'
import { ArrowLeft } from 'lucide-react'
import { Link } from 'react-router-dom'
import LogoFeather from '../../assets/Feather.svg'

export default function ForgotPassword() {

  // estado do email
  const [email, setEmail] = useState('')

  // envio do formulário
  const handleReset = (e) => {
    e.preventDefault()
    alert(`Link de recuperação enviado para ${email}`)
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

          <h2>Forgotten your password?</h2>
          <p>Enter your email below, and we’ll send you a link to reset it.</p>
        </div>

        {/* formulário */}
        <form className="login-form" onSubmit={handleReset}>
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

          <button type="submit" className="btn-login">
            Send Reset Link
          </button>
        </form>

        {/* voltar */}
        <div className="login-footer">
          <Link
            to="/login"
            style={{
              display: 'flex',
              alignItems: 'center',
              gap: 5,
              justifyContent: 'center'
            }}
          >
            <ArrowLeft size={16} /> Back to Login
          </Link>
        </div>

      </div>
    </div>
  )
}
