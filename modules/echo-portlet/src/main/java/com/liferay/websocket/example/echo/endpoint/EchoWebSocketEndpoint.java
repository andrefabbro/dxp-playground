package com.liferay.websocket.example.echo.endpoint;

import java.util.Locale;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.osgi.service.component.annotations.Component;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.registry.RegistryUtil;
import com.liferay.websocket.example.echo.constants.EchoPortletKeys;

@Component(
	immediate = true, 
	property = {
		"org.osgi.http.websocket.endpoint.path=" + EchoPortletKeys.ECHO_WEBSOCKET_PATH
		}, 
	service = Endpoint.class
)
public class EchoWebSocketEndpoint extends Endpoint {

	private final static Log _log = LogFactoryUtil.getLog(EchoWebSocketEndpoint.class);

	@Override
	public void onOpen(final Session session, EndpointConfig endpointConfig) {

        ModelListener<BlogsEntry> modelListener = new BaseModelListener<BlogsEntry>() {
            @Override
            public void onAfterCreate(BlogsEntry model) throws ModelListenerException {
            	
            	_log.info("CREATED " + model.getTitle());
            	_log.info("Attempting to send");

                if (session.isOpen()) {
                    try {
                        User user = UserLocalServiceUtil.getUser(model.getUserId());
                        String country = user.getAddresses().get(0).getCountry().getName(Locale.ENGLISH);

                        JSONObject json = JSONFactoryUtil.createJSONObject();
                        json.put("address", country);
                        session.getBasicRemote().sendText(json.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        };
        
        RegistryUtil.getRegistry().registerService(ModelListener.class, modelListener, null);
	}
}
