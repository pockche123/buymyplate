package org.example.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http, Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
//        http.oauth2ResourceServer(resourceServer ->
//                resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter))
//        );
//
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ).csrf(csrf ->
//                csrf.disable()
//        );
//
//        http.authorizeHttpRequests(requests ->
//                requests
//                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("CUSTOMER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/**").hasAnyRole("CUSTOMER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
//                            .requestMatchers(HttpMethod.GET, "/api/user/info").authenticated()
//                            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                        .anyRequest().authenticated()
//        );
//
//        return http.build();
//    }
//
//    @Bean
//    JwtAuthenticationConverter authenticationConverter(
//            Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt ->
//                authoritiesConverter.convert(jwt.getClaims())
//        );
//        return converter;
//    }
//
//    @Bean
//    AuthoritiesConverter realmRolesAuthoritiesConverter() {
//        return claims -> {
//            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
//            if (resourceAccess == null) return Collections.emptyList();
//
//            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("buymyplate-rest-api");
//            if (clientAccess == null) return Collections.emptyList();
//
//            List<String> roles = (List<String>) clientAccess.get("roles");
//            if (roles == null) return Collections.emptyList();
//
//            return roles.stream()
//                    .map(role -> "ROLE_" + role) // Add prefix and standardize case
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        };
//    }
//}
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest().authenticated();
//        http.oauth2ResourceServer()
//                .jwt();
//
//        http.sessionManagement()
//                .sessionCreationPolicy(STATELESS);
//        return http.build();
//    }
//}





@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf ->
                        csrf.disable()
                )
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/v1/vehiclePlates").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/v1/vehiclePlates/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/v1/vehiclePlates/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

