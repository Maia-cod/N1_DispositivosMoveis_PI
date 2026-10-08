# Guia de apresentação — N1 (BarberAgenda)

**Tema em 1 frase:** "App para o cliente marcar horário na barbearia, com agenda do dia e bloqueio de horários já ocupados."

**Credenciais de teste (já cadastradas no app):**
- Dono (barbeiro): `carlos / 123` → pode cancelar qualquer agendamento, mesmo em cima da hora.
- Cliente: `ana / 123` → cancelamento permitido só até 2h antes.

---

## 1. Interface e Layouts Modernos

| O que mostrar | Onde está |
|---|---|
| ConstraintLayout em todas as telas | `res/layout/activity_login.xml:2`, `activity_main.xml:2`, `activity_cadastro.xml:10`, `activity_detalhe.xml:10`, `item_agendamento.xml:8` |
| Tema Material Design 3 | `res/values/themes.xml:3` (parent `Theme.Material3.DayNight`) |
| MaterialButton | `activity_login.xml` (botaoEntrar:143), `activity_cadastro.xml` (botaoSalvar, botaoData, botaoHora:194) |
| MaterialCardView (itens da lista) | `res/layout/item_agendamento.xml:2` |
| TextInputLayout OutlinedBox (campos com erro) | `activity_cadastro.xml` (layoutCliente:12, layoutTelefone:31...) |

**Fale:** "Não usamos RelativeLayout nem LinearLayout como raiz; tudo é ConstraintLayout ancorado com constraints, então a tela se adapta a qualquer tamanho."

## 2. Boas Práticas em Java (View Binding + Lambda)

| O que mostrar | Onde está |
|---|---|
| View Binding habilitado | `app/build.gradle:22` (`buildFeatures { viewBinding true }`) |
| Nenhum `findViewById` no projeto | rodar: `rg findViewById app/src` → **0 ocorrências** |
| ViewBinding em cada Activity | `LoginActivity.java:21`, `MainActivity.java:33`, `CadastroActivity.java:46`, `DetalheActivity.java:36` |
| Listeners com Lambda | `LoginActivity.java:31`, `MainActivity.java:48`, `CadastroActivity.java:65-68`, `DetalheActivity.java:56-57`, `AgendamentoAdapter.java:74` |
| Callback assíncrono também com lambda | `AgendamentoRepository.java:66-79` (autenticar/contar/inserir/atualizar) |

**Fale:** "Binding gera a classe automaticamente; se o ID mudar, o build quebra no lugar errado — previne crash de `findViewById` nulo."

## 3. Navegação e Estabilidade (Intents + ciclo de vida)

| O que mostrar | Onde está |
|---|---|
| Intent explícita (Main → Detalhe) | `MainActivity.java:84-88` |
| Tráfego seguro de dados via `Serializable` + null-check | `DetalheActivity.java:40-47` (se faltar extra → fecha com aviso, não crasha) |
| Rotação preserva o estado | `onSaveInstanceState` em `DetalheActivity.java:61-64` e `CadastroActivity.java:72-76` |
| Datas/horas escolhidas sobrevivem à rotação | `CadastroActivity.java` (arestas/CHAVE_*:13-17) |
| LiveData (lifecycle) com Room | `AgendamentoDao.java:25` (`LiveData<List<Agendamento>>`), observe em `MainActivity.java:59` |

**Fale:** "Usamos Intent explícita com putExtra/getSerializableExtra com checagem de null; e onSaveInstanceState pra não perder os dados na rotação."

## 4. Listagens e Recursos Nativos

| O que mostrar | Onde está |
|---|---|
| RecyclerView + ViewHolder | `MainActivity.java:52-53` (adapter+layout), `AgendamentoAdapter.java:22` (ListAdapter), ViewHolder:103 |
| DiffUtil (lista performática) | `AgendamentoAdapter.java:24-39` |
| MaterialDatePicker (data) | `CadastroActivity.java:82` |
| MaterialTimePicker (hora) | `CadastroActivity.java:100` |

**Fale:** "RecyclerView reaproveita as views (ViewHolder); a busca filtra a lista em `MainActivity.aplicarFiltro()` e atualiza o estado vazio."

---

## Regras de negócio (3) pra demonstrar
1. **Conflito:** agendar 2 clientes no mesmo dia/hora → `erro_conflito` (`RegrasNegocio.java:48`).
2. **Antecedência mínima de 1h:** `RegrasNegocio.java:41` (`ANTECEDENCIA_MINIMA_MS`).
3. **Cancelamento ≤2h antes (cliente)** / dono cancela qualquer hora: `RegrasNegocio.java:54` — demo: logar como `ana`, tentar cancelar um agendamento para daqui a 30 min → bloqueado; logar como `carlos` → cancela.

## Estados de interface (R10)
- Lista vazia / busca sem resultado → TextView `textListaVazia` (`activity_main.xml:33`).
- Agendamento vencido fica vermelho ("ATRASADO") → `AgendamentoAdapter.pintarStatus()`:78.

---

## Roteiro de apresentação (sugestão, 20 min)
1. **3 min — Contexto:** problema (marcar horário sem conflito), público (cliente + barbeiro).
2. **3 min — Tela de login** (2 perfis), entrando como `ana`.
3. **6 min — Demonstração:** criar agendamento (DatePicker + TimePicker) → ver na lista → abrir detalhe → tentar conflito (falha) → mostrar regra de cancelamento.
4. **4 min — Código vivo:** mostre os 4 pontos acima no Android Studio (busque `findViewById` = 0).
5. **4 min — Encerramento:** arquitetura em camadas (data/negocio/ui), Room + LiveData, próximos passos (backend, notificação).

## Lembrete importante
O documento exige declaração de uso de IA no relatório final e domínio do código na arguição ("a impossibilidade de explicar decisões caracteriza ausência de autoria"). Leia bem este guia antes da apresentação.