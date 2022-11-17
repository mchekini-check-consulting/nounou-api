package fr.checkconsulting.nounouapi.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigurtion {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/v1/health/").permitAll()
                .antMatchers("/api/v1/nounou/**").authenticated()
                .antMatchers("/api/v1/disponibilites/**").authenticated()
                .and()
                .oauth2ResourceServer().jwt();

        return http.build();
    }

}
