import React from 'react';
import './style.css';
import LogoFeather from '../../assets/Feather.svg'
import { Home, Archive, Tag, Linkedin, Github } from 'lucide-react';

/* MENU LATERAL */
function MenuLateral({ filtroAtivo, setFiltro, tagsDisponiveis }) {
    return (
        <nav className="menu-lateral-container">

            {/* LOGO */}
            <div className="logo-section">
                <img src={LogoFeather} alt="Logo" style={{ width: 30, height: 30 }} />
                <h1>Senai Notes</h1>
            </div>

            {/* BOTÕES PRINCIPAIS */}
            <div className="nav-buttons">
                <button 
                    className={`menu-btn ${filtroAtivo === 'all' ? 'ativo' : ''}`}
                    onClick={() => setFiltro('all')}
                >
                    <Home size={20} />
                    Todas as Notas
                </button>

                <button 
                    className={`menu-btn ${filtroAtivo === 'archive' ? 'ativo' : ''}`}
                    onClick={() => setFiltro('archive')}
                >
                    <Archive size={20} />
                    Notas Arquivadas
                </button>
            </div>

            {/* LISTA DE TAGS */}
            <div className="tags-section">
                <h2>Tags</h2>

                <div className="tags-list">

                    {/* AVISO SEM TAGS */}
                    {tagsDisponiveis.length === 0 && (
                        <span style={{ fontSize: 12, color: '#ccc', paddingLeft: 12 }}>
                            Sem tags criadas
                        </span>
                    )}

                    {/* TAGS GERADAS */}
                    {tagsDisponiveis.map((tag) => (
                        <button 
                            key={tag}
                            className={`tag-btn ${filtroAtivo === tag ? 'ativo-tag' : ''}`}
                            onClick={() => setFiltro(tag)}
                        >
                            <Tag size={16} /> 
                            {tag}
                        </button>
                    ))}
                </div>
            </div>
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
        </nav>
        
    );
}

export default MenuLateral;
