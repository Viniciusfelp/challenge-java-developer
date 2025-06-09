# Challenge Java Developer - Neurotech

Este projeto é uma API RESTful para avaliação e aplicação de diferentes modalidades de crédito a clientes PF, de acordo com critérios específicos. Desenvolvido como parte do desafio para desenvolvedores Java da Neurotech.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- Swagger/OpenAPI para documentação
- Flyway para migrações de banco de dados
- Maven

## Funcionalidades

O sistema oferece diferentes modalidades de crédito baseadas em critérios específicos:

- **Crédito com Juros fixos**: Aplicado a clientes com idade entre 18 e 25 anos, independente de renda. Taxa de 5% a.a.
- **Crédito com Juros variáveis**: Aplicado a clientes com idade entre 21 e 65 anos, com renda entre R$ 5.000,00 e R$ 15.000,00.
- **Crédito Consignado**: Aplicado a clientes acima de 65 anos, independente de renda.

Além disso, o sistema avalia a elegibilidade para crédito automotivo baseado no modelo do veículo:
- **Hatch**: Renda entre R$ 5.000,00 e R$ 15.000,00.
- **SUV**: Renda acima de R$ 8.000,00 e idade superior a 20 anos.

## Configuração do Ambiente

### Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven

### Configuração do Banco de Dados

O projeto utiliza MySQL como banco de dados. Para configurar o banco de dados usando Docker:

1. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:
```
MYSQL_ROOT_PASSWORD=sua_senha_root
MYSQL_DATABASE=neurotech
MYSQL_USER=credito
MYSQL_PASSWORD=credito1234
```

2. Inicie o container do MySQL:
```bash
docker-compose up -d
```

### Executando a Aplicação

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/challenge-java-developer.git
cd challenge-java-developer
```

2. Compile o projeto:
```bash
./mvnw clean install
```

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: http://localhost:5000

## Documentação da API

A documentação da API está disponível através do Swagger UI:
- URL: http://localhost:5000/swagger-ui.html

### Endpoints Principais

#### Clientes
- `POST /api/client` - Cadastra um novo cliente
- `GET /api/client/{id}` - Retorna os dados de um cliente específico

#### Crédito
- `GET /api/credit/{id}/check/{vehicleModel}` - Verifica se um cliente está apto a receber crédito para um determinado modelo de veículo
- `GET /api/credit/eligible-clients/hatch-fixed-interest` - Retorna todos os clientes entre 23 e 49 anos que possuem Crédito com juros fixos e estão aptos a adquirirem crédito automotivo para veículos do tipo Hatch

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── com/
│   │           └── neurotech/
│   │               └── challenge/
│   │                   ├── controller/    # Controladores REST
│   │                   ├── DTOs/          # Objetos de Transferência de Dados
│   │                   ├── entity/        # Entidades JPA
│   │                   ├── exception/     # Exceções personalizadas
│   │                   ├── repository/    # Repositórios Spring Data
│   │                   ├── rules/         # Regras de negócio
│   │                   ├── service/       # Serviços
│   │                   └── ChallengeJavaDeveloperApplication.java
│   └── resources/
│       ├── application.yaml  # Configurações da aplicação
│       └── db/
│           └── migration/    # Scripts de migração Flyway
└── test/                     # Testes automatizados
```

## Testes

Para executar os testes:

```bash
./mvnw test
```

## Licença

Este projeto é parte de um desafio de código e não possui uma licença específica.
