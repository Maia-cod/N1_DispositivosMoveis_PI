# Backlog Priorizado — BarberAgenda (N1 / N2)

## Visão geral
Projeto integrador da disciplina Projeto Integrador ADS 2026/2. Objetivo: aplicação móvel Android para agendamento de serviços em barbearia.
- **Entrega N1** (prazo original: 28/09–02/10): navegação, autenticação, cadastro/consulta de agendamentos, persistência local, regras de negócio (conflito, antecedência 1h, cancelamento) e recursos nativos de data/hora.
- **Entrega N2**: ampliar para ≥6 telas, cadastro de usuários, persistência remota, API externa, recurso nativo do dispositivo, visão consolidada, APK assinado/testado em dispositivo físico.

---

## Histórias de usuário (RF) — Priorização

| ID | História | Perfil | Prioridade | Status N1 | Comentário |
|----|----------|--------|------------|-----------|------------|
| **RF01** | Autenticar usuário com login e senha | Ambos | Essencial | ✅ Concluída | Dois perfis: dono e cliente |
| **RF02** | Manter a sessão do usuário (permanecer autenticado) | Ambos | Importante | ✅ Concluída | SharedPreferences |
| **RF03** | Encerrar sessão (“Sair”) | Ambos | Essencial | ✅ Concluída | |
| **RF04** | Listar agendamentos ordenados por data e hora | Ambos | Essencial | ✅ Concluída | RecyclerView |
| **RF05** | Buscar agendamentos por nome do cliente | Ambos | Importante | ✅ Concluída | Filter no adapter |
| **RF06** | Cadastrar agendamento (cliente, telefone, serviço, data/hora, observação) | Ambos | Essencial | ✅ Concluída | Tela de cadastro |
| **RF07** | Selecionar data por calendário nativo | Ambos | Essencial | ✅ Concluída | MaterialDatePicker |
| **RF08** | Selecionar hora por relógio nativo | Ambos | Essencial | ✅ Concluída | MaterialTimePicker |
| **RF09** | Validar campos obrigatórios antes de salvar | Ambos | Essencial | ✅ Concluída | Nome, telefone, data/hora |
| **RF10** | Impedir agendamento em horário já ocupado (conflito) | Ambos | Essencial | ✅ Concluída | RN01 |
| **RF11** | Exigir antecedência mínima de 1 hora | Ambos | Importante | ✅ Concluída | RN02 |
| **RF12** | Exibir o detalhe completo de um agendamento | Ambos | Essencial | ✅ Concluída | DetalheActivity |
| **RF13** | Marcar agendamento como concluído | Dono | Importante | ✅ Concluída | Botão “Concluir” |
| **RF14** | Cancelar agendamento respeitando a regra por perfil | Ambos | Importante | ✅ Concluída | Cliente: até 2h antes; Dono: a qualquer momento |
| **RF15** | Indicar status (Confirmado, Atrasado, Cancelado, Concluído) | Ambos | Importante | ✅ Concluída | Coluna/status no card |
| **RF16** | Exibir estado de lista vazia / busca sem resultado | Ambos | Desejável | ✅ Concluída (mensagem “Nenhum agendamento”) | |
| **RF17** | Cadastrar e gerenciar usuários pela interface | Dono | Importante | ⏳ Pendente N2 | Nova tela + CRUD de usuários |
| **RF18** | Sincronizar agendamentos com serviço de retaguarda | Ambos | Essencial N2 | ⏳ Pendente N2 | Endpoint REST (futuro) |
| **RF19** | Consultar serviço/interface externa pertinente ao domínio | Ambos | Desejável N2 | ⏳ Pendente N2 | Ex.: API de CEP/horário |
| **RF20** | Notificar/lembrar o cliente do horário marcado | Cliente | Desejável N2 | ⏳ Pendente N2 | Pending notification channel |
| **RF21** | Exibir visão consolidada (resumo/indicador de atendimentos) | Dono | Importante N2 | ⏳ Pendente N2 | Gráfico simples (MPAndroidChart ou similar) |

---

## Regras de negócio (RN) — validação

| ID | Regra | Comportamento | Onde verifica |
|----|-------|---------------|----------------|
| RN01 | Não permitir conflito de horário | Bloqueia salvamento se já houver agendamento Confirmado no mesmo dia/hora; exibe mensagem de erro. | Tela de novo agendamento / AgendamentoRepository |
| RN02 | Antecedência mínima de 1 hora | Permite agendamento apenas para horário ≥ 1h a partir de agora. | Tela de novo agendamento |
| RN03 | Prazo de cancelamento por perfil | Cliente cancela até 2h antes; dono cancela a qualquer momento. Agendamentos concluídos/cancelados não podem ser alterados. | DetalheActivity / AgendamentoRepository |
| RN04 | Validação de dados de entrada | Nome do cliente e serviço obrigatórios; telefone com ≥8 dígitos; data e hora obrigatórias. | Telas de login e cadastro |
| RN05 | Exclusão lógica | Cancelar ou conclui altera o *status*; o registro é preservado para histórico. | AgendamentoRepository + DAO |

