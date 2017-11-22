// ==============================================================
// Verificar as estatisticas de cache (groovy)
// ==============================================================
import com.liferay.portal.kernel.cache.MultiVMPoolUtil
import org.apache.commons.lang.StringEscapeUtils

cacheManager = MultiVMPoolUtil.getPortalCacheManager().getEhcacheManager()

println "************* STATISTICS EHCACHE **********"
names = cacheManager.getCacheNames();
names.each { i ->
    cache = cacheManager.getCache(i);
    println i
    println 'Hit=' + cache.getStatistics().cacheHitCount()
    println 'Miss=' + cache.getStatistics().cacheMissCount()
    println 'LocalHeapSize=' + cache.getStatistics().getLocalHeapSize()
    println 'EvictedCount =' + cache.getStatistics().cacheEvictedCount()
}

println "************* CONFIGURATION EHCACHE **********"
println StringEscapeUtils.escapeXml(cacheManager.getActiveConfigurationText())