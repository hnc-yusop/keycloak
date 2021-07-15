package org.keycloak.social.malang;

import com.fasterxml.jackson.databind.JsonNode;

import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider;
import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.KeycloakSession;
import org.keycloak.saml.common.util.StringUtil;

public class MalangIdentityProvider extends AbstractOAuth2IdentityProvider<MalangIdentityProviderConfig> implements SocialIdentityProvider<MalangIdentityProviderConfig> {

        public static final String AUTH_URL = "https://accounts.malangmalang.com/oauth2/authorize";
        public static final String TOKEN_URL = "https://api.malangmalang.com/accounts/oauth2/token";
        public static final String PROFILE_URL = "https://api.malangmalang.com/accounts/oauth2/me";
        public static final String DEFAULT_SCOPE = "email";

        protected static final String PROFILE_URL_FIELDS_SEPARATOR = ",";

        public MalangIdentityProvider(KeycloakSession session, MalangIdentityProviderConfig config) {
                super(session, config);
                config.setAuthorizationUrl(AUTH_URL);
                config.setTokenUrl(TOKEN_URL);
                config.setUserInfoUrl(PROFILE_URL);
        }

        protected BrokeredIdentityContext doGetFederatedIdentity(String accessToken) {

                try {
                        final String fetchedFields = getConfig().getFetchedFields();
                        final String url = StringUtil.isNotNull(fetchedFields)
                                        ? String.join(PROFILE_URL_FIELDS_SEPARATOR, PROFILE_URL, fetchedFields)
                                        : PROFILE_URL;

                        JsonNode profile = SimpleHttp.doGet(url, session).header("Authorization", "Bearer " + accessToken).asJson();
                        return extractIdentityFromProfile(null, profile);
                } catch (Exception e) {
                        throw new IdentityBrokerException("Could not obtain user profile from malang.", e);
                }
        }

        @Override
        protected boolean supportsExternalExchange() {
                return true;
        }

        @Override
        protected String getProfileEndpointForValidation(EventBuilder event) {
                return PROFILE_URL;
        }

        @Override
        protected BrokeredIdentityContext extractIdentityFromProfile(EventBuilder event, JsonNode profile) {
               String identity = profile.get("identify").asText();
               String email = profile.get("email").asText();
               
               BrokeredIdentityContext user = new BrokeredIdentityContext(identity);

               user.setIdpConfig(getConfig());
               user.setUsername(email);
               user.setEmail(email);
               user.setIdp(this);

               AbstractJsonUserAttributeMapper.storeUserProfileForMapper(user, profile, getConfig().getAlias());

               return user;
        }

        @Override
        protected String getDefaultScopes() {
                return DEFAULT_SCOPE;
        }
}
