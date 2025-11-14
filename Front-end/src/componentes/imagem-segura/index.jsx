// Componente que carrega imagens de forma segura, com fallback e suporte a blob
import React, { useState, useEffect } from 'react'
import api from '../../pages/services/api'
import { Image as ImageIcon, AlertCircle } from 'lucide-react'

// Aceita estilo inline e classe externa
export default function ImagemSegura({ url, className, alt, style }) {
  // Guarda a imagem que será exibida
  const [imagemSrc, setImagemSrc] = useState(null)

  // Controle de carregamento
  const [loading, setLoading] = useState(true)

  // Indica falha ao carregar imagem
  const [erro, setErro] = useState(false)

  // Sempre que a URL mudar, tenta carregar a imagem
  useEffect(() => {
    setLoading(true)
    setErro(false)
    setImagemSrc(null)

    // Caso não tenha URL, nada é exibido
    if (!url) {
      setLoading(false)
      return
    }

    // Se for um File do upload local, cria um blob local
    if (url instanceof File) {
      setImagemSrc(URL.createObjectURL(url))
      setLoading(false)
      return
    }

    // Se já for um blob local, só exibe
    if (typeof url === 'string' && url.startsWith('blob:')) {
      setImagemSrc(url)
      setLoading(false)
      return
    }

    // Se for URL do servidor, busca com Axios
    api
      .get(url, { responseType: 'blob' })
      .then(response => {
        const blobUrl = URL.createObjectURL(response.data)
        setImagemSrc(blobUrl)
        setLoading(false)
      })
      .catch(() => {
        setErro(true)
        setLoading(false)
      })
  }, [url])

  // Se não há URL, nada é renderizado
  if (!url) return null

  // Em caso de erro, mostra placeholder vermelho
  if (erro) {
    return (
      <div
        className={className}
        style={{
          ...style,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          backgroundColor: '#fee',
          color: '#f00'
        }}
      >
        <AlertCircle size={24} />
      </div>
    )
  }

  // Mostra indicador de carregamento enquanto a imagem é baixada
  if (loading) {
    return (
      <div
        className={className}
        style={{
          ...style,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          backgroundColor: '#f3f4f6'
        }}
      >
        <span style={{ fontSize: 12, color: '#999' }}>Carregando...</span>
      </div>
    )
  }

  // Exibe a imagem final
  return (
    <img
      src={imagemSrc}
      alt={alt}
      className={className}
      style={style}
    />
  )
}
