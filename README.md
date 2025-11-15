<p align="center">
  <img src="[LINK PARA docs/Feather.svg]" width="100" alt="Logo Senai Notes">
</p>

<h1 align="center">
  Senai Notes (Projeto Fullstack)
</h1>

<p align="center">
  <strong>Um sistema de anotações completo, seguro e responsivo, construído com React, Java (Spring Boot) e implantado em uma arquitetura de nuvem 100% desacoplada (Vercel, Render, AWS RDS & S3).</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Frontend-React_(Vite)-61DAFB?logo=react" alt="React">
  <img src="https://img.shields.io/badge/Backend-Java_Spring-6DB33F?logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Storage-Amazon_S3-569A31?logo=amazons3" alt="Amazon S3">
  <img src="https://img.shields.io/badge/Deploy_Front-Vercel-000000?logo=vercel" alt="Vercel">
  <img src="https://img.shields.io/badge/Deploy_Back-Render-46E3B7?logo=render" alt="Render">
</p>

<p align="center">
  <a href="https://senai-notes-xi.vercel.app/login">
    <img src="https://img.shields.io/badge/Aplicação_Online-Acessar_Deploy-blue?style=for-the-badge" alt="Deploy do Front-end">
  </a>
  &nbsp;
  <a href="https://senai-notes.onrender.com/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/API_Docs-Ver_Swagger-green?style=for-the-badge" alt="Deploy do Back-end">
  </a>
</p>

<p align="center">
  <a href="https://git.io/typing-svg">
    <img src="https://readme-typing-svg.herokuapp.com?color=%2336BCF7&center=true&vCenter=true&width=600&lines=Projeto+Fullstack+completo+Senai+2025;+Desenvolvido+por+Daniel+Gomes;+React+%2B+Java+%2B+Cloud+AWS+%26+Render" alt="Typing SVG">
  </a>
</p>

---

## 🎞️ Demonstração em Ação

O design foi 100% baseado no Figma, implementado com temas Light e Dark Mode e totalmente responsivo para desktop e mobile.

*(DICA: Grave sua tela usando o **ScreenToGif** ou **LICEcap**, arraste o GIF para a pasta `docs` e substitua as imagens abaixo!)*

<table>
  <thead>
    <tr>
      <th align="center">Modo Light (Desktop)</th>
      <th align="center">Modo Dark (Desktop)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><img src="[LINK PARA docs/dashboard.jpg]" alt="Dashboard Light Mode"></td>
      <td><img src="[LINK PARA docs/dashboard-noturno.jpg]" alt="Dashboard Dark Mode"></td>
    </tr>
  </tbody>
</table>

### Responsividade Mobile (Fiel ao Figma)
<p align="center">
  <img src="[LINK PARA docs/mobile-todas as telas.jpg]" alt="Demonstração Mobile" width="80%">
</p>

---

## 🎯 Sobre o Projeto (O "Baguio Técnico")

O Senai Notes é uma aplicação Fullstack completa que simula um ambiente de produção real. O objetivo principal era ir além do CRUD básico, implementando uma **arquitetura de nuvem segura, escalável e desacoplada**, resolvendo desafios complexos de infraestrutura.

O maior desafio foi garantir o **armazenamento persistente de imagens** (`Uploads`). Diferente de um ambiente local (onde a pasta `/uploads` funciona), servidores de nuvem (Render/Elastic Beanstalk) possuem **armazenamento efêmero** (temporário). A solução foi integrar o **Amazon S3** para o armazenamento de objetos, garantindo que os dados não fossem perdidos após um reinício da API.

---

## ✨ Funcionalidades (Features)

<details>
  <summary><strong>🛡️ 1. Autenticação e Segurança (JWT)</strong></summary>
  <br>
  <ul>
    <li>Sistema completo de autenticação via <strong>JSON Web Tokens (JWT)</strong>.</li>
    <li>Rotas de back-end protegidas com <strong>Spring Security</strong> (só acessa com token válido).</li>
    <li>Rotas de front-end protegidas (se não houver token, o usuário vê <strong>404</strong> em vez da tela de login - o "modo stealth").</li>
    <li>Criptografia de senhas no banco de dados usando <strong>BCrypt (PasswordEncoder)</strong>.</li>
    <li>Fluxo completo de <strong>Cadastro</strong>, <strong>Login</strong>, <strong>Recuperação de Senha</strong> (com envio de e-mail) e <strong>Troca de Senha</strong> (validando a senha antiga).</li>
  </ul>
  
  <p align="center">
    <img src="[LINK PARA docs/Tela-Login.png]" width="32%">
    <img src="[LINK PARA docs/Tela-criarConta.png]" width="32%">
    <img src="[LINK PARA docs/Tela-recuperar-Senha.png]" width="32%">
  </p>
  
  <p align="center">
    <strong>Prova do Envio de E-mail (Recuperação de Senha):</strong><br>
    <img src="[LINK PARA docs/WhatsApp Image 2025-11-15 at 02.59.49 (1).jpeg]" width="300">
  </p>
</details>

