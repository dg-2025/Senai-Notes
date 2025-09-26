--Criar schema
create schema notes

--Criar Tabela de usuario
create table notes.usuario (
	id_usuario INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	senha VARCHAR (12) NOT NULL,
	data_criacao TIMESTAMPTZ
	
);

--Criar Tabela de Tags
create table notes.tags(
	id_tag int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	id_usuario int,
	FOREIGN KEY (id_usuario) REFERENCES notes.usuario(id_usuario),
	nome text not null,
	descricao text,
	data_criacao TIMESTAMPTZ
);

--Criar Tabela de Nota
create table notes.nota (

	id_nota INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	id_usuario INT,
	FOREIGN KEY (id_usuario) REFERENCES notes.usuario(id_usuario),
	titulo TEXT NOT NULL,
	id_tag int,
	FOREIGN KEY (id_tag) REFERENCES notes.tags(id_tag),
	descricao TEXT,
	imagem TEXT,
	arquivado BOOLEAN,
	data_criacao TIMESTAMPTZ,
	ultima_edicao TIMESTAMPTZ
);

select * from notes.usuario

