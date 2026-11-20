# Documento de Casos de Uso — Sistema de Biblioteca Escolar

## 1. Visão geral

O Sistema de Biblioteca Escolar é uma aplicação desktop que informatiza a rotina de uma biblioteca de escola: controle do acervo e dos leitores (alunos e professores), empréstimos, devoluções, renovações, reservas e cobrança de multas por atraso. O bibliotecário opera o balcão, os leitores consultam o acervo e acompanham seus empréstimos, e o administrador gerencia usuários, perfis de acesso e relatórios. O acesso é feito mediante autenticação, com permissões distintas por perfil.

## 2. Atores

| Ator | Descrição |
|---|---|
| Leitor (abstrato) | Generalização de Aluno e Professor; reúne o que ambos fazem no sistema. |
| Aluno | Estudante da escola que consulta o acervo, renova, reserva e acompanha seus empréstimos e multas. |
| Professor | Docente da escola; faz tudo o que o Aluno faz e também sugere aquisição de livros. |
| Bibliotecário | Funcionário responsável pelo balcão: gerencia acervo e leitores, registra empréstimos, devoluções e pagamento de multas. |
| Administrador | Gerencia usuários e perfis de acesso e emite relatórios gerenciais. |

## 3. Histórias de usuário (Product Backlog inicial)

| ID | Papel | História |
|---|---|---|
| US01 | Todos | Como usuário, quero entrar no sistema com login e senha para acessar as funções do meu perfil. |
| US02 | Administrador | Como administrador, quero cadastrar usuários e definir seus perfis para controlar quem acessa o quê. |
| US03 | Bibliotecário | Como bibliotecário, quero cadastrar e manter os livros e seus exemplares para manter o acervo atualizado. |
| US04 | Bibliotecário | Como bibliotecário, quero cadastrar e manter alunos e professores para que possam pegar livros. |
| US05 | Bibliotecário | Como bibliotecário, quero manter categorias, autores e editoras para organizar o acervo. |
| US06 | Aluno | Como aluno, quero pesquisar livros por título, autor ou categoria e ver se há exemplar disponível. |
| US07 | Bibliotecário | Como bibliotecário, quero registrar o empréstimo de um exemplar a um leitor. |
| US08 | Bibliotecário | Como bibliotecário, quero que o sistema impeça empréstimo a leitor com multa pendente ou acima do limite de livros. |
| US09 | Bibliotecário | Como bibliotecário, quero registrar a devolução de um exemplar. |
| US10 | Bibliotecário | Como bibliotecário, quero que o sistema calcule a multa quando a devolução for atrasada. |
| US11 | Aluno | Como aluno, quero renovar meu empréstimo para ficar mais tempo com o livro. |
| US12 | Aluno | Como aluno, quero reservar um livro sem exemplar disponível para ser atendido quando devolvido. |
| US13 | Bibliotecário | Como bibliotecário, quero registrar o pagamento de multas para liberar o leitor. |
| US14 | Aluno | Como aluno, quero ver meus empréstimos, prazos e multas para não atrasar. |
| US15 | Administrador | Como administrador, quero relatórios de empréstimos, atrasos e livros mais emprestados para apoiar decisões. |
| US16 | Professor | Como professor, quero sugerir a compra de um livro para ampliar o acervo. |

## 4. Lista de casos de uso

| ID | Nome | Ator principal | Histórias de origem |
|---|---|---|---|
| UC01 | Autenticar no sistema | Todos os atores | US01 |
| UC02 | Gerenciar usuários e perfis | Administrador | US02 |
| UC03 | Gerenciar acervo | Bibliotecário | US03 |
| UC04 | Gerenciar leitores | Bibliotecário | US04 |
| UC05 | Gerenciar categorias, autores e editoras | Bibliotecário | US05 |
| UC06 | Consultar acervo | Leitor (Aluno/Professor) | US06 |
| UC07 | Registrar empréstimo | Bibliotecário | US07 |
| UC08 | Validar elegibilidade do leitor | (incluído por UC07, UC11, UC12) | US08 |
| UC09 | Registrar devolução | Bibliotecário | US09 |
| UC10 | Calcular multa por atraso | (estende UC09) | US10 |
| UC11 | Renovar empréstimo | Leitor (Aluno/Professor) | US11 |
| UC12 | Reservar livro | Leitor (Aluno/Professor) | US12 |
| UC13 | Registrar pagamento de multa | Bibliotecário | US13 |
| UC14 | Consultar meus empréstimos e multas | Leitor (Aluno/Professor) | US14 |
| UC15 | Emitir relatórios | Bibliotecário, Administrador | US15 |
| UC16 | Sugerir aquisição de livro | Professor | US16 |

