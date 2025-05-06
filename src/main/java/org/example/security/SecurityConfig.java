package org.example.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http, Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

//        http.oauth2ResourceServer(resourceServer ->
//                resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter))
//        );

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).csrf(csrf ->
                csrf.disable()
        );

        http.authorizeHttpRequests(requests ->
                requests
//                        .requestMatchers(HttpMethod.GET, "/v1/vehiclePlates/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/v1/user/info").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("CUSTOMER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/v1/transactions").hasAnyRole("CUSTOMER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/**").hasAnyRole("CUSTOMER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(
            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                authoritiesConverter.convert(jwt.getClaims())
        );
        return converter;
    }

    @Bean
    AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> {
            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
            if (resourceAccess == null) return Collections.emptyList();

            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("buymyplate-rest-api");
            if (clientAccess == null) return Collections.emptyList();

            List<String> roles = (List<String>) clientAccess.get("roles");
            if (roles == null) return Collections.emptyList();

            return roles.stream()
                    .map(role -> "ROLE_" + role) // Add prefix and standardize case
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Your frontend URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Important for cookies/auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

