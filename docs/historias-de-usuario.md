# Histórias de Usuário — Product Backlog inicial

Ordenadas por prioridade (Alta → Média → Baixa). Cada história deve ser cadastrada como issue no GitHub com o rótulo do papel e da prioridade (veja `docs/como-criar-issues.md`).

Critério de priorização: o que sustenta o fluxo principal do balcão (acervo, empréstimo, devolução, multa) vem primeiro; autoatendimento do leitor em seguida; gestão e indicadores por último.

## Prioridade Alta

| ID | Papel | História |
|---|---|---|
| US01 | Bibliotecário | Como bibliotecário, quero cadastrar livros e seus exemplares, para manter o acervo organizado e saber o que existe na biblioteca. |
| US02 | Visitante | Como visitante, quero consultar o catálogo por título, autor ou categoria e ver quantos exemplares estão disponíveis, para saber se vale a pena ir à biblioteca. |
| US03 | Leitor / Bibliotecário / Administrador | Como usuário cadastrado, quero entrar no sistema com login e senha, para acessar apenas as funções do meu perfil. |
| US04 | Visitante | Como visitante, quero me cadastrar como leitor, para poder reservar e pegar livros emprestados. |
| US05 | Bibliotecário | Como bibliotecário, quero registrar o empréstimo de um exemplar a um leitor, para que a retirada fique registrada com a data de devolução prevista. |
| US06 | Bibliotecário | Como bibliotecário, quero registrar a devolução de um exemplar, para encerrar o empréstimo e liberar o exemplar. |
| US07 | Bibliotecário | Como bibliotecário, quero que o sistema impeça empréstimo a leitor bloqueado, com multa pendente ou acima do limite de empréstimos, para aplicar as regras da biblioteca sem conferência manual. |
| US08 | Bibliotecário | Como bibliotecário, quero que a multa por atraso seja calculada automaticamente na devolução, para eliminar erros e esquecimentos. |

## Prioridade Média

| ID | Papel | História |
|---|---|---|
| US09 | Leitor | Como leitor, quero reservar um livro sem exemplares disponíveis, para entrar na fila e ser atendido por ordem. |
| US10 | Leitor | Como leitor, quero ver meus empréstimos, prazos, reservas e multas, para não perder datas e não ser pego de surpresa. |
| US11 | Leitor | Como leitor, quero renovar um empréstimo antes de vencer, para ficar mais tempo com o livro sem ir ao balcão. |
| US12 | Bibliotecário | Como bibliotecário, quero registrar o pagamento de uma multa, para liberar o leitor a pegar novos livros. |
| US13 | Bibliotecário | Como bibliotecário, quero cadastrar autores, editoras e categorias, para classificar corretamente os livros. |
| US14 | Bibliotecário | Como bibliotecário, quero cadastrar, editar e bloquear leitores, para manter a base de leitores correta. |
| US15 | Administrador | Como administrador, quero cadastrar e desativar bibliotecários, para controlar quem opera o balcão. |
| US16 | Administrador | Como administrador, quero configurar prazo de empréstimo, valor da multa e limite de empréstimos, para ajustar as regras sem alterar o sistema. |
| US17 | Leitor | Como leitor, quero cancelar uma reserva, para liberar minha vez na fila quando não precisar mais do livro. |
| US18 | Leitor | Como leitor, quero ser avisado quando o livro que reservei estiver disponível, para retirá-lo dentro do prazo. |

## Prioridade Baixa

| ID | Papel | História |
|---|---|---|
| US19 | Administrador | Como administrador, quero um relatório de empréstimos e atrasos por período, para acompanhar a inadimplência. |
| US20 | Administrador | Como administrador, quero ver os livros mais emprestados, para decidir quais comprar ou repor. |