Casos de uso CRUD: UC02, UC03, UC04 e UC05 (4 de 16, dentro do limite de metade).

## 5. Relações de inclusão e extensão

## 5. Especificação dos casos de uso

### Regras de negócio gerais
- **RN01:** o aluno pode ter no máximo 3 empréstimos ativos e o professor, no máximo 5.
- **RN02:** o prazo de empréstimo é de 7 dias para aluno e 14 dias para professor.
- **RN03:** a renovação é permitida uma única vez por empréstimo, por mais 7 dias, e só se não houver reserva pendente para o livro.
- **RN04:** a multa por atraso é de R$ 0,50 por dia de atraso, por exemplar.
- **RN05:** leitor com multa pendente ou empréstimo atrasado não pode fazer empréstimo, renovação nem reserva.
- **Ciclo de vida do Empréstimo:** Ativo → Renovado → Atrasado → Devolvido (Ativo e Renovado passam a Atrasado ao vencer o prazo; Ativo e Renovado passam a Devolvido na devolução).

---

### UC01 — Autenticar no sistema
- **Ator principal:** Todos os atores
- **Histórias de origem:** US01
- **Pré-condição:** o usuário possui cadastro ativo.
- **Pós-condição:** o usuário está autenticado e vê apenas as funções do seu perfil.

**Fluxo principal**
1. O sistema exibe a tela de login.
2. O ator informa login e senha.
3. O ator escolhe a opção Entrar.
4. O sistema verifica o login e a senha.
5. O sistema identifica o perfil do usuário e abre a tela inicial correspondente.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Login ou senha incorretos** (ao passo 4)
1. O sistema exibe mensagem de erro.
2. Volta ao passo 2 do fluxo principal.

**Fluxo alternativo A2 — Campos em branco** (ao passo 3)
1. O sistema informa que login e senha são obrigatórios.
2. Volta ao passo 2 do fluxo principal.

**Fluxo alternativo A3 — Usuário inativo** (ao passo 4)
1. O sistema informa que o usuário está inativo e encerra o caso de uso sem conceder acesso.

---

### UC02 — Gerenciar usuários e perfis
- **Ator principal:** Administrador
- **Histórias de origem:** US02
- **Pré-condição:** o administrador está autenticado.
- **Pós-condição:** os dados de usuários e perfis ficam atualizados no banco.

**Fluxo principal (cadastrar usuário)**
1. O administrador escolhe a opção Novo usuário.
2. O sistema exibe o formulário (nome, login, senha, perfil).
3. O administrador preenche os dados e confirma.
4. O sistema valida os dados e verifica se o login é único.
5. O sistema grava o usuário e exibe mensagem de sucesso.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Consultar, alterar ou desativar usuário** (ao passo 1)
1. O administrador pesquisa e seleciona um usuário.
2. O administrador escolhe alterar dados, trocar o perfil ou desativar.
3. O sistema grava a alteração e volta ao passo 5 do fluxo principal.

**Fluxo alternativo A2 — Login já existente ou dados inválidos** (ao passo 4)
1. O sistema informa o erro.
2. Volta ao passo 3 do fluxo principal.

---

### UC03 — Gerenciar acervo
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US03
- **Pré-condição:** o bibliotecário está autenticado.
- **Pós-condição:** livros e exemplares ficam atualizados no banco.

