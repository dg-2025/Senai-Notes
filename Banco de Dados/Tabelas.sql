create schema notes
create table notes.usuario (

	id_usuario INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	data_criacao TIMESTAMPTZ,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	senha VARCHAR (12) NOT NULL
	
);
create table notes.nota (

	id_nota INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	id_usuario INT,
	FOREIGN KEY (id_usuario) REFERENCES notes.usuario(id_usuario),
	data_criacao TIMESTAMPTZ,
	ultima_edicao TIMESTAMPTZ,
	titulo TEXT NOT NULL,
	tag TEXT,
	descricao TEXT,
	imagem TEXT,
	arquivado BOOLEAN
);
