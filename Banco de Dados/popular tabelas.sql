--Criar usuarios 
insert into notes.usuario (nome, email, senha, data_criacao)
values ('daniel', 'daniel@gmail.com', '1234', now())

--Criar tags 
insert into notes.tags (id_tag, id_usuario, data_criacao, descricao, nome) values
(1, 1, now(), 'Tarefas relacionadas ao backend', 'Backend'),
(2, 1, now(), 'Atividades de front-end e design', 'Frontend'),
(3, 1, now(), 'Notas e lembretes de estudo', 'Estudos'),
(4, 1, now(), 'Tags para organização de projetos', 'Projetos'),
(5, 1, now(), 'Anotações pessoais e ideias rápidas', 'Pessoal');

--Criar notas
insert into notes.nota 
(arquivado, id_tag, id_usuario, data_criacao, ultima_edicao, descricao, imagem, titulo) 
values
(false, 1, 1, now(), now(), 'Anotações sobre o backend da aplicação', null, 'Notas Backend'),
(false, 2, 1, now(), now(), 'Checklist de tarefas do frontend', null, 'Notas Frontend'),
(false, 3, 1, now(), now(), 'Resumo das aulas de ADS', null, 'Notas de Estudos'),
(false, 4, 1, now(), now(), 'Planejamento do sistema de projetos', null, 'Notas de Projetos'),
(false, 5, 1, now(), now(), 'Ideias pessoais e rápidas', null, 'Notas Pessoais');


