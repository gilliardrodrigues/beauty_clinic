package com.ubi.beautyClinic.config;

import com.ubi.beautyClinic.adapters.outbound.PatientRepositoryAdapter;
import com.ubi.beautyClinic.adapters.outbound.ProfessionalRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ProfessionalRepositoryAdapter professionalRepositoryAdapter;

    @Autowired
    private PatientRepositoryAdapter patientRepositoryAdapter;

    @Bean
    public DaoAuthenticationProvider professionalAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(professionalRepositoryAdapter);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider patientAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(patientRepositoryAdapter);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(professionalAuthenticationProvider());
        providers.add(patientAuthenticationProvider());

        return new CustomAuthenticationManager(providers);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // don't authenticate this particular request
                .authorizeHttpRequests().requestMatchers("/js/**", "/img/**", "/css/**",
                "/swagger-ui/**", "/v3/api-docs/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET,"/professionals/filter/by-service/**").hasAnyAuthority("PATIENT", "PROFESSIONAL")
                .requestMatchers(HttpMethod.POST, "/professionals/authenticate", "/professionals/register")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/patients/authenticate", "/patients/register")
                .permitAll()
                .requestMatchers(HttpMethod.DELETE, "/professionals/**").hasAuthority("PROFESSIONAL")
                .requestMatchers(HttpMethod.GET, "/professionals/**").hasAuthority("PROFESSIONAL")
                .requestMatchers(HttpMethod.PUT, "/professionals/**").hasAuthority("PROFESSIONAL")
                .requestMatchers(HttpMethod.DELETE, "/patients/**").hasAuthority("PATIENT")
                .requestMatchers(HttpMethod.GET, "/patients/**").hasAuthority("PATIENT")
                .requestMatchers(HttpMethod.PUT, "/patients/**").hasAuthority("PATIENT")
                .requestMatchers("/patients/search/**").hasAuthority("PATIENT")
                .requestMatchers(HttpMethod.POST, "/appointments/**").hasAuthority("PATIENT")
                .requestMatchers(HttpMethod.DELETE, "/appointments/**").hasAnyAuthority("PATIENT", "PROFESSIONAL")
                .requestMatchers(HttpMethod.GET, "/appointments/**").hasAnyAuthority("PATIENT", "PROFESSIONAL")
                .requestMatchers(HttpMethod.PUT, "/appointments/**").hasAuthority("PROFESSIONAL")
                // all other requests need to be authenticated
                .anyRequest().authenticated();
        httpSecurity.authenticationManager(authenticationManager());

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
