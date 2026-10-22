# Biblioteca Escolar

Sistema desktop para controle de uma biblioteca escolar: catálogo, empréstimos, renovações, devoluções, reservas com fila, multas por atraso e relatórios.

Projeto da disciplina **Análise e Projeto de Sistemas** — IFPI, Campus Picos.

## Tema

Informatização de uma biblioteca de escola técnica, com quatro perfis de acesso: **Visitante**, **Leitor**, **Bibliotecário** e **Administrador**.

## Integrantes

<!-- Preencha com os nomes da equipe (até 4 integrantes) -->
- Felipe
- Hágatha 
- Allan
- nikolas


## Tecnologias

Java 21 · JavaFX · SQLite (sqlite-jdbc, arquivo local criado na primeira execução) · JDBC · Maven · Git/GitHub

## Como executar

Pré-requisitos: JDK 21 e Maven instalados.

```bash
git clone <url-do-repositorio>
cd biblioteca-escolar
mvn javafx:run
```

Na semana 1 o comando abre uma janela vazia, que será ampliada a cada entrega.

## Documentação (pasta `docs/`)

| Arquivo | Conteúdo |
|---|---|
| `docs/elicitacao.md` | Contexto, problemas, necessidades e regras de negócio |
| `docs/historias-de-usuario.md` | Product Backlog inicial (US01–US20) por prioridade |
| `docs/casos-de-uso.md` | Visão geral, atores, casos de uso (UC01–UC19), rastreabilidade e especificações |
| `docs/diagramas/casos-de-uso.png` | Diagrama de casos de uso |
| `docs/diagramas/casos-de-uso.dot` | Arquivo-fonte do diagrama (Graphviz) |

Para regenerar o diagrama: `cd docs/diagramas && dot -Tpng -Gdpi=110 casos-de-uso.dot -o casos-de-uso.png`

## Product Backlog

As histórias de usuário estão em `docs/historias-de-usuario.md` e devem estar cadastradas como issues deste repositório. O script `scripts/criar-issues.sh` cria todas (veja `docs/como-criar-issues.md`).

## Entregas semanais

| Semana | Entrega |
|---|---|
| 1 | Repositório, casos de uso e diagrama de casos de uso |
