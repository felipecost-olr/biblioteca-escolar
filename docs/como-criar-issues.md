# Como cadastrar as histórias como issues

O script `scripts/criar-issues.sh` cria os 20 issues na ordem de prioridade usando o GitHub CLI.

```bash
gh auth login                       # uma vez
cd biblioteca-escolar
bash scripts/criar-issues.sh        # usa o repositório do diretório atual
```

Ele cria os rótulos (`prioridade:alta`, `prioridade:media`, `prioridade:baixa`, e um por papel) e os issues US01 a US20, já na ordem do backlog.

Alternativa manual: crie um GitHub Project (Board), adicione cada linha de `docs/historias-de-usuario.md` como card e ordene de cima para baixo.
