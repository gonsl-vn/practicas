package com.viewnext.practicas.P4SeriesYPeliculas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests(auR
        ->auR.requestMatchers("/API/user").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/API/admin").hasRole("ADMIN")
                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpB->httpB.disable())
                .formLogin(formLogin -> formLogin.permitAll());
        return http.build();

    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("contraseña")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("contraseña")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);

    }


}
