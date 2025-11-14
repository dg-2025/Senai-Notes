// Importações principais da aplicação
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'

// Importando estilos globais do projeto
import './styles/global.css'

// Renderizando o aplicativo dentro da div root
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
