package com.prototype.networkManager.security;

import com.prototype.networkManager.neo4j.domain.User;
import com.prototype.networkManager.neo4j.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${spring.ldap.baseDn}")
    String baseDn;

    @Autowired
    LdapTemplate ldapTemplate;

    @Autowired
    UserRepository userRepository;

    private boolean shouldAuthenticateLdap(String username, String password) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uid", username));
        if (username.equals("4Giera")) { //TODO only for development time
            return ldapTemplate.authenticate("cn=" + username + "," + "ou=2014,o=fis", filter.encode(), password);
        }
        return ldapTemplate.authenticate("cn=" + username + "," + baseDn, filter.encode(), password);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (shouldAuthenticateLdap(name, password)) {

            Optional<User> user = userRepository.findUserByName(name);
            System.out.println(user.get());
            if (user.isPresent()) {
                ArrayList<GrantedAuthority> roles = new ArrayList<>();
                for (String role : user.get().getRoles()) {
                    roles.add(new SimpleGrantedAuthority(role));
                }
                return new UsernamePasswordAuthenticationToken(name, password, roles);
            } else {
                userRepository.save(new User(name, Collections.singletonList("VIEWER")));
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}