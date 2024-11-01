package application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()
            .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration configuration) throws Exception{
            return configuration.getAuthenticationManager();
        }
    
    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
