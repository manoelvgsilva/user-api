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

## User
### Cria um novo usuario

<details>
    <summary><code>POST</code> <code><b>/user</b></code> <code>(Cria um novo usuario)</code></summary>

##### Parametros
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | Body      |  required | object (JSON)   | User obj |


##### Example cURL

> ```java
>  curl -X 'POST' 'http://localhost:8080/users' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"username": "João Baptista", "dataNasc": "2000-01-01", "password": "123abc", "cpf": "01234567890", "phone": "01234567890", "email": "joaobaptista@gmail.com", "role": "ADMIN"}'
> ```
</details>

_____________________________________________________________

### gera o login do usuario

<details>
    <summary><code>POST</code> <code><b>/auth/login/</b></code> <code>(Gera o login do usuario)</code></summary>

##### Parameters
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | Body      |  required | object (JSON)   | User obj |
 
##### Example cURL
> ```java
>  curl -X 'POST' 'http://localhost:8080/auth/login' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"email": "joaobaptista@gmail.com", "password": "123abc"}'
> ```
</details>

______________________________________________________________________________________

### captura o usuario pelo email

<details>
    <summary><code>GET</code> <code><b>/user/{email}</b></code> <code>(faz o login do usuario com o token gerado anteriormente)</code></summary>

##### Parametro
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | email      |  required | string | User email |

##### Example cURL

> ```java
>  curl -X 'GET' 'http://localhost:8080/users/joaobaptista@gmail.com'
> ```
</details>

______________________________________________________________________________________

### Edita dados de usuario

<details>
    <summary><code>PUT</code> <code><b>/user/{cpf}</b></code> <code>(edita dados do usuario)</code></summary>

##### Parametros
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | cpf      |  required | string | User cpf |
> | Body      |  required | object(json) | User obj |


##### Example cURL

> ```java
>  curl -X 'PUT' 'http://localhost:8080/users/01234567890' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{"username": "João Batista", "dataNasc": "2000-01-01", "password": "123abc", "phone": "012345670965", "email": "joaobatista@gmail.com", "role": "ADMIN"}'
> ```
</details>

______________________________________________________________________________________

## Users
### captura todos os usuarios

<details>
    <summary><code>GET</code> <code><b>/users</b></code> <code>(captura todos os usuarios)</code></summary>

##### Parametros
> none


##### Example cURL

> ```java
>  curl -X 'GET' 'http://localhost:8080/users'
> ```
</details>