**Fluxo principal (cadastrar livro e exemplares)**
1. O bibliotecário escolhe a opção Novo livro.
2. O sistema exibe o formulário (título, autor, editora, categoria, ISBN, ano, quantidade de exemplares).
3. O bibliotecário preenche os dados e confirma.
4. O sistema valida os dados.
5. O sistema grava o livro e gera os exemplares com código de tombo e situação Disponível.
6. O sistema exibe mensagem de sucesso e encerra o caso de uso.

**Fluxo alternativo A1 — Consultar, alterar ou excluir livro** (ao passo 1)
1. O bibliotecário pesquisa e seleciona um livro.
2. O bibliotecário escolhe alterar ou excluir.
3. O sistema grava a alteração e volta ao passo 6 do fluxo principal.

**Fluxo alternativo A2 — Dados inválidos ou ISBN duplicado** (ao passo 4)
1. O sistema informa o erro.
2. Volta ao passo 3 do fluxo principal.

**Fluxo alternativo A3 — Livro com exemplar emprestado** (em A1, ao excluir)
1. O sistema informa que não é possível excluir e encerra a operação.

---

### UC04 — Gerenciar leitores
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US04
- **Pré-condição:** o bibliotecário está autenticado.
- **Pós-condição:** os dados de leitores ficam atualizados no banco.

**Fluxo principal (cadastrar leitor)**
1. O bibliotecário escolhe a opção Novo leitor.
2. O sistema exibe o formulário (nome, matrícula, tipo Aluno/Professor, turma, contato).
3. O bibliotecário preenche os dados e confirma.
4. O sistema valida os dados e verifica se a matrícula é única.
5. O sistema grava o leitor e exibe mensagem de sucesso.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Consultar, alterar ou inativar leitor** (ao passo 1)
1. O bibliotecário pesquisa e seleciona um leitor.
2. O bibliotecário escolhe alterar ou inativar.
3. O sistema grava a alteração e volta ao passo 5 do fluxo principal.

**Fluxo alternativo A2 — Matrícula já cadastrada ou dados inválidos** (ao passo 4)
1. O sistema informa o erro.
2. Volta ao passo 3 do fluxo principal.

**Fluxo alternativo A3 — Leitor com empréstimo ativo ou multa pendente** (em A1, ao inativar)
1. O sistema informa que não é possível inativar e encerra a operação.

---

### UC05 — Gerenciar categorias, autores e editoras
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US05
- **Pré-condição:** o bibliotecário está autenticado.
- **Pós-condição:** categorias, autores e editoras ficam atualizados no banco.

**Fluxo principal (cadastrar item)**
1. O bibliotecário escolhe o tipo de cadastro (categoria, autor ou editora).
2. O sistema exibe o formulário com os campos do tipo escolhido.
3. O bibliotecário preenche o nome e confirma.
4. O sistema valida os dados e verifica duplicidade.
5. O sistema grava o item e exibe mensagem de sucesso.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Consultar, alterar ou excluir item** (ao passo 1)
1. O bibliotecário pesquisa e seleciona um item.
2. O bibliotecário escolhe alterar ou excluir.
3. O sistema grava a alteração e volta ao passo 5 do fluxo principal.

**Fluxo alternativo A2 — Item duplicado** (ao passo 4)
1. O sistema informa que o item já existe.
2. Volta ao passo 3 do fluxo principal.

**Fluxo alternativo A3 — Item vinculado a livros** (em A1, ao excluir)
1. O sistema informa que não é possível excluir e encerra a operação.

---

### UC06 — Consultar acervo
- **Ator principal:** Leitor (Aluno ou Professor)
- **Histórias de origem:** US06
- **Pré-condição:** o leitor está autenticado.
- **Pós-condição:** nenhuma alteração de dados.

**Fluxo principal**
1. O leitor escolhe a opção Consultar acervo.
2. O leitor informa o critério de busca (título, autor ou categoria).
3. O sistema pesquisa no acervo.
4. O sistema exibe a lista de livros com a quantidade de exemplares disponíveis.
5. O caso de uso é encerrado.

