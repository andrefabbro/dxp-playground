# Liferay DXP Hacks

Esse repositório contém um conjunto de hacks para o Liferay DXP, com o objetivo de aprender um pouco mais sobre a plataforma em um nível mais baixo. Não deve ser usado em ambiente produtivo já que não foi apropriadamente testado.

Verificar na pasta hacks cada um dos exemplos:

* Hack #01 - Aplicações usando WCM Templates
* Hack #02 - Templates com Dynamic Queries
* Hack #03 - Console Freemarker em tempo real
* Hack #04 - Organizando o seu portal-ext.properties
* Hack #05 - Social Driver Portlet
* Hack #06 - Proteção simples para Webservices
* Hack #07 - OSGi e Web Sockets
* Hack #08 - Integrando o gogo shell com Nashorn
* Hack #09 - API's do Liferay com Javascript
* Hack #10 - Grafo de Bundles OSGi

Cada hack contém seu próprio arquivo README contendo instruções.

## Setup do Ambiente

Instalar última versão do Docker: https://www.docker.com/

Instalar a blade: https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/blade-cli

Incluir uma licença válida na pasta:

```
configs/local/deploy
```

Incluir o pacote ```liferay-dxp-digital-enterprise-tomcat-7.0-sp4-20170705142422877.zip``` na pasta:

```
~/.liferay/bundles/
```

Incluir o patch ```liferay-fix-pack-de-32-7010.zip``` na seguinte pasta:

```
~/.liferay/fix-packs/
```

Incluir o patching tool versão 2.0.7 (```patching-tool-2.0.7.zip```) na pasta:

```
~/.liferay/patching-tool/
```

Rodar o script para criação do ambiente:

```
./bootstrap.sh init
```

Executar o portal usando:

```
blade server start
```

Para executar em modo debug:

Executar o portal usando:

```
blade server start -d
```
