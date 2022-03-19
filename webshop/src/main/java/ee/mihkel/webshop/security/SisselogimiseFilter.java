package ee.mihkel.webshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Log4j2
public class SisselogimiseFilter extends BasicAuthenticationFilter {
            // JwtAuthorizationFilter
    private String secret;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public SisselogimiseFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // tokeni kontroll --->
    // token oleks olemas
    // token algaks "Bearer " algusega
    // SIIS kontrollime TOKENIT - kas see on meie oma ja kas see on valiidne
    // KUI ON, siis anname sisselogimise õigused
    // VÕIMALIK ON TEHA ka kõiki ülejäänud päringuid
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        log.info("Hakkan tokenit kontrollima: " + token);
        if (token != null && token.startsWith("Bearer ")) {
            log.info("Token sobib");

            log.info(secret);
            token = token.replace("Bearer ","");

            // Claims == tokeni mudel
            // id, expiration, subject, jnejne
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            log.info(email);


            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    null);

            SecurityContextHolder.getContext().setAuthentication(auth);

            // localhost:8080/payment <-- isikukood + tooted
            // localhost:8080/payment --- token - email
            // Controllerisse
            // SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        }

        super.doFilterInternal(request, response, chain);
    }
}
