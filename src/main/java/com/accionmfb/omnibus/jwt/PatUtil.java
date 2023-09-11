package com.accionmfb.omnibus.jwt;

import com.accionmfb.omnibus.config.OmniBusProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = OmniBusProperties.class)
public class PatUtil {

    private final OmniBusProperties omniBusProperties;

    public String createPat(String mobileNumber){
        return Jwts.builder()
                .claim("mobileNumber", mobileNumber)
                .setSubject(mobileNumber)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(getJWTSigningKey())
                .compact();
    }

    public String getCustomerMobileFromPAT(String token){
        Jws<Claims> claimsJws = getClaimsFromToken(cleanToken(token));
        return (String) claimsJws.getBody().get("mobileNumber");
    }

    public Jws<Claims> getClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getJWTSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getJWTSigningKey(){
        String secret = omniBusProperties.getPatSigningKey();

        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    private String cleanToken(String authToken){
        return authToken.startsWith("Bearer ") ? authToken.replace("Bearer ", "").trim() : authToken.trim();
    }
}
