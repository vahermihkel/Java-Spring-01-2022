package ee.mihkel.webshop.security;

import ee.mihkel.webshop.model.request.output.AuthData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokeniEhitaja {

    @Value("${jwt.secret}")
    String secret;

    // token + aegumise aeg
    public AuthData createJwtAuthtoken(String email) {
        AuthData authData = new AuthData();

        Date newDate = DateUtils.addHours(new Date(), 3);
        authData.setExpiration(newDate);

        String token = Jwts.builder() // hakatakse ehitama
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .setIssuer("webshop") // kui ainult see, siis on kõikidel sama token
                .setSubject(email) // kui see ka, siis on erinevatel emailidega sisseloginutel erinev,
                // aga ühel ja samal inimesel kes logib sama emailiga, tal on sama
                .setExpiration(newDate) // igaüks unikaalne
                .compact(); // ehitatakse valmis
        authData.setToken(token);

        return authData;
    }
}
