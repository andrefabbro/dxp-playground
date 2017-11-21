package com.liferay.devhacks.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Raymond Augé
 */
public class ServiceLocatorHack {

	public static ServiceLocatorHack getInstance() {
		return _instance;
	}

	public Object findService(String serviceName) {
		Object bean = null;

		try {
			bean = PortalBeanLocatorUtil.locate(_getServiceName(serviceName));
		} catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	public Object findService(String servletContextName, String serviceName) {
		Object bean = null;

		try {
			bean = PortletBeanLocatorUtil.locate(servletContextName, _getServiceName(serviceName));
		} catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	private ServiceLocatorHack() {
	}

	public static final String VELOCITY_SUFFIX = ".velocity";

	private String _getServiceName(String serviceName) {
		if (!serviceName.endsWith(VELOCITY_SUFFIX)) {
			serviceName += VELOCITY_SUFFIX;
		}

		return serviceName;
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceLocatorHack.class);

	private static ServiceLocatorHack _instance = new ServiceLocatorHack();

}
