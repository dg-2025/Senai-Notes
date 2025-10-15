-- Criar schema do projeto
CREATE SCHEMA IF NOT EXISTS notes;

-- Tabela USUARIO
CREATE TABLE notes.usuario (
    id_usuario SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela NOTA
CREATE TABLE notes.nota (
    id_nota SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    imagem_url TEXT,
    titulo TEXT NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_edicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario)
        REFERENCES notes.usuario(id_usuario)
        ON DELETE CASCADE
);

-- Tabela TAGS
CREATE TABLE notes.tags (
    id_tag SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (id_usuario)
        REFERENCES notes.usuario(id_usuario)
        ON DELETE CASCADE
);

-- Tabela associativa TAG_ANOTACAO
CREATE TABLE notes.tag_anotacao (
    id_tag INT NOT NULL,
    id_nota INT NOT NULL,
    PRIMARY KEY (id_tag, id_nota),
    FOREIGN KEY (id_tag)
        REFERENCES notes.tags(id_tag)
        ON DELETE CASCADE,
    FOREIGN KEY (id_nota)
        REFERENCES notes.nota(id_nota)
        ON DELETE CASCADE
);
