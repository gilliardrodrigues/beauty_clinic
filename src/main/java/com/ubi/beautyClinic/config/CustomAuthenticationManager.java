package com.ubi.beautyClinic.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    public CustomAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationException lastException = null;

        for (AuthenticationProvider provider : providers) {
            try {
                if (provider.supports(authentication.getClass())) {
                    return provider.authenticate(authentication);
                }
            } catch (AuthenticationException e) {
                // Captura a exceção para posterior verificação
                lastException = e;
            }
        }

        if (lastException != null) {
            throw lastException;
        }

        throw new ProviderNotFoundException("No authentication provider found for the given authentication.");
    }
}
