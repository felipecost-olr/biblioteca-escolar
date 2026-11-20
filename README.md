# Sistema de Biblioteca Escolar

## Tema
Sistema desktop para informatizar a rotina de uma biblioteca escolar, incluindo consulta ao acervo, leitores, empréstimos, devoluções, renovações, reservas e multas.

## Integrantes
- Allan
- Felipe 
- Hágatha 

## Tecnologias
- Java 21
- JavaFX
- SQLite
- JDBC
- Maven

## Estrutura 



## Como executar
1. Instale o JDK 21.
2. Instale o Maven.
3. Abra o terminal na pasta do projeto.
4. Execute:

```bash
mvn javafx:run
```

O projeto abre uma tela de login e permite criar contas com nome, e-mail, senha e data de nascimento. As contas são salvas no SQLite em `~/.biblioteca-escolar/biblioteca.db`; o login valida o e-mail e a senha e armazena as senhas protegidas por hash.

## Documentação
- `docs/casos-de-uso.md`: visão geral, atores, histórias e casos de uso.
- `docs/diagramas/casos-de-uso.png`: diagrama de casos de uso.
- `docs/diagramas/casos-de-uso.puml`: arquivo-fonte PlantUML do diagrama.
- `docs/backlog.md`: histórias de usuário organizadas por prioridade.
