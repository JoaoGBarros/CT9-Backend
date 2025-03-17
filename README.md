# CT9 Na Palma Mão - Backend

## Introdução
O backend do **CT9 Na Palma Mão** é responsável pelo processamento de dados e regras de negócio do sistema. Ele fornece uma API RESTful para comunicação com o frontend web e o aplicativo móvel, permitindo a gestão de usuários, disciplinas e informações acadêmicas.

## Tecnologias Utilizadas

O backend foi desenvolvido utilizando as seguintes tecnologias:

- **Java 19** - Linguagem de programação principal.
- **Spring Boot 3.1.3** - Framework para desenvolvimento de aplicações web.
- **PostgreSQL 16.0** - Banco de dados relacional.
- **Docker 27.1.1** - Para containerização e gestão de ambientes.
- **IntelliJ IDEA** - IDE utilizada para desenvolvimento.

## Passo a Passo para Rodar o Backend

### 1. Clonar o Repositório
```bash
git clone https://gitlab.com/ct9-app/ct9-backend.git
cd ct9-backend
```

### 2. Configurar o Banco de Dados
Certifique-se de ter o **PostgreSQL** instalado e rodando.

Atualize as credenciais no arquivo `application.properties` ou `application.yml`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ct9_palma_mao
spring.datasource.username= <seu_usuario>
spring.datasource.password= <sua_senha>
```

### 3. Construir o Projeto
Abra o IntelliJ IDEA e importe o projeto. Em seguida, execute o seguinte comando para compilar o projeto:
```bash
./mvnw clean install
```

### 4. Executar a Aplicação
Para rodar a aplicação, utilize o comando:
```bash
./mvnw spring-boot:run
```

Ou execute diretamente pelo IntelliJ IDEA:
1. Abra a classe principal (`Application.java`).
2. Clique com o botão direito e selecione "Run".

## 5. Testar a API
Faça requisições via **Insomnia** ou **Postman**.

## 6. Hostear a Aplicação

Passo a Passo para Hostear a Aplicação no Google Cloud Run

1. Pré-requisitos:

   Conta no Google Cloud: Crie uma conta no Google Cloud.
   Google Cloud SDK: Instale o Google Cloud SDK.

2. Configurar o Google Cloud SDK

No terminal do Google Cloud SDK execute os seguintes comandos:
```bash
  gcloud init
  gcloud projects create [PROJECT_ID]
  gcloud config set project [PROJECT_ID]
```

Substitua [PROJECT_ID] pelo ID do seu projeto.

4. Habilitar APIs necessárias
```bash
  gcloud services enable cloudbuild.googleapis.com run.googleapis.com
```

5. Construir a imagem Docker
```bash
  gcloud builds submit --config cloudbuild.yaml .
