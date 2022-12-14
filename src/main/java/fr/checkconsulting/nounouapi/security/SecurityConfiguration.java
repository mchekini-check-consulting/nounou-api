package fr.checkconsulting.nounouapi.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/health/").permitAll()
                .antMatchers("/api/v1/search/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/api/v1/famille/**").authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }
}

