# Liferay DXP Hacks

Esse repositório contém um conjunto de hacks para o Liferay DXP, apresentado no Liferay Symposium SP em 2017, com o objetivo de aprender um pouco mais sobre a plataforma. Não deve ser usado em ambiente produtivo já que não foi apropriadamente testado.

Baseado em [A set of random hacks for Liferay 6.2 and Liferay 7](https://github.com/jamesfalkner/lr-hacks)

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

A apresentação pode ser encontrada em:

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/vh8vCaC31QI/0.jpg)](https://youtu.be/vh8vCaC31QI)

## Setup do Ambiente

Instalar última versão do Docker: https://www.docker.com/

Instalar o blade tools: https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/blade-cli

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

```
blade server start -d
```

Instalar os módulos:

```
cd modules/
blade deploy
```

