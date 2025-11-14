import React from 'react';
import './style.css';
import { Archive, ArchiveRestore, Trash2 } from 'lucide-react';

function AcoesNota({ nota, aoArquivar, aoDeletar }) {

    // nota não selecionada
    if (!nota) return null;

    // estado de arquivamento
    const isArchived = nota.arquivado;

    return (
        <div className="acoes-container">
            <div className="grupo-botoes">

                {/* arquivar ou restaurar */}
                <button 
                    className="btn-acao" 
                    onClick={() => aoArquivar(nota)}
                >
                    {isArchived ? (
                        <>
                            <ArchiveRestore size={18} />
                            Restore Note
                        </>
                    ) : (
                        <>
                            <Archive size={18} />
                            Archive Note
                        </>
                    )}
                </button>

                {/* deletar nota */}
                <button 
                    className="btn-acao btn-delete" 
                    onClick={() => aoDeletar(nota)}
                >
                    <Trash2 size={18} />
                    Delete Note
                </button>

            </div>
        </div>
    );
}

export default AcoesNota;
