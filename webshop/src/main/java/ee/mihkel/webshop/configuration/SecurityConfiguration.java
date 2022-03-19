package ee.mihkel.webshop.configuration;

import ee.mihkel.webshop.security.SisselogimiseFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration faili
//   class     extends WebSecurityConfigurerAdapter

// configure() <---- ligipääseda
// HttpSecurity <---
// http.   <--- niimoodi kirjutan üle vana loogikat ENDA loogikaga
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    String secret;

    // KÕIK PÄRINGUD käivad läbi configure
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // teen filtri valmis, millele sätestan omad tingimused kuidas sisselogituna sisse saada
        SisselogimiseFilter jwtFilter = new SisselogimiseFilter(authenticationManager());
        jwtFilter.setSecret(secret);

        http
           .cors().and().headers().xssProtection().disable().and()// võtan security protectionid maha (koostöös)
           .csrf().disable() // xss  cross-site scripting   --   csrf   Cross-Site Request Forgery  protectionid maha
           .addFilter(jwtFilter) // SISSELOGIMISE KONTROLL - filtri teen valmis üleval "new" abil
           .authorizeRequests() // autoriseeri päringuid
           .antMatchers(HttpMethod.GET, "/products").permitAll() // luban KÕIGILE teha GET päringuid localhost:8080/products
           .antMatchers("/login").permitAll() // luban KÕIGILE teha päringuid localhost:8080/login
           .antMatchers("/signup").permitAll() // luban KÕIGILE teha päringuid localhost:8080/signup
           .anyRequest().authenticated();// kõik ülejäänud päringud on kättesaadavad kui autentitud
                            // autentitud on läbi filtri --- .addFilter(jwtFilter) abil
                            // jwt - JSON Web Token --- Bearer daomwa123nkn312ib412ininj123
    }
}
