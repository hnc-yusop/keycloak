package org.keycloak.social.malang;

import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper;

public class MalangUserAttributeMapper extends AbstractJsonUserAttributeMapper {

        private static final String[] cp = new String[] { MalangIdentityProviderFactory.PROVIDER_ID };

        @Override
        public String[] getCompatibleProviders() {
                return cp;
        }

        @Override
        public String getId() {
                return "malang-user-attribute-mapper";
        }
}
