package com.ms.ecommerce.security;

import com.ms.ecommerce.model.UserModel;
import com.ms.ecommerce.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserModelService userModelService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String incomePassword = authentication.getCredentials().toString();

        UserModel userFound = userModelService.getByLogin(login);

        if (userFound == null) {
            throw new UsernameNotFoundException("Login or password are incorrect");
        }

        String encryptedPassword = userFound.getPassword();

        boolean matchesPassword = encoder.matches(incomePassword, encryptedPassword);

        if(matchesPassword) {
            return new CustomAuthentication(userFound);
        }
        throw new UsernameNotFoundException("Login or password are incorrect");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
