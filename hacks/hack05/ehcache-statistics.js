// ==============================================================
// Verificar as estatisticas de cache (javascript)
// ==============================================================

out.println("************* CONFIGURATION EHCACHE **********");
svm = Packages.com.liferay.portal.kernel.cache.MultiVMPoolUtil.getMultiVMPool();
f = svm.getClass().getDeclaredField("_portalCacheManager");
f.setAccessible(true);
val = f.get(svm)
out.println(
  Packages.org.apache.commons.lang.StringEscapeUtils.escapeXml(val.getEhcacheManager().getActiveConfigurationText())
);

out.println("************* STATISTICS EHCACHE **********");
names = val.getEhcacheManager().getCacheNames();
for (i=0; i < names.length ; i++ ) {
  cache = val.getEhcacheManager().getCache(names[i]);
  out.println(names[i]);
  out.println('Hit='+cache.getStatistics().cacheHitCount());
  out.println('Miss='+cache.getStatistics().cacheMissCount());
  out.println('LocalHeapSize='+cache.getStatistics().getLocalHeapSize());
  out.println('EvictedCount ='+cache.getStatistics().cacheEvictedCount());
}