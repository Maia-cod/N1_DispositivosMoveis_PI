# BarberAgenda

Aplicação móvel Android para agendamento de serviços em barbearia.
Projeto Integrador da disciplina **Projeto Integrador ADS 2026/2** — PUC Goiás.

---

## 📋 Descrição

Permite ao **dono/barbeiro** gerenciar a agenda (ver, cadastrar, concluir, cancelar) e ao **cliente** marcar horários, visualizar a agenda e cancelar com regra de prazo (até 2h antes). Persistência local via **Room / SQLite**, autenticação com dois perfis (dono/cliente), navegação com **Intents explícitas** e listas com **RecyclerView** + **Material Design 3**.

---

## 🛠️ Pré-requisitos

- **Java**: JDK 21 (variável de ambiente `JAVA_HOME` apontando para `C:\Program Files\Java\jdk-26`).
- **Android Studio**: versão 2024.2+ (versão mais recente instalada).
- **Android SDK**: `E:/Android/sdk` (configured na máquina).
- **Git**: para clonagem e versionamento.
- **Gradle Wrapper**: incluso (`gradlew.bat`).

---

## ▶️ Como construir e rodar

### 1. Clonar o repositório (ou abrir a pasta existente)

```powershell
git clone <endereço-do-repositorio>
cd BarberAgenda
```

### 2. Construir (versão debug)

Abrir o terminal na raiz do projeto e executar:

```powershell
.\gradlew.bat assembleDebug
```

**Resultado esperado:** *BUILD SUCCESSFUL em ~1 minuto* (36 tarefas, último sucesso em 08/10/2026). O APK gerado fica em:

```
app/build/outputs/apk/debug/app-debug.apk
```

### 3. Rodar no emulador ou dispositivo físico

- No **Android Studio**: clicar em *Run 'app'* (ícone ▶️) após o build bem-sucedido.
- Via linha de comando:

```powershell
.\gradlew.bat installDebug
```

### 4. Primeiro acesso (login)

A aplicação vem com dois perfis de acesso **pré‑carregados** no banco local (seed via `BuildConfig.DEMO_*`). Use as credenciais abaixo:

| Perfil | Login (exemplo) | Senha (exemplo) |
|--------|-----------------|-----------------|
| **Dono** | `carlos` | `123` |
| **Cliente** | `ana` | `123` |

> **Nota:** As credenciais exatas são lidas de `BuildConfig.DEMO_DONO_LOGIN/SENHA` e `BuildConfig.DEMO_CLIENTE_LOGIN/SENHA`. Se necessário, consulte `app/data/AppDatabase.java` para ajustar os dados de teste.

---

## 📱 Telas (N1)

1. **Login** — escolha perfil (dono/cliente).
2. **Agenda** — lista de agendamentos ordenados por data/hora; busca por nome do cliente.
3. **Novo agendamento** — seleção de data (MaterialDatePicker) e hora (MaterialTimePicker), validação de campos, regra de antecedência mínima 1h e bloqueio de conflito.
4. **Detalhe do agendamento** — visualização completa, botões “Concluir” (dono) e “Cancelar” (com regra de prazo).

---

## 🗂️ Estrutura relevante do código

| Arquivo / Pasta | Responsabilidade |
|-----------------|------------------|
| `app/src/main/java/com/exemplo/barberagenda/ui/` | Activities (LoginActivity, MainActivity, CadastroActivity, DetalheActivity) e Adapters |
| `app/src/main/java/com/exemplo/barberagenda/data/` | `AgendamentoRepository`, `AppDatabase`, DAOs, `Sessao`, `RegrasNegocio` |
| `app/src/main/java/com/exemplo/barberagenda/negocio/` | Lógica central: conflitos, antecedência, cancelamento por perfil |
| `app/src/main/res/layout/` | Layouts XML (principal: `activity_cadastro.xml` agora em `ConstraintLayout`) |
| `app/build.gradle` | Configurações: `buildConfig true`, `buildConfigField DEMO_*`, `ksp`, `viewBinding` |
| `docs/` | Artefatos da N1: diagramas (DER, arquitetura), protótipo navegável e memorial. |

---

## 🚧 Próximos passos (Roadmap N2)

- **R1**: ≥6 telas (atualmente 4) — novas telas de perfil, histórico, configuração.
- **R3**: CRUD de 2 entidades (incluir `Usuario` com exclusão e cadastro pela UI).
- **R6/R7**: Persistência remota + integração com API externa.
- **R8**: Recurso nativo (câmera para foto de perfil, localização, notificações).
- **R9**: Visão consolidada (gráfico de atendimentos).
- **R14**: APK assinado (release) e testado em dispositivo físico.

---

## 📦 Artefatos da N1 (already delivered)

| Artefato | Caminho |
|----------|---------|
| Documento de Projeto | `docs\PI2026-2_BarberAgenda_N1_DocumentoProjeto.docx` |
| Modelagem + Arquitetura | `docs\PI2026-2_BarberAgenda_N1_ModelagemArquitetura.docx` |
| Protótipo + Memorial | `docs\PI2026-2_BarberAgenda_N1_Prototipo.docx` |
| Backlog priorizado | `BACKLOG.md` |
| Código corrigido | `LinearLayout → ConstraintLayout`, `try/catch`, nomenclatura `vinculo`, `buildConfigField`, seed `BuildConfig` |

---

*Última atualização: 08/10/2026 — Build `assembleDebug` validado com sucesso.*