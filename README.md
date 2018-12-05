# Lançamentos Contábeis (WIP)

Sistema para lançamentos de contas contábeis

## Como rodar este projeto

### Pré requisitos

- **jdk8+**
- **docker**

### Instalação

- Clonar este projeto
- Entrar na pasta raiz e construir o projeto:

```
Linux
./gradlew clean install

Windows
gradlew.bat clean install
```
 
### Rodando os testes
```
Linux
./gradlew test

Windows
gradlew.bat test
```

 
### Rodando o projeto localmente

##### Pré requisitos
- mongodb instalado na maquina

##### Instruções
- configurar o endereço do mongo no arquivo ``application.yml``
- iniciar o programa com o seguinte comando:   
```
Linux
./gradlew bootRun

Windows
gradlew.bat bootRun
```

### Rodando o projeto em ambiente dockerizado

- construir o projeto
```
./gradlew clean build
```
- e executar o seguinte comando para construir a imagem docker
```
$ docker-compose build
```
- e, por fim, iniciar os containers
```
$ docker-compose up
```
- o teste pode ser feito acessando o endereço ``localhost:8080/actuator``

### Escalando a aplicação
- é possivel escalar a aplicação rodando o seguinte comando:
```
docker-compose scale web=2
```
- assim o sistema ficara disponivel também em ``localhost:8081``

### À melhorar?
- retornar os valores das estatísticas fracionado com precisão de duas casas decimais
- aumentar cobertura de testes com mais fluxos alternativos
- colocar um load balance apontando para o micro serviço
- colocar um orchestrador de container




