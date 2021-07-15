package org.keycloak.social.malang;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.saml.common.util.StringUtil;

public class MalangIdentityProviderConfig extends OIDCIdentityProviderConfig {

    public MalangIdentityProviderConfig(IdentityProviderModel model) {
        super(model);
    }

    public MalangIdentityProviderConfig() {
    }

    public String getFetchedFields() {
        return Optional.ofNullable(getConfig().get("fetchedFields"))
                .map(fieldsConfig -> fieldsConfig.replaceAll("\\s+",""))
                .orElse("");
    }

    public void setFetchedFields(final String fetchedFields) {
        getConfig().put("fetchedFields", fetchedFields);
    }
}
