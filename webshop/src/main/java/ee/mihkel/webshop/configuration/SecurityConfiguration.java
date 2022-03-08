package ee.mihkel.webshop.configuration;

import ee.mihkel.webshop.security.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthorizationFilter jwtFilter = new JwtAuthorizationFilter(authenticationManager());

        http
           .cors().and().headers().xssProtection().disable().and()
           .csrf().disable()
           .addFilter(jwtFilter)
           .authorizeRequests()
           .antMatchers(HttpMethod.GET, "/products").permitAll()
           .antMatchers("/**").permitAll()
           .anyRequest().authenticated();
    }
}
