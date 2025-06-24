# 📝 Correção Automática de Provas - Backend (Java + Spring Boot)

Este projeto é a parte **backend** da aplicação de correção automatizada de provas objetivas, desenvolvida como desafio de Hackathon do 5º período do curso de Sistemas para Internet.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- Bootstrap

## 👥 Funcionalidades

- Login com autenticação por perfil (Admin, Professor, Aluno)
- Cadastro de turmas, disciplinas, alunos e provas objetivas
- Associação de alunos às turmas
- Correção automática de provas com cálculo de nota
- API para recebimento das respostas do app Flutter
- Visualização de estatísticas de desempenho
- Interface web amigável para professores e administradores
- Integração futura com o sistema acadêmico via API

## 🔐 Perfis de Usuário

- `ADMINISTRADOR` - Gerencia o sistema
- `PROFESSOR` - Cadastra e corrige provas
- `ALUNO` - Consulta suas notas

## ⚙️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/HACKATON-5-SEMESTRE/mvcJava_projectGabarito.git
   ```

2. Configure o arquivo `application.properties` com acesso ao banco MySQL:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hackathon_5
   spring.datasource.username=root
   spring.datasource.password=
   ```

3. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

## 🧠 Equipe

- **Daniel Mesquita Oliveira** – RA: 14044 – [@danielsz3](https://github.com/danielsz3)
- **Igor Antonucci** – RA: 13636 – [@IgorQuadros](https://github.com/IgorQuadros)
- **Paulo Ricardo Rigobello Muraro** – RA: 4784 – [@paulorigobello](https://github.com/paulorigobello)
- **Thiago da Silva Felipe** – RA: 13628 – [@ThiagoFelipe01](https://github.com/ThiagoFelipe01)

---

> 📅 Desenvolvido para o Hackathon 5º Semestre – Sistemas para Internet - Faculdade UniAlfa
