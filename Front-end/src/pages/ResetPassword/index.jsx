// imports principais
import React, { useState } from 'react'
import '../Login/style.css'
import { Eye, EyeOff } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import LogoFeather from '../../assets/Feather.svg'

export default function ResetPassword() {

  // navegação
  const navigate = useNavigate()

  // estados da senha
  const [senha, setSenha] = useState('')
  const [confirmaSenha, setConfirmaSenha] = useState('')
  const [mostrar, setMostrar] = useState(false)

  // salvar nova senha
  const handleSave = (e) => {
    e.preventDefault()

    if (senha !== confirmaSenha) {
      alert("As senhas não coincidem!")
      return
    }

    alert("Senha alterada com sucesso!")
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

          <h2>Reset Your Password</h2>
          <p>Choose a new password to secure your account.</p>
        </div>

        {/* formulário */}
        <form className="login-form" onSubmit={handleSave}>

          {/* nova senha */}
          <div className="grupo-input">
            <label>New Password</label>

            <div className="input-senha-wrapper">
              <input
                type={mostrar ? "text" : "password"}
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
              />

              <button
                type="button"
                className="btn-olho"
                onClick={() => setMostrar(!mostrar)}
              >
                {mostrar ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          {/* confirmar senha */}
          <div className="grupo-input">
            <label>Confirm New Password</label>

            <input
              type={mostrar ? "text" : "password"}
              value={confirmaSenha}
              onChange={(e) => setConfirmaSenha(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="btn-login">Reset Password</button>
        </form>

      </div>
    </div>
  )
}
