## Hack 03 - Console Freemarker em tempo real

Example:

```
<#assign userLocalService = serviceLocator.findService('com.liferay.portal.kernel.service.UserLocalService')/>

<#list userLocalService.getUsers(-1,-1) as user>
${user.firstName}<br/>
</#list>
```
