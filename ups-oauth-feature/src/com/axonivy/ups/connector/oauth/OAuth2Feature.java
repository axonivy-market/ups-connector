package com.axonivy.ups.connector.oauth;

import javax.ws.rs.Priorities;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ivyteam.ivy.rest.client.FeatureConfig;
import ch.ivyteam.ivy.rest.client.authentication.HttpBasicAuthenticationFeature;
import ch.ivyteam.ivy.rest.client.oauth2.OAuth2BearerFilter;
import ch.ivyteam.ivy.rest.client.oauth2.OAuth2TokenRequester.AuthContext;
import ch.ivyteam.ivy.rest.client.oauth2.uri.OAuth2UriProperty;

/**
 * UPS AUTH flow implementation.
 *
 * <ul>
 * <li>Requires a registered application:
 * https://developer.ups.com/get-started?loc=en_US#tabs_1_tabPane_4</li>
 * </ul>
 *
 * @since 9.2
 */
public class OAuth2Feature implements Feature {

	public static interface Default {
		String AUTH_URI = "https://onlinetools.ups.com/security/v1/oauth";
	}

	public static interface Property {
		String APP_ID = "AUTH.appId";
		String CLIENT_SECRET = "AUTH.secretKey";
		String AUTH_BASE_URI = "AUTH.baseUri";
	}

	@Override
	public boolean configure(FeatureContext context) {
		var config = new FeatureConfig(context.getConfiguration(), OAuth2Feature.class);
		var oauth2 = new OAuth2BearerFilter(OAuth2Feature::requestToken,
				new OAuth2UriProperty(config, Property.AUTH_BASE_URI, Default.AUTH_URI));
		context.register(oauth2, Priorities.AUTHORIZATION);
		return true;
	}

	private static Response requestToken(AuthContext ctxt) {
		FeatureConfig config = ctxt.config;
		Form form = new Form("grant_type", "client_credentials");
		var response = ctxt.target
				.register(HttpBasicAuthenticationFeature.basic(config.readMandatory(Property.APP_ID),
						config.readMandatory(Property.CLIENT_SECRET)))
				.request().accept(MediaType.WILDCARD).post(Entity.form(form));

		return response;
	}
}
