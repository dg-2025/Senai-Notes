// imports principais
import React, { useEffect } from 'react'
import './style.css'
import { CheckCircle, XCircle, X } from 'lucide-react'

function ToastNotificacao({
  isOpen,
  onClose,
  message,
  type = 'success',      // success ou error
  linkText,              // texto opcional
  linkAction             // função opcional
}) {

  // se o toast não estiver aberto, não renderiza
  if (!isOpen) return null

  // fecha automático após 4 segundos
  useEffect(() => {
    const timer = setTimeout(() => onClose(), 4000)
    return () => clearTimeout(timer)
  }, [isOpen, onClose])

  // ícone e cor dependendo do tipo
  const Icon = type === 'success' ? CheckCircle : XCircle
  const color = type === 'success' ? "#22C55E" : "#EF4444"

  return (
    <div className={`toast-container toast-${type}`}>

      {/* conteúdo principal */}
      <div className="toast-content">
        <Icon size={20} color={color} />
        <span>{message}</span>

        {/* link opcional */}
        {linkText && linkAction && (
          <button className="toast-link" onClick={linkAction}>
            {linkText}
          </button>
        )}
      </div>

      {/* botão fechar */}
      <button className="toast-close-btn" onClick={onClose}>
        <X size={16} />
      </button>
    </div>
  )
}

export default ToastNotificacao
