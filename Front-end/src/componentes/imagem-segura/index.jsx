import React from 'react'

export default function ImagemSegura({ url, className, alt, style }) {

  if (!url) {
    return (
      <div
        className={className}
        style={{
          ...style,
          backgroundColor: '#f3f4f6'
        }}
      />
    )
  }

  return (
    <img
      src={url}
      alt={alt}
      className={className}
      style={style}
      onError={(e) => {
        e.target.src =
          'https://via.placeholder.com/300x300/ffdddd/ff0000?text=Erro'
      }}
    />
  )
}