**Fluxo alternativo A1 — Nenhum livro encontrado** (ao passo 4)
1. O sistema informa que nenhum livro foi encontrado.
2. Volta ao passo 2 do fluxo principal.

**Fluxo alternativo A2 — Livro sem exemplar disponível** (ao passo 4)
1. O sistema indica que não há exemplar disponível e oferece a opção Reservar (UC12).

---

### UC07 — Registrar empréstimo
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US07
- **Pré-condição:** o bibliotecário está autenticado e o leitor e o exemplar estão cadastrados.
- **Pós-condição:** empréstimo criado no estado Ativo, com data de devolução prevista, e exemplar marcado como Emprestado.
- **Relações:** inclui UC08.

**Fluxo principal**
1. O bibliotecário escolhe a opção Novo empréstimo.
2. O bibliotecário informa a matrícula do leitor.
3. O sistema executa UC08 (Validar elegibilidade do leitor).
4. O bibliotecário informa o código de tombo do exemplar.
5. O sistema verifica que o exemplar está Disponível.
6. O sistema calcula a data de devolução prevista conforme RN02.
7. O bibliotecário confirma o empréstimo.
8. O sistema grava o empréstimo, atualiza a situação do exemplar e exibe o comprovante.
9. O caso de uso é encerrado.

**Fluxo alternativo A1 — Leitor não elegível** (ao passo 3)
1. O sistema exibe o motivo (RN01 ou RN05) e encerra o caso de uso sem registrar o empréstimo.

**Fluxo alternativo A2 — Exemplar indisponível ou inexistente** (ao passo 5)
1. O sistema informa o problema.
2. Volta ao passo 4 do fluxo principal.

**Fluxo alternativo A3 — Exemplar reservado para outro leitor** (ao passo 5)
1. O sistema informa que o exemplar está reservado para outro leitor.
2. Volta ao passo 4 do fluxo principal.

---

### UC08 — Validar elegibilidade do leitor
- **Ator principal:** não possui (caso de uso incluído por UC07, UC11 e UC12)
- **Histórias de origem:** US08
- **Pré-condição:** o leitor foi identificado pelo caso de uso base.
- **Pós-condição:** o sistema sabe se o leitor está apto ou não.

**Fluxo principal**
1. O sistema consulta o leitor e confirma que está ativo.
2. O sistema verifica se há multa pendente (RN05).
3. O sistema verifica se há empréstimo atrasado (RN05).
4. O sistema verifica o limite de empréstimos ativos (RN01).
5. O sistema retorna ao caso de uso base indicando que o leitor está apto.

**Fluxo alternativo A1 — Leitor inativo** (ao passo 1)
1. O sistema retorna "não apto" com o motivo "leitor inativo".

**Fluxo alternativo A2 — Multa pendente ou empréstimo atrasado** (aos passos 2 e 3)
1. O sistema retorna "não apto" com o motivo correspondente.

**Fluxo alternativo A3 — Limite de empréstimos atingido** (ao passo 4)
1. O sistema retorna "não apto" com o motivo "limite de empréstimos atingido".

---

### UC09 — Registrar devolução
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US09
- **Pré-condição:** o bibliotecário está autenticado e existe empréstimo Ativo, Renovado ou Atrasado para o exemplar.
- **Pós-condição:** empréstimo no estado Devolvido e exemplar com situação Disponível (ou Reservado, se houver reserva).
- **Relações:** UC10 estende este caso de uso.

**Fluxo principal**
1. O bibliotecário escolhe a opção Registrar devolução.
2. O bibliotecário informa o código de tombo do exemplar.
3. O sistema localiza o empréstimo em aberto e exibe seus dados.
4. O sistema verifica que a devolução está dentro do prazo.
5. O bibliotecário confirma a devolução.
6. O sistema registra a data de devolução, muda o empréstimo para Devolvido e libera o exemplar.
7. O sistema exibe mensagem de sucesso e encerra o caso de uso.

**Fluxo alternativo A1 — Devolução em atraso** (ao passo 4)
1. O sistema executa UC10 (Calcular multa por atraso).
2. Volta ao passo 5 do fluxo principal.

