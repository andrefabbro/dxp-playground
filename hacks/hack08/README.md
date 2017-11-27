## Hack 08 - Shell Nashorn

Desde o JDK 6, o Java foi liberado contendo um motor de JavaScript com base no Rhino da Mozilla. Essa funcionalidade permite embarcar código JavaScript dentro do Java e até chamar no Java o código JavaScript embarcado.

Esse motor de javascript é provido pela biblioteca Nashorn, para acessar o console gogo, execute: 

```
telnet localhost 11311
```

Para adicionar o nashorn ao contexto do gogo shell, execute:

```
addcommand context ${.context} (${.context} class)
thefactoryclass = ((context:bundle 0) loadclass jdk.nashorn.api.scripting.NashornScriptEngineFactory)
theinstance = ($thefactoryclass newInstance)
engine = ($theinstance getScriptEngine -scripting)
$engine put "gogo" (( context:bundle 0 ) bundleContext)
$engine eval "var x = 1; var y = x+2; print(y);"
```

Para interagir com sucessivos comandos, execute:

```
while { result = ( $engine eval "var line = readLine('js$ '); line;" ) } { $engine eval $result }
```

Para exportar a API `javax.script`, é necessário incluir no portal-ext.properties:

```
module.framework.system.packages.extra=\
        javax.faces.convert,\
        javax.faces.webapp,\
        \
        #
        # Dynamic References
        #
        \
        com.mysql.jdbc,\
        com.sun.security.auth.module,\
        org.apache.naming.java,\
        sun.misc,\
        sun.security.provider,\
        javax.script, \
        javax.servlet;version="3.1.0", \
        javax.servlet.http;version="3.1.0"
```



