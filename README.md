# Spring-boot com AWS localstack SQS

Projeto de demonstração de usu do serviço SQS da AWS, mas utilizando o localstack para mockar o serviço.

### Referências
1. [https://dev.to/hebertrfreitas/localstack-um-mock-funcional-para-os-servicos-da-aws-1jkd](https://dev.to/hebertrfreitas/localstack-um-mock-funcional-para-os-servicos-da-aws-1jkd)
2. [https://medium.com/codex/spring-boot-sns-localstack-619b9b75f2ac](https://medium.com/codex/spring-boot-sns-localstack-619b9b75f2ac)


### Requisitos

* Java 11
* Maven
* Docker
* Docker Compose

Rodar o comando docker-compose up -d
- Utilizei apesnas o serviço "sqs", mas você pode usar vários, como s3, apigateway, dynamodb ...
- O arquivo startup.sh, em init-scripts ja cria a fila no SQS a ser utilizada.
```yaml
version: "3.6"

services:
  localstack:
    container_name: "localstack"
    image: localstack/localstack:latest
    network_mode: bridge
    ports:
      - "127.0.0.1:4566:4566"
      - "127.0.0.1:4571:4571"
    environment:
      - AWS_DEFAULT_REGION=ap-southeast-1
      - SERVICES= ${SERVICES-sqs}
      - DEBUG=${DEBUG- }
      - DATA_DIR=${DATA_DIR- }
      - LAMBDA_EXECUTOR=${LAMBDA_EXECUTOR- }
      - LOCALSTACK_API_KEY=${LOCALSTACK_API_KEY- }
      - HOST_TMP_FOLDER=${TMPDIR:-/tmp/}localstack
      - DOCKER_HOST=unix:///var/run/docker.sock
    volumes:
      - "${TMPDIR:-/tmp}/localstack:/tmp/localstack"
      - "/var/run/docker.sock:/var/run/docker.sock"
      - ./init-scripts:/docker-entrypoint-initaws.d
```

Após subir o docker, entre na pasta **procucer** e execute mvn spring-boot:run
e faça o mesmo para **consumer**, ou rode a partir de sua IDE favorita.

Você pode fazer um POST para http://localhost:6080/sendMessage, com qualquer payload JSON, e ele será publicado no SQS.

Você pode ver o consumer recebendo a mensagem enviada no console.