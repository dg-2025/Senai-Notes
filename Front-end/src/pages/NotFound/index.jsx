// imports principais
import React from 'react'
import { Link } from 'react-router-dom'
import { Linkedin, Github } from 'lucide-react';
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
      <div className="menu-footer">
        <p>Developed by <strong>Daniel</strong></p>
        <div className="social-icons">
          <a href="https://www.linkedin.com/in/daniel-gomes-fullstack" target="_blank" rel="noopener noreferrer" title="LinkedIn">
            <Linkedin size={20} />
          </a>
          <a href="https://github.com/dg-2025" target="_blank" rel="noopener noreferrer" title="GitHub">
            <Github size={20} />
          </a>
        </div>
      </div>
    </div>

  )
}
