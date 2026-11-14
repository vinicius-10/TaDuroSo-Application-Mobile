# 💸 Tá Duro Sô

> Tá Duro Sô é um aplicativo Android de controle de despesas pessoais, desenvolvido durante a disciplina de Programação para Dispositivos Móveis da UTFPR.

<div align="left">
  <img src="https://img.shields.io/badge/Status-Em_Desenvolvimento-yellow" alt="Status">
  <img src="https://img.shields.io/badge/Plataforma-Android-3DDC84?logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Linguagem-Java_11-ED8B00?logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Banco_de_Dados-Room_(SQLite)-003B57?logo=sqlite&logoColor=white" alt="Room">
  <img src="https://img.shields.io/badge/Min_SDK-24_(Android_7.0)-blue" alt="Min SDK">
</div>


## 📑 Sumário
- [Sobre o Projeto](#-sobre-o-projeto)
- [Objetivo](#-objetivo)
- [Funcionalidades](#-funcionalidades)
- [Conceitos Aplicados](#-conceitos-aplicados)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar](#-como-executar)
- [Autor](#-autor)

---

## 💜 Sobre o Projeto
O **Tá Duro Sô** é uma ferramenta simples e centralizada para registrar e acompanhar gastos do dia a dia. O nome brinca com a expressão mineira "tá duro, sô?", aquela pergunta que todo mundo faz no fim do mês.

O usuário cadastra cada despesa informando nome, valor, data, se é essencial ou não, a forma de pagamento e se é uma despesa fixa. Os dados ficam salvos no próprio aparelho e podem ser consultados, editados, ordenados e excluídos a qualquer momento.

## 🎯 Objetivo
- Registrar despesas pessoais de forma rápida
- Classificar gastos entre essenciais e não essenciais
- Identificar despesas fixas (recorrentes)
- Acompanhar os gastos em ordem cronológica
- Aplicar na prática os conceitos de desenvolvimento Android vistos na disciplina

## ✨ Funcionalidades
- **Cadastro de despesas** com validação de todos os campos (nome, valor, data e tipo)
- **Listagem** personalizada com nome, valor, data e categoria
- **Edição e exclusão** pelo clique longo no item, com barra de ações contextual
- **Confirmação antes de excluir**, evitando remoções acidentais
- **Ordenação por data** (crescente ou decrescente), com a preferência salva entre usos
- **Sugestão de forma de pagamento**: o app pode lembrar a última forma usada
- **Limpar formulário com "Desfazer"**, por meio de uma Snackbar
- **Formas de pagamento**: crédito, débito, PIX e dinheiro
- **Tema claro e escuro**, seguindo a configuração do sistema
- **Idiomas**: português (pt-BR) e inglês, com datas no formato da região do aparelho
- **Tela "Sobre"** com informações do autor e link externo

## 📚 Conceitos Aplicados
| Conceito | Onde aparece |
|---|---|
| Activities e ciclo de vida | `ListExpense`, `MerActivity`, `About` |
| Navegação com Intents e retorno de resultado | `registerForActivityResult` entre a lista e o formulário |
| Intent implícita | Abertura do link externo na tela "Sobre" |
| ListView com Adapter customizado | `ExpenseAdapter` com padrão *ViewHolder* |
| Menus de opções e *Contextual Action Mode* | Menus da lista e do formulário |
| Persistência com Room | `ExpenseDatabase`, `ExpenseDao`, `Converters` |
| SharedPreferences | Ordenação da lista e sugestão de pagamento |
| Diálogos e Snackbar | `UtilsAlert` e ação de desfazer |
| Internacionalização (i18n) | `values/` e `values-pt-rBR/` |
| Temas e modo noturno | `values-night/` e `drawable-night/` |

## 🛠 Tecnologias Utilizadas
- Linguagem: Java 11
- IDE: Android Studio
- Build: Gradle (Kotlin DSL) + Android Gradle Plugin 9.3
- Banco de Dados: Room 3.0 (SQLite)
- Interface: AppCompat, Material Components, ConstraintLayout
- SDK: mínimo 24 (Android 7.0) · alvo 37
- Versionamento: Git + GitHub

## 📂 Estrutura do Projeto
```
app/src/main/
├── java/com/example/taduros/
│   ├── ListExpense.java        # Tela principal: lista de despesas
│   ├── MerActivity.java        # Cadastro e edição de despesas
│   ├── About.java              # Tela "Sobre"
│   ├── ExpenseAdapter.java     # Adapter da ListView
│   ├── model/
│   │   ├── Expense.java        # Entidade da despesa
│   │   └── Category_expense.java
│   ├── persistence/
│   │   ├── ExpenseDatabase.java
│   │   ├── ExpenseDao.java
│   │   └── Converters.java     # Conversão Date <-> timestamp
│   └── utils/
│       ├── UtilsAlert.java     # Diálogos de aviso e confirmação
│       └── UtilsDate.java      # Formatação de datas por região
└── res/
    ├── layout/                 # Telas e linha da lista
    ├── menu/                   # Menus de opções e de ação
    ├── values/                 # Textos em inglês, cores e temas
    ├── values-pt-rBR/          # Textos em português
    └── values-night/           # Tema escuro
```

## ▶️ Como Executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/vinicius-10/TaDuroSo-Android.git
   ```
2. Abra a pasta no **Android Studio** e aguarde a sincronização do Gradle.
3. Inicie um emulador ou conecte um aparelho com Android 7.0 ou superior.
4. Clique em **Run ▶**.

## 👤 Autor
**Vinicius Souza Dias**
Engenharia de Computação, UTFPR

[![LinkedIn](https://img.shields.io/badge/LinkedIn-vinicius--dias--dev-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/vinicius-dias-dev/)
[![GitHub](https://img.shields.io/badge/GitHub-vinicius--10-181717?logo=github&logoColor=white)](https://github.com/vinicius-10)
