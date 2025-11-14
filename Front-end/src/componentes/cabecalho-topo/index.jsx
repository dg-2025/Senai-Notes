import React from 'react';
import './style.css';
import { Search, Settings, LogOut } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

/* cabeçalho da lista principal */
function CabecalhoTopo({ termoBusca, setBusca }) {

    const navigate = useNavigate();

const handleLogout = () => {
        if(window.confirm("Deseja realmente sair?")) {
            // 1. Apaga o token
            localStorage.removeItem('token');
            localStorage.removeItem('usuarioNome'); // Se você salvou o nome
            
            // 2. Manda pro login
            navigate('/login');
        }
    };

    return (
        <header className="cabecalho-container">

            {/* título da seção */}
            <div className="titulo-secao">
                <h1>All Notes</h1>
            </div>

            {/* barra de busca + ícones */}
            <div className="acoes-topo">

                {/* campo de pesquisa */}
                <div className="barra-pesquisa">
                    <Search size={18} color="#9CA3AF" />
                    <input
                        type="text"
                        placeholder="Pesquisar por título, conteúdo ou tags..."
                        value={termoBusca}
                        onChange={(e) => setBusca(e.target.value)}
                    />
                </div>

                {/* botões de ação */}
                <div className="icones-config">

                    <button
                        className="btn-icon"
                        onClick={() => navigate('/settings')}
                        title="Settings"
                    >
                        <Settings size={20} />
                    </button>

                    <button
                        className="btn-icon"
                        onClick={handleLogout}
                        title="Logout"
                    >
                        <LogOut size={20} />
                    </button>
                </div>
            </div>
        </header>
    );
}

export default CabecalhoTopo;