**Fluxo alternativo A2 — Exemplar sem empréstimo em aberto** (ao passo 3)
1. O sistema informa que não há empréstimo em aberto para o exemplar e encerra o caso de uso.

**Fluxo alternativo A3 — Existe reserva para o livro** (ao passo 6)
1. O sistema marca o exemplar como Reservado para o primeiro leitor da fila.
2. Volta ao passo 7 do fluxo principal.

---

### UC10 — Calcular multa por atraso
- **Ator principal:** não possui (extensão de UC09)
- **Histórias de origem:** US10
- **Pré-condição:** a devolução está em andamento e a data atual é posterior à data de devolução prevista.
- **Pós-condição:** multa gerada com situação Pendente e vinculada ao empréstimo.

**Fluxo principal**
1. O sistema calcula os dias de atraso (data atual menos data prevista).
2. O sistema calcula o valor da multa: dias de atraso × valor diário (RN04).
3. O sistema registra a multa como Pendente, vinculada ao leitor e ao empréstimo.
4. O sistema exibe o valor da multa ao bibliotecário.
5. O sistema retorna ao UC09.

---

### UC11 — Renovar empréstimo
- **Ator principal:** Leitor (Aluno ou Professor)
- **Histórias de origem:** US11
- **Pré-condição:** o leitor está autenticado e possui empréstimo Ativo.
- **Pós-condição:** empréstimo no estado Renovado, com nova data de devolução prevista.
- **Relações:** inclui UC08.

**Fluxo principal**
1. O leitor escolhe a opção Meus empréstimos.
2. O sistema lista os empréstimos em aberto.
3. O leitor seleciona um empréstimo e escolhe Renovar.
4. O sistema executa UC08 (Validar elegibilidade do leitor).
5. O sistema verifica que o empréstimo ainda não foi renovado e que não há reserva pendente para o livro (RN03).
6. O sistema soma 7 dias à data de devolução prevista e muda o empréstimo para Renovado.
7. O sistema exibe a nova data de devolução e encerra o caso de uso.

**Fluxo alternativo A1 — Leitor não elegível** (ao passo 4)
1. O sistema exibe o motivo e encerra o caso de uso.

**Fluxo alternativo A2 — Empréstimo já renovado ou livro reservado** (ao passo 5)
1. O sistema informa que a renovação não é permitida e encerra o caso de uso.

**Fluxo alternativo A3 — Empréstimo vencido** (ao passo 5)
1. O sistema informa que empréstimos vencidos não podem ser renovados e orienta a devolução.

---

### UC12 — Reservar livro
- **Ator principal:** Leitor (Aluno ou Professor)
- **Histórias de origem:** US12
- **Pré-condição:** o leitor está autenticado.
- **Pós-condição:** reserva criada no estado Pendente, na fila do livro.
- **Relações:** inclui UC08.

**Fluxo principal**
1. O leitor pesquisa o livro (UC06) e escolhe a opção Reservar.
2. O sistema executa UC08 (Validar elegibilidade do leitor).
3. O sistema verifica que não há exemplar disponível do livro.
4. O sistema verifica que o leitor não possui reserva ativa para o mesmo livro.
5. O sistema registra a reserva na fila do livro.
6. O sistema informa a posição do leitor na fila e encerra o caso de uso.

**Fluxo alternativo A1 — Leitor não elegível** (ao passo 2)
1. O sistema exibe o motivo e encerra o caso de uso.

**Fluxo alternativo A2 — Há exemplar disponível** (ao passo 3)
1. O sistema informa que o livro está disponível para empréstimo no balcão e encerra o caso de uso.

**Fluxo alternativo A3 — Reserva já existente** (ao passo 4)
1. O sistema informa que o leitor já reservou este livro e encerra o caso de uso.

---

### UC13 — Registrar pagamento de multa
- **Ator principal:** Bibliotecário
- **Histórias de origem:** US13
- **Pré-condição:** o bibliotecário está autenticado e o leitor possui multa Pendente.
- **Pós-condição:** multa no estado Paga e leitor liberado para novos empréstimos, se não houver outras pendências.

