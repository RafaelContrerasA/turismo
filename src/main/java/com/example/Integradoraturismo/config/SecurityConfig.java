package com.example.Integradoraturismo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    
    
    @Autowired
	AuthenticationSuccessHandler successHandler;
    
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


//         http.csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(requests -> {
//                     requests.requestMatchers("/productos/**").authenticated();
//                     requests.anyRequest().permitAll();})
//                 .formLogin(login -> login.loginPage("/login").successHandler(successHandler)).csrf(csrf -> csrf.disable())
//                 .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login")).oauth2Login(login -> login.loginPage("/login").successHandler(successHandler));
// return http.build();


        http
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/productos/**").authenticated()  // Solo autenticados pueden acceder a /productos
                                .anyRequest().permitAll()  // Permitir el acceso al resto
                )
                .oauth2Login(login -> login.successHandler(successHandler));  // Habilitar autenticación con OAuth2

    return http.build();





                
    }

    // @Bean
    // public AuthenticationSuccessHandler authenticationSuccessHandler() {
    //     return new CustomAuthenticationSuccessHandler();
    // }

    
}