```

6. Deploy no Google Cloud Run

Acesse o projeto no Google Cloud Console e clique em "Cloud Run" no menu lateral. Em seguida,
acesse: 
"Implantar Container" -> "Criar Serviço" -> "Implantar uma revisão de uma imagem de contêiner atual" -> Selecione a imagem criada (estara marcada como latest) 

## 7. Variaveis de Ambiente

Para configurar variáveis de ambiente para diferentes ambientes (local e Docker), siga estes passos:

1. Crie arquivos .env:  
   - .env.local para desenvolvimento local
   - .env.docker para ambiente Docker

2. Adicione variáveis de ambiente nos respectivos arquivos .env. Por exemplo:
  
- **.env.local:**
  ``` bash
  SPRING_APPLICATION_NAME=ct9-backend
  SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ct9
  SPRING_DATASOURCE_USERNAME=postgres
  SPRING_DATASOURCE_PASSWORD=123
  SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
  SPRING_PROFILES_ACTIVE=local
  SPRING_JPA_HIBERNATE_DDL_AUTO=update
  SPRING_JPA_SHOW_SQL=true
  SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
  ```
- **.env.docker:**
  ``` bash
  SPRING_APPLICATION_NAME=ct9-backend
  SPRING_DATASOURCE_URL=BANCO_DE_DADOS_URL
  SPRING_DATASOURCE_USERNAME=DB_USER
  SPRING_DATASOURCE_PASSWORD=DB_PASSWORD
  SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
  SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
  SPRING_JPA_PROPERTIES_HIBERNATE_TEMP_USE_JDBC_METADATA_DEFAULTS=false
  SPRING_JPA_SHOW_SQL=true
  SPRING_JPA_HIBERNATE_DDL_AUTO=update
  MANAGEMENT_DEFAULTS_METRICS_EXPORT_ENABLED=false
  MANAGEMENT_METRICS_ENABLE_PROCESS=false
  MANAGEMENT_METRICS_ENABLE_SYSTEM=false
  ```

3. Configure o IntelliJ IDEA para usar o arquivo .env apropriado:
  - Vá para Run > Edit Configurations....
  - Selecione a configuração da sua aplicação Spring Boot.
  - No campo Environment variables (Modify Options), adicione ENVIRONMENT=local para desenvolvimento local ou ENVIRONMENT=docker para ambiente Docker.
  - Certifique-se de que sua classe DotenvConfig está corretamente configurada para carregar o arquivo .env apropriado com base na variável ENVIRONMENT.  
  - Seguindo esses passos, sua aplicação carregará as variáveis de ambiente corretas para os ambientes local e Docker


## 8. Endpoints

### AuthController

- **POST /auth/verifyRole**

    - Verifica o papel do usuário.
    - **Request Body:** `String uid`
    - **Response:** `Map<String, String>`


### CursosController

- **GET /cursos/teste**

    - Teste de cadastro de curso.
    - **Response:** `String`


- **GET /cursos/getTodosCursos**

    - Retorna todos os cursos cadastrados.
    - **Response:** `Iterable<Curso>`


- **GET /cursos/getCurso/{id}**

    - Retorna um curso específico pelo ID.
    - **Path Variable:** `Long id`
    - **Response:** `Optional<Curso>`


- **POST /cursos/cadastrarCurso**

    - Cadastra um novo curso.
    - **Request Body:** `Curso curso`
    - **Response:** `Long`


- **PUT /cursos/editarCurso/{id}**

    - Edita um curso existente.
    - **Path Variable:** `Long id`
    - **Request Body:** `Curso curso`
    - **Response:** `Long`


- **GET /cursos/{id}/getDisciplinas**

    - Retorna as disciplinas de um curso específico.
    - **Path Variable:** `Long id`
    - **Response:** `List<Disciplina>`

### DisciplinaController

- **GET /disciplina/{disciplinaId}**

    - Retorna uma disciplina específica pelo ID.
    - **Path Variable:** `Long disciplinaId`
    - **Response:** `Disciplina`


- **PUT /disciplina/editar/{disciplinaId}**

    - Edita uma disciplina existente.
    - **Path Variable:** `Long disciplinaId`
    - **Request Body:** `Disciplina disciplina`
    - **Response:** `Disciplina`


- **GET /disciplina/getDisciplinasOfertadas/{semestreId}**

    - Retorna as disciplinas ofertadas em um semestre específico.
    - **Path Variable:** `Long semestreId`
    - **Response:** `List<DisciplinaOfertada>`


- **POST /disciplina/saveDisciplinasOfertadas/{cursoId}/{semestreId}**

    - Salva as disciplinas ofertadas para um curso e semestre específicos.
    - **Path Variables:** `Long cursoId`, `Long semestreId`
    - **Request Body:** `List<DisciplinaOfertadaDTO>`
    - **Response:** `List<DisciplinaOfertada>`

### ExtensaoController

- **GET /extensao/getTodasExtensao**

    - Retorna todas as extensões cadastradas.
    - **Response:** `List<Extensao>`


- **POST /extensao/salvarExtensao**

    - Salva uma nova extensão.
    - **Request Body:** `Extensao extensao`
    - **Response:** `Extensao`


- **GET /extensao/getExtensao/{id}**

    - Retorna uma extensão específica pelo ID.
    - **Path Variable:** `Long id`
    - **Response:** `Extensao`

### NoticiasController

- **GET /noticias/getTodasNoticias**

    - Retorna todas as notícias cadastradas.
    - **Response:** `Iterable<Noticia>`


- **POST /noticias/salvarNoticia**

    - Salva uma nova notícia.
    - **Request Body:** `Noticia noticia`
    - **Response:** `Noticia`


- **GET /noticias/getNoticia/{id}**

    - Retorna uma notícia específica pelo ID.
    - **Path Variable:** `Long id`
    - **Response:** `Noticia`

### PPCController

- **POST /ppc/{id}/cadastrarPPC**

    - Cadastra um novo PPC para um curso específico.
    - **Path Variable:** `Long id`
    - **Request Body:** `PPC ppc`
    - **Response:** `PPC`


- **GET /ppc/{id}/getPPCs**

    - Retorna todos os PPCs de um curso específico.
    - **Path Variable:** `Long id`
    - **Response:** `List<PPC>`


- **GET /ppc/getPPC/{cursoId}/{ppcId}**

    - Retorna um PPC específico pelo ID do curso e ID do PPC.
    - **Path Variables:** `Long cursoId`, `Long ppcId`
    - **Response:** `PPC`

### SemestreController
- **GET /semestre/getSemestres/{cursoId}**

    - Retorna todos os semestres de um curso específico.
    - **Path Variable:** `Long cursoId`
    - **Response:** `List<Semestre>`


- **POST /semestre/salvarSemestre/{cursoId}**

    - Salva um novo semestre para um curso específico.
    - **Path Variable:** `Long cursoId`
    - **Request Body:** `Semestre semestre`
    - **Response:** `Semestre`


- **GET /semestre/getSemestre/{semestreId}**

    - Retorna um semestre específico pelo ID.
    - **Path Variable:** `Long semestreId`
    - **Response:** `Semestre`

