# user-api

api de cadastro de usuarios de um sistema ecommerce contendo as seguintes 
rotas GET, POST E UPDATE

# Pré requisitos

Java 17
Maven 3.6
Docker
Mongo 6
Kafka

# Instalação

1. Clone o repositório:
   ```zsh
   git clone https://github.com/manoelvgsilva/user-api.git
   ```

2. Navegue até o diretório do projeto:
   ```zsh
   cd user-api
   ```

3. Instale as dependências:
   ```zsh
   mvn install
   ```

4. Para rodar em Docker:
   ```zsh
   docker-compose up -d
   ```

5. Inicie a aplicação:
   ```zsh
   mvn spring-boot:run
   ```

### Endpoints principais:

- `GET /users`: Retorna a lista de usuarios para o administrador do sistema.
- `POST /users`: Cria um novo usuário.
- `GET /users/{email}`: Retorna um usuario especifico atraves do email.
- `PUT /users/{cpf}`: Faz atualização de dados de um usuario.

#### Exemplo de requisição para criar um usuario:
```zsh
curl -X POST http://localhost:8080/users
-H "Content-Type: application/json" \
-d '{
    "username": "João Baptista",
    "dataNasc": "2000-01-01",
    "password": "123abc",
    "cpf": "01234567890",
    "phone": "01234567890",
    "email": "alguem@gmail.com",
    "role": "ADMIN"
}'