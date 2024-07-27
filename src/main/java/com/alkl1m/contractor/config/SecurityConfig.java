package com.alkl1m.contractor.config;

import com.alkl1m.contractor.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("USER", "CREDIT_USER", "OVERDRAFT_USER", "DEAL_SUPERUSER", "CONTRACTOR_RUS", "CONTRACTOR_SUPERUSER", "SUPERUSER")
                                .requestMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("CONTRACTOR_SUPERUSER", "SUPERUSER")
                                .requestMatchers(HttpMethod.PATCH, "/**").hasAnyAuthority("CONTRACTOR_SUPERUSER", "SUPERUSER")
                                .requestMatchers(HttpMethod.POST, "/contractor/search").hasAnyAuthority("CONTRACTOR_RUS", "DEAL_SUPERUSER", "SUPERUSER")
                                .requestMatchers(HttpMethod.POST, "/contractor/crud/search").hasAnyAuthority("CONTRACTOR_RUS", "DEAL_SUPERUSER", "SUPERUSER")
                                .requestMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("CONTRACTOR_SUPERUSER", "SUPERUSER")
                                .anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
