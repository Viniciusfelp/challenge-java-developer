# Challenge Java Developer — Neurotech

API RESTful para cadastro de clientes e análise de elegibilidade a modalidades de crédito, incluindo regras para financiamento automotivo por modelo de veículo. Projeto construído em Spring Boot com MySQL e documentado via OpenAPI/Swagger.

> Porta padrão: 5000 | Swagger UI: http://localhost:5000/swagger-ui.html | API Docs (JSON): http://localhost:5000/api-docs

---

## Sumário
- [Tecnologias](#tecnologias)
- [Arquitetura e Padrões](#arquitetura-e-padrões)
- [Pré-requisitos](#pré-requisitos)
- [Configuração](#configuração)
  - [Variáveis de ambiente (.env)](#variáveis-de-ambiente-env)
  - [Banco de dados](#banco-de-dados)
- [Como executar](#como-executar)
  - [Executar com Docker Compose (recomendado)](#executar-com-docker-compose-recomendado)
  - [Executar localmente (Maven)](#executar-localmente-maven)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Endpoints principais e exemplos](#endpoints-principais-e-exemplos)
- [Testes](#testes)
- [Build e empacotamento](#build-e-empacotamento)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Solução de problemas](#solução-de-problemas)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## Tecnologias
- Java 21
- Spring Boot (Web, Validation)
- Spring Data JPA
- MySQL 8
- Flyway (migrações)
- OpenAPI 3 / Swagger UI
- Docker e Docker Compose
- Maven Wrapper (mvnw)

## Arquitetura e Padrões
- Camadas bem definidas: controller → service → repository → entity.
- Regras de negócio isoladas no pacote `rules` (ex.: juros fixos/variáveis, consignado, elegibilidade por veículo Hatch/SUV).
- Migrações de banco de dados versionadas com Flyway em `src/main/resources/db/migration`.
- Tratamento de erros centralizado (`exception/RestExceptionHandler`).
- Documentação OpenAPI configurada em `SwaggerConfig`.

## Pré-requisitos
- Docker e Docker Compose instalados
- Opcional (execução local):
  - Java 21 (JDK)
  - Maven (ou use o Maven Wrapper incluído)

## Configuração
### Variáveis de ambiente (.env)
Crie um arquivo `.env` na raiz do projeto com as variáveis abaixo (valores de exemplo já compatíveis com `application.yaml` e `compose.yml`):

```
MYSQL_ROOT_PASSWORD=senha_root_segura
MYSQL_DATABASE=neurotech
MYSQL_USER=credito
MYSQL_PASSWORD=credito1234
```

Variáveis consumidas pela aplicação (via `application.yaml`):
- `SERVER_PORT` (padrão: 5000)
- `DB_URL` (padrão local: jdbc:mysql://localhost:3306/neurotech)
- `DB_USER` (padrão: credito)
- `DB_PASS` (padrão: credito1234)

### Banco de dados
- Em desenvolvimento com Docker, o MySQL sobe via `compose.yml` na porta 3306 e é referenciado pela aplicação como `mysql` (hostname do serviço).
- Migrações Flyway rodam automaticamente na inicialização.

## Como executar
### Executar com Docker Compose (recomendado)
1) Crie o `.env` conforme acima.
2) Build e subida dos serviços:

```bash
# Linux/macOS/PowerShell
docker compose up -d --build
```

- App disponível em: http://localhost:5000
- Swagger UI: http://localhost:5000/swagger-ui.html
- Para logs: `docker compose logs -f app`
- Para parar: `docker compose down`

### Executar localmente (Maven)
1) Suba um MySQL localmente (ou via Docker apenas do banco):

```bash
docker run --name neurotech-mysql -e MYSQL_ROOT_PASSWORD=senha_root_segura -e MYSQL_DATABASE=neurotech -e MYSQL_USER=credito -e MYSQL_PASSWORD=credito1234 -p 3306:3306 -d mysql:8.0
```

2) Configure `src/main/resources/application.yaml` se necessário (as variáveis já possuem defaults compatíveis).

3) Build e execução:

```bash
# macOS/Linux
./mvnw clean spring-boot:run

# Windows (PowerShell ou CMD)
mvnw.cmd clean spring-boot:run
```

Aplicação em http://localhost:5000

## Documentação da API (Swagger)
- Swagger UI: http://localhost:5000/swagger-ui.html
- API Docs (OpenAPI JSON): http://localhost:5000/api-docs

## Endpoints principais e exemplos
Clientes
- POST /api/client — Cadastra um novo cliente
  Exemplo:
  ```bash
  curl -i -X POST http://localhost:5000/api/client \
    -H "Content-Type: application/json" \
    -d '{"name":"João Silva","age":24,"income":10000.0}'
  ```
  Retorna 201 Created com Location: /api/client/{id}

- GET /api/client/{id} — Busca cliente por ID
  ```bash
  curl -s http://localhost:5000/api/client/{id}
  ```

Crédito
- GET /api/credit/{id}/check/{vehicleModel} — Verifica elegibilidade de crédito para modelo de veículo específico.
  - `vehicleModel` aceitos: ver enum `VehicleModel` (ex.: HATCH, SUV).
  ```bash
  curl -s http://localhost:5000/api/credit/{id}/check/HATCH
  ```

- GET /api/credit/eligible-clients/hatch-fixed-interest — Lista paginada de clientes elegíveis a HATCH com juros fixos.
  Suporta paginação padrão Spring (`page`, `size`, `sort`).
  ```bash
  curl -s "http://localhost:5000/api/credit/eligible-clients/hatch-fixed-interest?page=0&size=10"
  ```

Regras de crédito (resumo)
- Juros fixos: idade entre 18 e 25 anos (5% a.a.).
- Juros variáveis: idade entre 21 e 65 anos, renda entre R$ 5.000 e R$ 15.000.
- Consignado: acima de 65 anos.
- Elegibilidade automotiva:
  - Hatch: renda entre R$ 5.000 e R$ 15.000.
  - SUV: renda acima de R$ 8.000 e idade > 20 anos.

## Testes
Execute a suíte de testes:
```bash
# macOS/Linux
./mvnw test

# Windows
mvnw.cmd test
```

## Build e empacotamento
Gera o JAR fat (com dependências) em `target/`:
```bash
# macOS/Linux
./mvnw -DskipTests package

# Windows
mvnw.cmd -DskipTests package
```
Imagem Docker (alternativa sem Compose):
```bash
docker build -t challenge-java-developer:latest .
docker run --rm -p 5000:5000 --name challenge-java-developer \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/neurotech \
  -e DB_USER=credito -e DB_PASS=credito1234 \
  challenge-java-developer:latest
```

## Estrutura do projeto
```
src/
├── main/
│   ├── java/br/com/neurotech/challenge/
│   │   ├── controller/      # Controladores REST
│   │   ├── DTOs/            # Objetos de Transferência de Dados
│   │   ├── entity/          # Entidades JPA
│   │   ├── exception/       # Exceções e handler global
│   │   ├── repository/      # Repositórios Spring Data
│   │   ├── rules/           # Regras de negócio de crédito
│   │   ├── service/         # Serviços de domínio
│   │   └── config/          # Configurações (Swagger/OpenAPI)
│   └── resources/
│       ├── application.yaml  # Configurações e paths do Swagger
│       └── db/migration/     # Scripts de migração Flyway
└── test/                     # Testes unitários e de integração
```

## Solução de problemas
- Porta ocupada 5000: altere `SERVER_PORT` no `.env` ou exporte antes de subir, e ajuste mapeamento no compose.
- Banco não sobe/erros de conexão: verifique `.env`, aguarde healthcheck do MySQL, confira `DB_URL` (no Compose é `jdbc:mysql://mysql:3306/neurotech`).
- Swagger não abre: confirme a URL `/swagger-ui.html` e se a aplicação iniciou sem erros.

## Contribuição
- Abra issues/pull requests descrevendo claramente o problema/melhoria.
- Padrões de commit sugeridos: Conventional Commits.
- Inclua testes quando possível.

## Licença
Este projeto é parte de um desafio técnico e não possui licença específica pública. Caso necessário, adicione uma licença conforme a política da sua organização.