<details>
  <summary><strong>📝 2. Gestão de Notas e UI/UX</strong></summary>
  <br>
  <ul>
    <li><strong>CRUD Completo</strong> de Notas (Criar, Ler, Atualizar, Deletar).</li>
    <li><strong>Upload de Imagem:</strong> Envio de capa da nota (via <code>FormData</code>) para a API, que processa e envia ao S3.</li>
    <li><strong>Leitura de Imagem Segura:</strong> O front-end usa <code>Axios Interceptor</code> (com Token) para requisitar a imagem, e o back-end retorna um <i>redirect</i> para a URL do S3.</li>
    <li><strong>Filtros e Busca:</strong> Filtro em tempo real por "Ativas", "Arquivadas", "Tags" e busca por texto.</li>
    <li><strong>Feedback Visual:</strong> Implementação de <strong>Toasts</strong> (alertas) e <strong>Modais</strong> de confirmação.</li>
    <li><strong>Tema Light/Dark:</strong> Troca de tema instantânea (CSS Variables) com persistência no <code>localStorage</code>.</li>
  </ul>
  
  <p align="center">
    <strong>Dashboard Principal</strong><br>
    <img src="[LINK PARA docs/image_fe916b.jpg]" width="80%">
  </p>
  
  <p align="center">
    <strong>Tela de Configurações (Light/Dark) e Modais de Confirmação</strong><br>
    <img src="[LINK PARA docs/configurações.png]" width="45%">
    <img src="[LINK PARA docs/image_ffdc3f.png]" width="45%">
  </p>
</details>

---

## 🛠️ Stack de Tecnologias

| Área | Tecnologia | Motivo da Escolha |
| :--- | :--- | :--- |
| **Frontend** | **React (com Vite)** | UI reativa e rápida, com `useState` e `useEffect` para gerenciamento de estado. |
| **Backend** | **Java 17 + Spring Boot** | API RESTful com Spring Data JPA e `Spring Security` para segurança. |
| **Banco de Dados**| **PostgreSQL** | Banco de dados relacional robusto para garantir a integridade dos dados. |
| **Armazenamento** | **Amazon S3** | A solução definitiva para armazenamento de arquivos estáticos (imagens) na nuvem. |
| **Deploy (Infra)** | **Vercel** (Front) + **Render** (Back) | Pipeline de CI/CD grátis, rápido e com HTTPS automático (resolvendo o "Mixed Content"). |
| **Infra (Banco)** | **AWS RDS** | Banco de dados gerenciado pela AWS, desacoplado da API para maior segurança e escalabilidade. |
| **Estilização** | CSS Puro (com Variáveis) | Para controle total do Dark Mode e da responsividade (Media Queries). |

---

## <details><summary>🏛️ Arquitetura e Modelagem de Dados</summary>

### Modelo Conceitual e Lógico
O sistema foi modelado para que um Usuário possua Notas e Tags, e as Notas se relacionem com as Tags (N:N, resultando na tabela `tags` que atua como associativa e principal).

<p align="center">
  <img src="[LINK PARA docs/modelagem.png]" alt="Modelo de Dados" width="700">
</p>

### Documentação da API (Swagger)
A API foi 100% documentada com SpringDoc (OpenAPI) e está disponível publicamente.

**Link da API:** [https://senai-notes.onrender.com/swagger-ui/index.html](https://senai-notes.onrender.com/swagger-ui/index.html)

<p align="center">
  <img src="[LINK PARA docs/login-controller-swagger.png]" alt="Swagger Login" width="80%">
  <img src="[LINK PARA docs/usuario-controller-swagger.png]" alt="Swagger Usuário" width="80%">
  <img src="[LINK PARA docs/nota-controller-swagger.png]" alt="Swagger Notas" width="80%">
  <img src="[LINK PARA docs/tag-controller-swagger.png]" alt="Swagger Tags" width="80%">
</p>

</details>

---

## <details><summary>🧗 A Jornada: Desafios do Deploy (Erros Reais)</summary>
<br>
O deploy não foi simples. A integração de 4 serviços de nuvem (Vercel, Render, RDS, S3) gerou desafios reais de infraestrutura:

1.  **O Erro `502 Bad Gateway` (API vs Banco):**
    * **Problema:** A API no Render/Beanstalk crashava (dava 502) porque o Firewall (Security Group) do AWS RDS bloqueava a conexão.
    * **Solução:** Configurar as **Inbound Rules** do RDS para permitir tráfego da porta `5432` vindo dos IPs do Render (ou `0.0.0.0/0` para testes).

2.  **O Erro `Mixed Content` (HTTP vs HTTPS):**
    * **Problema:** O Front-end (Vercel) é `HTTPS` 🔒, mas o deploy inicial da API (Elastic Beanstalk) era `HTTP`. O navegador bloqueia essa conexão.
    * **Solução:** Migrar a API do Beanstalk para o **Render.com**, que fornece HTTPS de graça.

3.  **O Erro `NonUniqueResultException` (Java):**
    * **Problema:** O banco estava com tags duplicadas, e o Java esperava um resultado único (`Optional<Tag>`).
    * **Solução:** Refatorar o `TagRepository` para retornar `List<Tag>` e tratar a duplicidade no `NotaService`.
</details>

---

## 👨‍💻 Autor

<p align="center">
  Feito com ☕ e muito código por <strong>Daniel Gomes</strong>.
</p>
<p align="center">
  <a href="https://www.linkedin.com/in/daniel-gomes-fullstack" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-Daniel%20Gomes-blue?logo=linkedin" alt="LinkedIn">
  </a>
  &nbsp;&nbsp;&nbsp;
  <a href="https://github.com/dg-2025" target="_blank">
    <img src="https://img.shields.io/badge/GitHub-dg--2025-black?logo=github" alt="GitHub">
  </a>
</p>
