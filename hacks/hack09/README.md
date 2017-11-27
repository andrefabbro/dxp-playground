## Hack 09 - Integrando com API's do Liferay no gogo shell

#### Gosh Profile

Veja em configs/local/etc/.

Esse hack adiciona diversos arquivos na pasta home do gogo, o que faz com que sejam executados quando o gogo shell é inciado. Ele irá buscar pelos scripts na pasta 'scripts/' e criará os comandos baseado no nome do arquivo. Devops podem usar uma biblioteca de scripts úteis para serem invocados via gogo.

Para usar isso, use a pasta /etc no $LIFERAY_HOME, e copie os arquivos nessa pasta, é preciso também incluir a seguinte propriedade no portal-ext.properties:

```
module.framework.properties.gosh.home=${module.framework.base.dir}
```

com.liferay.portal.monitoring.configuration.MonitoringConfiguration.cfg

```
monitorPortletActionRequest=true
monitorPortalRequest=true
monitorPortletEventRequest=true
monitorPortletRenderRequest=true
monitorPortletResourceRequest=true
monitorServiceRequest=true
showPerRequestDataSample=true
```


Profile gosh em tomcat/bin/etc

http://enroute.osgi.org/appnotes/gogo.html