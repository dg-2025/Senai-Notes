// Importações principais
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'

// Estilos globais (valores e padrões do site inteiro)
import './styles/global.css'

// Renderização da aplicação
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
