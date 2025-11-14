import React from 'react';
import './style.css';
import { Home, Search, Archive, Tag, Settings, Plus } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

/* MENU MOBILE */
function MenuMobile({ filtroAtivo, setFiltro, aoCriarNova, showFab = true }) {
    const navigate = useNavigate();

    return (
        <nav className="menu-mobile-container">

            {/* BOTÃO HOME */}
            <button 
                className={`item-mobile ${filtroAtivo === 'all' ? 'ativo' : ''}`}
                onClick={() => setFiltro('all')}
            >
                <Home size={20} />
                <span>Home</span>
            </button>

            {/* BOTÃO BUSCA */}
            <button 
                className="item-mobile"
                onClick={() => {
                    setFiltro('all');
                    setTimeout(() => {
                        document.querySelector('.barra-pesquisa input')?.focus();
                    }, 100);
                }}
            >
                <Search size={20} />
                <span>Search</span>
            </button>

            {/* BOTÃO ARQUIVADOS */}
            <button 
                className={`item-mobile ${filtroAtivo === 'archive' ? 'ativo' : ''}`}
                onClick={() => setFiltro('archive')}
            >
                <Archive size={20} />
                <span>Archived</span>
            </button>

            {/* BOTÃO LISTA DE TAGS */}
            <button 
                className={`item-mobile ${filtroAtivo === 'LISTA_TAGS' ? 'ativo' : ''}`} 
                onClick={() => setFiltro('LISTA_TAGS')}
            >
                <Tag size={20} />
                <span>Tags</span>
            </button>

            {/* BOTÃO CONFIGURAÇÕES */}
            <button 
                className={`item-mobile ${filtroAtivo === 'settings' ? 'ativo' : ''}`} 
                onClick={() => navigate('/settings')}
            >
                <Settings size={20} />
                <span>Settings</span>
            </button>

            {/* BOTÃO FLUTUANTE CRIAR NOVA */}
            {showFab && (
                <button className="fab-criar" onClick={aoCriarNova}>
                    <Plus size={24} color="#FFF" />
                </button>
            )}

        </nav>
    );
}

export default MenuMobile;
