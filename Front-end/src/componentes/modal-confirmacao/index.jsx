// imports principais
import React from 'react'
import './style.css'
import { Archive, Trash2 } from 'lucide-react'

function ModalConfirmacao({
  isOpen,
  onClose,
  onConfirm,
  title,
  message,
  confirmButtonText,
  confirmButtonColor = 'primary', // primary (azul) | danger (vermelho)
  iconType                                       // delete | archive
}) {

  // modal fechado → não renderiza
  if (!isOpen) return null

  // ícone dinâmico
  const Icon = iconType === 'delete' ? Trash2 : Archive

  return (
    <div className="modal-overlay">
      <div className="modal-card">

        {/* cabeçalho */}
        <div className="modal-header">
          <Icon
            size={24}
            color={iconType === 'delete' ? "#EF4444" : "#2563EB"}
          />
          <h2>{title}</h2>
        </div>

        {/* corpo */}
        <div className="modal-body">
          <p>{message}</p>
        </div>

        {/* botões */}
        <div className="modal-footer">
          <button className="btn-modal-cancel" onClick={onClose}>
            Cancel
          </button>

          <button
            className={`btn-modal-confirm btn-${confirmButtonColor}`}
            onClick={onConfirm}
          >
            {confirmButtonText}
          </button>
        </div>

      </div>
    </div>
  )
}

export default ModalConfirmacao
