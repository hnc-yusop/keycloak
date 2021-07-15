package org.keycloak.social.malang;

import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

public class MalangIdentityProviderFactory extends AbstractIdentityProviderFactory<MalangIdentityProvider> implements SocialIdentityProviderFactory<MalangIdentityProvider> {

    public static final String PROVIDER_ID = "malang";

    @Override
    public String getName() {
        return "MalangMalang";
    }

    @Override
    public MalangIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new MalangIdentityProvider(session, new MalangIdentityProviderConfig(model));
    }

    @Override
    public OAuth2IdentityProviderConfig createConfig() {
        return new OAuth2IdentityProviderConfig();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