**Fluxo principal**
1. O bibliotecário escolhe a opção Registrar pagamento de multa.
2. O bibliotecário informa a matrícula do leitor.
3. O sistema lista as multas pendentes com seus valores.
4. O bibliotecário seleciona as multas a quitar e confirma o recebimento.
5. O sistema registra o pagamento com a data e muda as multas para Paga.
6. O sistema exibe o comprovante e encerra o caso de uso.

**Fluxo alternativo A1 — Leitor sem multa pendente** (ao passo 3)
1. O sistema informa que não há multas pendentes e encerra o caso de uso.

**Fluxo alternativo A2 — Cancelar** (ao passo 4)
1. O bibliotecário escolhe Cancelar.
2. O sistema encerra o caso de uso sem registrar pagamento.

---

### UC14 — Consultar meus empréstimos e multas
- **Ator principal:** Leitor (Aluno ou Professor)
- **Histórias de origem:** US14
- **Pré-condição:** o leitor está autenticado.
- **Pós-condição:** nenhuma alteração de dados.

**Fluxo principal**
1. O leitor escolhe a opção Meus empréstimos e multas.
2. O sistema busca os empréstimos do leitor.
3. O sistema exibe cada empréstimo com livro, data de retirada, data de devolução prevista e situação.
4. O sistema exibe as multas do leitor com valor e situação.
5. O caso de uso é encerrado.

**Fluxo alternativo A1 — Nenhum empréstimo ou multa** (ao passo 3)
1. O sistema informa que não há empréstimos nem multas e encerra o caso de uso.

**Fluxo alternativo A2 — Empréstimo atrasado** (ao passo 3)
1. O sistema destaca o empréstimo atrasado e o valor da multa acumulada.
2. Volta ao passo 4 do fluxo principal.

---

### UC15 — Emitir relatórios
- **Ator principal:** Bibliotecário e Administrador
- **Histórias de origem:** US15
- **Pré-condição:** o usuário está autenticado.
- **Pós-condição:** nenhuma alteração de dados.

**Fluxo principal**
1. O ator escolhe a opção Relatórios.
2. O sistema exibe os tipos disponíveis (empréstimos por período, empréstimos em atraso, livros mais emprestados).
3. O ator escolhe o tipo e informa os filtros (por exemplo, período).
4. O sistema consulta os dados.
5. O sistema exibe o relatório em tela.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Período inválido** (ao passo 3)
1. O sistema informa que a data inicial é posterior à final.
2. Volta ao passo 3 do fluxo principal.

**Fluxo alternativo A2 — Nenhum dado no período** (ao passo 5)
1. O sistema informa que não há dados para os filtros escolhidos.
2. Volta ao passo 3 do fluxo principal.

---

### UC16 — Sugerir aquisição de livro
- **Ator principal:** Professor
- **Histórias de origem:** US16
- **Pré-condição:** o professor está autenticado.
- **Pós-condição:** sugestão registrada com situação Pendente.

**Fluxo principal**
1. O professor escolhe a opção Sugerir aquisição.
2. O sistema exibe o formulário (título, autor, editora, justificativa).
3. O professor preenche os dados e confirma.
4. O sistema valida os dados.
5. O sistema grava a sugestão e exibe mensagem de sucesso.
6. O caso de uso é encerrado.

**Fluxo alternativo A1 — Campos obrigatórios em branco** (ao passo 4)
1. O sistema informa os campos a preencher.
2. Volta ao passo 3 do fluxo principal.

**Fluxo alternativo A2 — Livro já existe no acervo** (ao passo 4)
1. O sistema informa que o livro já faz parte do acervo e encerra o caso de uso.

- UC07, UC11 e UC12 **incluem** UC08 (a validação de elegibilidade é sempre executada).
- UC10 **estende** UC09 (a multa só é calculada quando há atraso na devolução).

## 6. Diagrama

Ver `docs/diagramas/casos-de-uso.png` (fonte: `docs/diagramas/casos-de-uso.puml`, PlantUML).
