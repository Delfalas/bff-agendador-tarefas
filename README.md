# 🧠 BFF - Agendador de Tarefas

Este projeto representa o **BFF (Backend For Frontend)** da arquitetura de microsserviços, responsável por **orquestrar e integrar os serviços de Usuário, Tarefas e Notificação**.

Além disso, o projeto está **totalmente containerizado com Docker**, permitindo execução completa e simplificada de toda a arquitetura.

---

## 🧩 Papel na Arquitetura

[ Frontend / Cliente ]
          ↓
        BFF
          ↓
 ┌────────┼────────┐
 ↓        ↓        ↓
Usuário  Tarefas  Notificação
(SQL)   (MongoDB)   (Email)

O BFF atua como uma **camada intermediária**, responsável por:

* 🔗 Orquestrar múltiplos microsserviços
* 🔐 Centralizar autenticação e repasse de token
* 📦 Agregar dados de diferentes serviços
* ⚠️ Padronizar tratamento de erros
* ⏰ Executar rotinas automáticas (cron)

---

## 🚀 Funcionalidades

### 👤 Usuário

* Cadastro, login e autenticação
* Atualização de dados
* Gerenciamento de endereços e telefones
* Integração com API de CEP

---

### 📅 Tarefas

* Criação de tarefas (unitário e lote)
* Consulta por usuário
* Consulta por período
* Atualização e exclusão
* Controle de status de notificação

---

### 📧 Notificação

* Envio de emails
* Integração com serviço de notificação
* Disparo automático via cron

---

### ⏰ Agendamento (Cron)

* Busca tarefas futuras automaticamente
* Envia notificações por email
* Atualiza status da tarefa

---

## 🔄 Fluxo Geral

1. Usuário realiza login via BFF
2. BFF autentica no microserviço de usuário
3. Token JWT é utilizado nas demais requisições
4. Usuário cria tarefas
5. Cron busca tarefas futuras
6. BFF aciona serviço de notificação
7. Status da tarefa é atualizado

---

## 🔌 Comunicação entre Microsserviços

O BFF utiliza **OpenFeign** para comunicação entre serviços:

* 👤 `UsuarioClient`
* 📅 `TarefaClient`
* 📧 `EmailClient`

### ✔️ Benefícios:

* Código mais limpo e declarativo
* Baixo acoplamento
* Facilidade de manutenção

---

## ⚠️ Tratamento de Erros (Feign)

Classe: `FeignError`

Mapeamento de erros HTTP:

* `400` → `IllegalArgumentException`
* `401` → `UnauthorizedException`
* `403 / 404` → `ResourceNotFoundException`
* `409` → `ConflictException`
* Default → `BusinessException`

---

## ⏰ Cron Service

### 📌 Funcionamento

* Executado com base em expressão CRON configurável
* Busca tarefas da próxima 1h até +5 minutos
* Envia email automaticamente
* Atualiza status para `NOTIFICADO`

### 🔄 Fluxo interno:

1. Realiza login automático
2. Gera token JWT
3. Busca tarefas por período
4. Envia email
5. Atualiza status da tarefa

---

## 🔐 Segurança

* Autenticação via JWT
* Token propagado entre serviços
* Endpoints protegidos

---

🗄️ Bancos de Dados

O sistema utiliza dois tipos de banco, cada um com um propósito específico:

🐘 PostgreSQL (SQL)

Utilizado pelo microsserviço de Usuário

Armazena: Usuários, Endereços, Telefones

🍃 MongoDB (NoSQL)

Utilizado pelo microsserviço de Tarefas

Armazena: Tarefas agendadas, Status de notificação, Datas e eventos

---

## 📂 Estrutura do Projeto

Organização em camadas:

* `business` → regras de negócio
* `controller` → endpoints REST
* `infrastructure.client` → Feign Clients
* `infrastructure.exceptions` → exceções customizadas
* `infrastructure.security` → configuração de segurança

---

## 📦 DTOs

Separação clara:

* `dto.in` → entrada (request)
* `dto.out` → saída (response)

---

## ⚠️ Tratamento Global de Exceções

* `GlobalExceptionHandler`
* Exceções customizadas:

  * BusinessException
  * ConflictException
  * ResourceNotFoundException
  * UnauthorizedException
  * IllegalArgumentException

---

## 🔗 Principais Endpoints

### 👤 Usuário

```http
POST /usuario
POST /usuario/login
GET /usuario/email
PUT /usuario
DELETE /usuario/{email}
```

---

### 📅 Tarefas

```http
POST /tarefas
POST /tarefas/lote
GET /tarefas
GET /tarefas/eventos
PUT /tarefas
PATCH /tarefas
DELETE /tarefas
```

---

### 📧 Email

```http
POST /email
```

---

## 🐳 Docker (Projeto Completo)

O projeto está **100% containerizado**, permitindo subir toda a arquitetura com um único comando.

### 📦 Inclui:

* Dockerfile para cada microserviço
* Dockerfile para o BFF
* `docker-compose.yml` com:

  * BFF
  * Microserviço de Usuário
  * Microserviço de Tarefas
  * Microserviço de Notificação
  * Banco de dados (se aplicável)

---

### ▶️ Como rodar o projeto

```bash
docker-compose up --build
```

---

### 🔄 Rebuild completo

```bash
docker-compose down -v
docker-compose up --build
```

---

### 📌 Benefícios do Docker

* Ambiente padronizado
* Fácil execução em qualquer máquina
* Isolamento dos serviços
* Simulação real de arquitetura distribuída

---

## ⚙️ Configurações Importantes

### 🔐 Credenciais do Cron

```properties
usuario.email=seu@email.com
usuario.senha=sua_senha
```

---

### ⏰ Expressão CRON

```properties
cron.horario=0 */5 * * * *
```

---

## 🔄 Integração entre Serviços

* 👤 Usuário → autenticação e dados
* 📅 Tarefas → gerenciamento de eventos
* 📧 Notificação → envio de emails

O BFF centraliza toda essa comunicação.

---


## 📌 Observações

* Arquitetura desacoplada e escalável
* Pronta para produção com pequenas melhorias
* Persistência híbrida (SQL + NoSQL)

---

## 👨‍💻 Autor

Projeto desenvolvido com foco em:

* Arquitetura de microsserviços
* Padrão BFF (Backend For Frontend)
* Integração com OpenFeign
* Automação com CRON
* Containerização com Docker
