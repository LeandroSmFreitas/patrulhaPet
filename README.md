
# Patrulha Pet

**Patrulha Pet** é um projeto de gestão de adoção de animais. Este sistema tem como objetivo facilitar a adoção responsável de pets.



## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação utilizada para o desenvolvimento do back-end.
- **Docker**: Utilizado para gerenciar o container do banco de dados.
- **New Relic**: Implementado para coletar métricas e monitorar o desempenho do aplicativo.
- **Swagger**: Implementado para listar e testar os endpoints.
- **Jacoco**: Implementado para verificar a porcentagem de codígo coberta pelos testes.


## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar adicionar as seguintes variáveis de ambiente no seu application.properties

`spring.datasource.url`

`spring.datasource.username`

`spring.datasource.password`

`server.error.include-stacktrace`

`api.security.token.secret`


## Rodando os testes

Para rodar os testes, rode o seguinte comando, ele irá também gerar um relátorio feito pelo jacoco com a porcentagem de cobertura de testes

```bash
  ./gradlew clean test jacocoTestReport
```


## banco de dados

Para rodar o projeto primeiro precisa subir o container com o banco de dados

```bash
  /usr/local/bin/docker compose -f /seu/caminho/ate/patrulhapet/src/main/docker/mysql.yml -p docker up -d
```
ou você pode ir no proprio arquivo mysql.yml e subir clicando na seta que aparece ao lado de services
    
## Rodando projeto

Após a configuração das variáveis de ambiente, a configuração do banco de dados o projeto esta pronto para ser rodado.

```bash
  ./gradlew bootRun
```

você pode utilizar esse comando ou rodar pelo proprio intelij
## Metricas New Relic

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)

