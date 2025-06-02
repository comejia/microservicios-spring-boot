package com.comejia.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> { 
                auth.requestMatchers("/authorized", "/logout")
                        .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/items/api/v1/items", "/api/products/api/v1/products", "/api/users/api/v1/users")
                        .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/items/api/v1/items/{id}", "/api/products/api/v1/products/{id}", "/api/users/api/v1/users/{id}")
                        .hasAnyRole("ADMIN", "USER")
                    // .pathMatchers(HttpMethod.POST, "/api/items", "/api/products", "/api/users").hasRole("ADMIN")
                    // .pathMatchers(HttpMethod.PUT, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}").hasRole("ADMIN")
                    // .pathMatchers(HttpMethod.DELETE, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}").hasRole("ADMIN")
                    .requestMatchers("/api/items/api/v1/items/**", "/api/products/api/v1/products/**", "/api/users/api/v1/users/**")
                        .hasRole("ADMIN")
                    .anyRequest().authenticated();
            })
            .cors(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
            .oauth2Client(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(
                new Converter<Jwt, AbstractAuthenticationToken>() {
                    @Override
                    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
                        Collection<String> roles = jwt.getClaimAsStringList("roles");
                        Collection<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                        return new JwtAuthenticationToken(jwt, authorities);
                    }
                })
            ))
            .build();
    }

}