---

## Sprint / Ciclo de desenvolvimento (plano sugerido)

| Semana | Atividade | Responsável (placeholder) | Entregável |
|--------|-----------|---------------------------|------------|
<<<<<<< HEAD
| 5 (31/08–04/09) | Modelagem de dados, DER e repositório | Isabella Batista | DER + memorial arquitetural |
| 6 (08–11/09) | Protótipo navegável; backlog prioritizado | Kevin Lucas | Protótipo HTML + BACKLOG.md |
| 7 (14–18/09) | Estrutura em camadas, navegação e autenticação | Felipe Moura | Login + navegação entre 4 telas |
| 8 (21–25/09) | Módulo de agendamento e persistência local | Integrante 4 | CRUD de agendamento + Room |
=======
| 5 (31/08–04/09) | Modelagem de dados, DER e repositório | Ana | DER + memorial arquitetural |
| 6 (08–11/09) | Protótipo navegável; backlog prioritizado | Bruno | Protótipo HTML + BACKLOG.md |
| 7 (14–18/09) | Estrutura em camadas, navegação e autenticação | Carlos | Login + navegação entre 4 telas |
| 8 (21–25/09) | Módulo de agendamento e persistência local | Diego | CRUD de agendamento + Room |
>>>>>>> 2dfd45fc015ddfc7418bd81dcbf71d9c5b7c9a5e
| 9 (28/09–02/10) | Revisão, ajuste de regras e entrega da N1 | Equipe | Artefatos da N1 (docx + código) |
| 10–12 | Evolução para N2: 6+ telas, cadastro de usuários, remoto | — | — |

---

## Distribuição de responsabilidades (equipe)

| Integrante | Atribuição técnica | Áreas de foco (N1 → N2) |
|------------|-------------------|-------------------------|
<<<<<<< HEAD
| **Kevin Lucas** (coordenador) | Gestão do projeto, interlocução com o docente, documentação | Acompanhamento de prazos, revisão de documentos |
| **Isabella Batista** (responsável técnico) | Persistência (Room) e camada de dados | RF01–RF11, modelo de banco, sincronização N2 |
| **Felipe Moura** | Interface (layouts, View Binding) e navegação | RF03–RF16, protótipo, telas N2 |
| **Integrante 4** | Regras de negócio, listas (RecyclerView) e protótipo | RF10–RF15, regras RN01–RN05, visão consolidada N2 |

> **Observação:** o Documento Norteador exige equipes de 3 a 4 integrantes. Kevin Lucas (coordenador), Isabella Batista (responsável técnico) e Felipe Moura (interface e navegação) estão definidos; falta apenas o Integrante 4 (regras de negócio, listas e protótipo) a ser indicado oficialmente.
=======
| **Integrante 1** (coordenador) | Gestão do projeto, interlocução com o docente, documentação | Acompanhamento de prazos, revisão de documentos |
| **Integrante 2** | Persistência (Room) e camada de dados | RF01–RF11, modelo de banco, sincronização N2 |
| **Integrante 3** | Interface (layouts, View Binding) e navegação | RF03–RF16, protótipo, telas N2 |
| **Integrante 4** | Regras de negócio, listas (RecyclerView) e protótipo | RF10–RF15, regras RN01–RN05, visão consolidada N2 |

> **Observação:** os nomes dos integrantes foram substituídos por “placeholder” pois ainda não foram definidos oficialmente. A distribuição será confirmada no início do semestre.
>>>>>>> 2dfd45fc015ddfc7418bd81dcbf71d9c5b7c9a5e

---

## Fluxo Git

1. **Branch `main`** — sempre versão estável (build `assembleDebug` bem‑sucedido).
2. **Branches de funcionalidade**: `feature/<short‑desc>` (ex.: `feature/login`, `feature/agendamento`).
3. **Pull Request** — todo código deve passar por revisão antes de ser mergeado em `main`.
4. **Commit message** — convenção curta: `feat: <descrição>`, `fix: <descrição>`, `docs: <descrição>`.
5. **Tags de release**: `v1.0-N1`, `v2.0-N2` após cada entrega.

---

## Próximos passos imediatos

- [ ] **Criar `README.md`** (instalação, execução, credenciais `DEMO_*`, comandos Gradle).  
- [ ] **Preencher os placeholders** nos artefatos da N1 (nome da equipe, data, repositório) e exportar os `.docx` em PDF (`PI2026-2_<Equipe>_N1.pdf`).  
- [ ] **Definir nomes dos integrantes** na tabela de responsabilidades.  
- [ ] **Planejar as 6+ telas** para N2 (R1) e iniciar protótipo de novas telas.

---
*Última atualização: 08/10/2026*