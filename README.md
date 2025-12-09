# 🗳️ Desafio Votação – API

API REST desenvolvida em **Spring Boot 3 + Java 21**, responsável por gerenciar **pautas, sessões de votação, associados e votos**, seguindo boas práticas de arquitetura, validação, testes automatizados e documentação via Swagger.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4**
- Spring Data JPA
- Spring Validation
- **H2 Database (file-based)**
- **Swagger / OpenAPI (springdoc)**
- Lombok
- Logback + Logstash Encoder
- Gradle
- JUnit 5
- Mockito
- AssertJ
- **JaCoCo (cobertura de testes)**

---

## 📦 Pré-requisitos

Antes de executar a aplicação, você precisa ter instalado:

- ✅ **Java JDK 21**
- ✅ **Gradle** (ou usar o wrapper `./gradlew`)
- ✅ Git (opcional, mas recomendado)

---

## ▶️ Como Executar a Aplicação

### 1️⃣ Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd desafio-votacao
```

---

### 2️⃣ Build do projeto

```bash
./gradlew clean build
```

> Esse comando irá:
> - Compilar o projeto
> - Executar os testes
> - Gerar relatório de cobertura (JaCoCo)

---

### 3️⃣ Executar a aplicação

```bash
./gradlew bootRun
```

✅ A aplicação iniciará em:

```text
http://localhost:8080
```

---

## 📄 Documentação da API (Swagger)

A API é totalmente documentada via **Swagger UI**:

```text
http://localhost:8080/swagger-ui/index.html#/
```

No Swagger é possível:
- Visualizar todos os endpoints
- Conferir modelos de request e response
- Executar chamadas diretamente pelo navegador

---

## 🗄️ Banco de Dados (H2)

O projeto utiliza **H2 file-based**, permitindo persistência dos dados localmente.

### 📍 Configuração

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./src/main/resources/voting-db
```

---

### 🧪 Console do H2

O console do banco pode ser acessado em:

```text
http://localhost:8080/h2-console
```

Configuração para login:

| Campo       | Valor                                         |
|------------|-----------------------------------------------|
| JDBC URL   | `jdbc:h2:file:./src/main/resources/voting-db` |
| User       | `sa`                                          |
| Password   | *(vazio)*                                     |

---

## ⚙️ Configurações Importantes

### application.yml

- Perfil ativo: **dev**
- Porta padrão: **8080**
- Fuso horário global: **UTC**
- `ddl-auto`: `update`
- Logs detalhados habilitados para o pacote da aplicação

---

## ✅ Testes e Cobertura (JaCoCo)

### Executar testes

```bash
./gradlew test
```

### Relatório de cobertura

Após a execução dos testes, o relatório HTML estará disponível em:

```text
build/reports/jacoco/test/html/index.html
```

📌 Classes ignoradas na cobertura de testes:
- `config`
- `dto`
- `exception`
- `*Application`

---

## 🧠 Padrões e Boas Práticas

- Arquitetura em camadas bem definida
- Separação clara entre Controller, Service e Repository
- Uso de DTOs para entrada e saída de dados
- Validações com **Bean Validation**
- Datas e horários tratados em **UTC**
- Organização de código focada em domínio
- Código testável e de fácil manutenção

---

## 🔗 Links Importantes

- Swagger UI  
  👉 http://localhost:8080/swagger-ui/index.html#/

- H2 Console  
  👉 http://localhost:8080/h2-console

- Relatório JaCoCo  
  👉 `build/reports/jacoco/test/html/index.html`

---

## 🏁 Considerações Finais

Este projeto foi desenvolvido com foco em:

- ✅ Clareza de domínio
- ✅ Código limpo e bem organizado
- ✅ Testes automatizados
- ✅ Facilidade de execução e avaliação

O código foi pensado para evoluir e escalar de forma sustentável 🚀
