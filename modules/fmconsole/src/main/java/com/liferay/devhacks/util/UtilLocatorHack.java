package com.liferay.devhacks.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Raymond Augé
 */
public class UtilLocatorHack {

	public static UtilLocatorHack getInstance() {
		return _instance;
	}

	public Object findUtil(String utilName) {
		Object bean = null;

		try {
			bean = PortalBeanLocatorUtil.locate(_getUtilName(utilName));
		} catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	public Object findUtil(String servletContextName, String utilName) {
		Object bean = null;

		try {
			bean = PortletBeanLocatorUtil.locate(servletContextName, _getUtilName(utilName));
		} catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	private UtilLocatorHack() {
	}

	private String _getUtilName(String utilName) {
		if (!utilName.endsWith(ServiceLocatorHack.VELOCITY_SUFFIX)) {
			utilName += ServiceLocatorHack.VELOCITY_SUFFIX;
		}

		return utilName;
	}

	private static Log _log = LogFactoryUtil.getLog(UtilLocatorHack.class);

	private static UtilLocatorHack _instance = new UtilLocatorHack();

}
