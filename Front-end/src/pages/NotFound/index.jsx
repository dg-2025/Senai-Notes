// imports principais
import React from 'react'
import { Link } from 'react-router-dom'
import './style.css'
import LogoFeather from '../../assets/Feather.svg'

export default function NotFound() {
  return (
    <div className="notfound-container">

      {/* logo */}
      <img
        src={LogoFeather}
        alt="Logo"
        style={{ width: 50, height: 50 }}
      />

      {/* textos */}
      <h1 className="notfound-title">404</h1>
      <h2 className="notfound-subtitle">Página não encontrada</h2>

      <p className="notfound-text">
        Ops! Parece que a página que você está procurando foi movida ou não existe mais.
      </p>

      {/* botão voltar */}
      <Link to="/dashboard" className="notfound-button">
        Voltar para o Dashboard
      </Link>
    </div>
  )
}
