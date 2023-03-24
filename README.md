# ads-api Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/ads-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)


#!/bin/bash

#Este script utilizado para criaÃ§Ã£o de variaveis no devops azure
# 20-05-2021
#############################################################################################################

TOKEN=YzExMjE0MTpqeWE3aGJmM29pdHA3cm11NHhhcWp4ejVnNDd5b3Y0aXlueDVwZHZtZXR0bnE3N2s3dTJh
SISTEMA=TESTE-CETAD-DES
VARIAVEIS=$(cat variaveis.txt)

response=$(curl --insecure --request GET --url "https://devops.caixa/projetos/Caixa/_apis/distributedtask/variablegroups?groupName=$SISTEMA" --header "Authorization:Basic $TOKEN")

echo $response
isVar=$(echo $response | ./jq -r '.count')
vars=""

valor=""

if [ "${isVar}" = 0 ]; then

	echo "Criando variaveis"
	
	for v in $VARIAVEIS; do


		echo $v
		if [ "$vars" = "" ];then
		vars=$(echo '"'$v'": {"value": "'$valor'"}')
		else
		vars=$(echo $vars',"'$v'": {"value": "'$valor'"}')
		fi

	done
	
	data=$(echo {'"name"': "'$SISTEMA'",'"description"': '"Grupo de variÃ¡veis de Desenvolvimento"','"variables"': {$vars}})
	response=$(curl --insecure --request POST -o /dev/null -s -w "%{http_code}\n" --url 'https://devops.caixa/projetos/Caixa/_apis/distributedtask/variablegroups?api-version=5.1-preview' --header "authorization: Basic $TOKEN " --header 'content-type: application/json'  --data "$data")
	echo "Resposta: "$response

	if [ $response = 200 ];then
		echo "Variaveis incluidas com sucesso"
	else
		echo"Erro na criaÃ§Ã£o"
	fi

else
	echo "Atualizando variaveis"
	idVar=$(echo $response | ./jq -r '.value[0].id')
	echo $idVar
	
	variables_teste=$(echo $response |./jq 'has(.value[0].variables)')
        echo $variables_teste
        echo ""


	variables=$(echo $response |./jq -r '.value[0].variables')
	echo $variables
	echo ""

	response_new=$(echo $response | ./jq '.value[0].variables += { "DATASOURCE_CONNECTION_URL2": { "value": "teste" }}')
	echo $response_new
	echo ""

	vars=$(echo $response_new |./jq -r '.value[0].variables')
        echo $vars
	echo ""
	
	data=$(echo {'"name"': "'$SISTEMA'",'"variables"': $vars})
	echo $data
       # response=$(curl --insecure --request PUT --url "https://devops.caixa/projetos/Caixa/_apis/distributedtask/variablegroups/$idVar?api-version=5.1-preview" --header "authorization: Basic $TOKEN " --header 'content-type: application/json'  --data "$data")


	


fi



