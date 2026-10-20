# Elicitação de Requisitos — Biblioteca Escolar

## 1. Contexto

A biblioteca de uma escola de ensino técnico controla hoje empréstimos, devoluções e multas em um caderno e em planilhas soltas. Isso causa livros perdidos, multas calculadas na mão (e muitas vezes esquecidas), ausência de histórico por leitor e nenhuma forma de o aluno saber se um livro está disponível sem ir até o balcão.

O sistema **Biblioteca Escolar** vai substituir esse controle por uma aplicação desktop com banco local, com perfis de acesso distintos.

## 2. Partes interessadas

| Parte interessada | Interesse |
|---|---|
| Visitante (pessoa sem cadastro) | Consultar o acervo e se cadastrar como leitor |
| Leitor (aluno ou professor) | Encontrar livros, reservar, acompanhar prazos e multas |
| Bibliotecário | Operar o balcão: empréstimos, devoluções, multas, acervo e leitores |
| Administrador (coordenação) | Definir as regras da biblioteca, gerir a equipe e acompanhar indicadores |

## 3. Técnicas utilizadas

- Entrevista com a bibliotecária responsável (rotina de balcão, problemas atuais).
- Entrevista com a coordenação (regras, prazos, relatórios).
- Conversa com alunos (como procuram livros, o que os incomoda).
- Análise do caderno e das planilhas atuais.

> Nota: esta elicitação foi elaborada pela equipe para fins da disciplina, a partir do contexto de uma biblioteca escolar típica.

## 4. Problemas levantados

1. O aluno não sabe se o livro está disponível e perde a viagem até a biblioteca.
2. Devoluções atrasadas passam despercebidas; a multa é calculada manualmente e com erros.
3. Não há histórico: não se sabe quem está com qual exemplar nem quantos empréstimos o aluno já fez.
4. Livros bastante procurados não têm fila; quem chega primeiro leva, mesmo que outro tenha pedido antes.
5. A coordenação não consegue saber quais livros giram mais nem qual o índice de atrasos.
6. Qualquer pessoa que ajuda no balcão mexe em tudo, sem distinção de responsabilidade.

## 5. Necessidades identificadas (resumo)

- Catálogo consultável por título, autor e categoria, com situação dos exemplares.
- Cadastro de leitores e login com perfis (Leitor, Bibliotecário, Administrador).
- Registro de empréstimo, renovação e devolução, com controle de prazo.
- Cálculo automático de multa por atraso e registro de quitação.
- Reserva de livros indisponíveis, com fila e aviso ao leitor.
- Cadastro do acervo (livros, exemplares, autores, editoras, categorias).
- Parâmetros configuráveis (prazo, valor da multa, limite de empréstimos).
- Relatórios de empréstimos, atrasos e livros mais emprestados.

## 6. Regras de negócio levantadas

| ID | Regra |
|---|---|
| RN01 | O prazo padrão de empréstimo é de 14 dias corridos; a data de devolução prevista é a data do empréstimo mais o prazo. O prazo é configurável pelo Administrador. |
| RN02 | Cada leitor pode ter no máximo 3 empréstimos em aberto ao mesmo tempo (configurável). |
| RN03 | Devolução após a data prevista gera multa = dias de atraso × valor diário (padrão R$ 0,50). Os dias são contados de forma corrida. |
| RN04 | Leitor bloqueado ou com multa pendente não pode retirar livros nem fazer reservas. |
| RN05 | Um empréstimo pode ser renovado até 2 vezes, desde que não esteja atrasado e não haja reserva de outro leitor para o título. Cada renovação soma um novo prazo a partir da data da renovação. |
| RN06 | Reservas formam fila por ordem de data. Quando um exemplar é devolvido, o primeiro da fila é avisado e tem 48 horas para retirá-lo; depois disso a reserva expira e passa ao próximo. |
| RN07 | O acesso às funções depende do perfil: o Visitante apenas consulta e se cadastra; o Leitor opera sobre os próprios dados; o Bibliotecário opera o balcão e o acervo; o Administrador faz tudo isso e ainda gere a equipe, os parâmetros e os relatórios. |
| RN08 | Exemplar emprestado, reservado para retirada ou baixado não pode ser emprestado a outro leitor. |

## 7. Entidades com ciclo de vida

- **Empréstimo:** ATIVO → RENOVADO → ATRASADO → DEVOLVIDO (um empréstimo ativo ou renovado que passa da data prevista torna-se atrasado; qualquer um dos três primeiros estados pode ir a devolvido).
- **Reserva:** AGUARDANDO → DISPONÍVEL → ATENDIDA, com saídas para CANCELADA (pelo leitor) e EXPIRADA (48 h sem retirada).

## 8. Glossário

- **Título (Livro):** a obra, com autor, editora e categoria.
- **Exemplar:** cada cópia física de um título, com seu código de tombo.
- **Tombo:** número de patrimônio que identifica um exemplar.
- **Baixa:** retirada definitiva de um exemplar do acervo (perdido ou danificado).
