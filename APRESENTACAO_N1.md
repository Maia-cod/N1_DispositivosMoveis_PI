# Guia de apresentação — N1 (10 minutos)

**Formato pedido pelo professor (MS Teams):** Objetivos → Justificativa → Funcionalidades (protótipos de telas), mostrando o **progresso do app**. Todos os integrantes devem falar.

**Credenciais de teste (já cadastradas no app):**
- Dono (barbeiro): `carlos / 123` → pode cancelar qualquer agendamento, mesmo em cima da hora.
- Cliente: `ana / 123` → cancelamento permitido só até 2h antes.

---

## Roteiro de 10 minutos

### 1. Objetivos — o que o app faz (2 min) — Kevin Lucas (coordenador)

**Mostrar:** tela de login (`docs/img/proto_login.png` ou o app aberto).

**Fale:** "O BarberAgenda é um app Android para agendamento de serviços em barbearia. O **cliente** marca horário, consulta a agenda e cancela com prazo; o **dono/barbeiro** gerencia a agenda, conclui atendimentos e cancela quando precisar. A persistência é local, com Room, e a navegação usa Intents entre 4 telas integradas."

Pontos a cobrir em 30 segundos cada:
- Dois perfis com permissões distintas (dono × cliente).
- Regras de negócio: sem conflito de horário, antecedência mínima de 1h, cancelamento do cliente só até 2h antes.
- Stack: Android nativo (Java) + Room + Material Design 3.

### 2. Justificativa — por que é relevante (2 min) — Integrante 4

**Fale:** "Barbearias pequenas ainda marcam horário por telefone ou WhatsApp: o cliente não sabe se o horário está livre, o barbeiro anota em papel e vive tendo conflito de agenda. O app resolve isso com controle centralizado: o cliente escolhe data e hora em seletores nativos, o sistema **bloqueia horário já ocupado** e o dono acompanha tudo em uma lista organizada com status (confirmado, atrasado, cancelado, concluído)."

**Mostrar:** `docs/img/proto_agenda.png` — "esta é a agenda que o dono vê ao abrir o app".

### 3. Funcionalidades — protótipos e progresso do app (5 min) — Felipe Moura, Isabella Batista e Integrante 4

**3a. Protótipos das telas (1,5 min)** — Felipe Moura abre o protótipo navegável (`docs/prototipo/barberagenda-prototipo.html`):
- Login → Agenda → Novo agendamento → Detalhe.
- Mostrar `docs/img/telas.png` (visão geral das telas) e destacar: "usamos MaterialCardView nos itens da lista e ConstraintLayout em tudo, então as telas se adaptam a qualquer tamanho de tela".

**3b. Demonstração ao vivo no app (3,5 min)** — seguir o roteiro:
1. Login como `ana` (cliente) → tela da agenda com lista em RecyclerView.
2. Botão **+** → **Novo agendamento**: escolher data no **MaterialDatePicker** e hora no **MaterialTimePicker** → salvar.
3. Testar **conflito**: tentar agendar outro cliente no mesmo dia/hora → mensagem de bloqueio (RN01).
4. Testar **antecedência**: tentar horário com menos de 1h → bloqueado (RN02).
5. Abrir o **detalhe** do agendamento → como cliente, cancelamento é permitido só até 2h antes (RN03).
6. Sair, entrar como `carlos` (dono) → cancelamento liberado a qualquer hora + botão "Concluir".
7. Usar a **busca** por nome do cliente para filtrar a lista.

**3c. Código e critérios técnicos (1 min)** — Isabella Batista (responsável técnico) mostra rápido no Android Studio:

| Critério avaliado | O que mostrar |
|---|---|
| ConstraintLayout + Material 3 | `res/layout/` — nenhum LinearLayout/RelativeLayout; MaterialCardView em `item_agendamento.xml:2` |
| View Binding + Lambda, sem findViewById | `MainActivity.java:33` (binding); rodar busca por `findViewById` = **0 ocorrências**; listener em lambda em `MainActivity.java:48` |
| Intents explícitas + ciclo de vida | `MainActivity.java:84-88` (putExtra serializável); `DetalheActivity.java:40-47` (null-check, não crasha); rotação preserva dados (`onSaveInstanceState`) |
| RecyclerView + recursos nativos | `AgendamentoAdapter.java:22` (ListAdapter + ViewHolder); `CadastroActivity.java:82` (MaterialDatePicker) |

**Fale:** "Nenhum findViewById — o View Binding gera as classes e quebra o build se um ID mudar, o que evita crash."

### 4. Fechamento — estado atual e próximos passos (1 min) — Integrante 1

**Fale:** "Entregamos a N1 com navegação completa, autenticação de dois perfis, cadastro e gestão de agendamentos com persistência local e as três regras de negócio verificáveis. Para a N2 planejamos: novas telas (perfil, histórico), cadastro de usuários, visão consolidada e integração com serviço remoto."

---

## Checklist rápido antes de gravar/apresentar

- [ ] Emulador aberto com o app instalado (`gradlew installDebug`) e logado como `ana`.
- [ ] Aba do navegador com o protótipo: `docs/prototipo/barberagenda-prototipo.html`.
- [ ] Ter pelo menos 1 agendamento futuro (≥3h) para demonstrar cancelamento da cliente.
- [ ] Ter 1 agendamento criado para demonstrar o conflito de horário.
- [ ] Android Studio aberto no projeto para o momento do código.
- [ ] Ensaiar: cada integrante fala seu bloco — a arguição é individual.

## Regras de negócio para demonstrar (se perguntarem)

1. **Conflito** — mesmo dia/hora já ocupado → `erro_conflito` (`RegrasNegocio.java:48`).
2. **Antecedência mínima 1h** — `RegrasNegocio.java:41`.
3. **Cancelamento** — cliente até 2h antes; dono a qualquer hora → `RegrasNegocio.java:54`.

> **Nota:** o professor avaliará domínio do código na arguição — cada integrante deve saber explicar o trecho que implementou.
