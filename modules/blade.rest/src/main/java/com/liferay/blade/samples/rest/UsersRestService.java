/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.blade.samples.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SortedArrayList;

/**
 * @author Liferay
 */
@ApplicationPath("/blade.users")
@Component(
		immediate = true, 
		property = { "jaxrs.application=true" }, 
		service = Application.class
)
public class UsersRestService extends Application {

	private static final String SECRET_KEY = "liferay.devhacks.secret";

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton((Object) this);
	}

	@GET
	@Path("/list")
	@Produces("text/plain")
	public Response getUsers(@QueryParam("name") String name, @QueryParam("note") String note,
			@QueryParam("signature") String signature) {

		Map<String, String> args = new HashMap<String, String>() {
			private static final long serialVersionUID = -7259412140530971511L;

			{
				put("name", name);
				put("note", note);
			}
		};

		if (!isValidSignature(args, signature)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
		} else {

			StringBuilder result = new StringBuilder();

			result.append("Ola " + name + ", voce disse: " + note);

			for (User user : _userLocalService.getUsers(-1, -1)) {
				result.append(user.getFullName()).append(",\n");
			}

			return Response.status(Response.Status.OK).entity(result.toString()).build();
		}
	}

	// echo -n 'Algo dificil de advinharname=Andrenote=Hello'|openssl dgst -sha256
	private static boolean isValidSignature(Map<String, String> args, String signature) {

		String secret = PropsUtil.get(SECRET_KEY);

		List<String> sortedArgs = new SortedArrayList<String>();
		sortedArgs.addAll(args.keySet());

		String preSig = secret;
		for (String paramName : sortedArgs) {
			preSig += (paramName + "=" + args.get(paramName));
		}

		String shaSig = DigesterUtil.digestHex(Digester.SHA_256, preSig);

		return shaSig.equals(signature);
	}

	@Reference
	private volatile UserLocalService _userLocalService;

}