package com.jb.GrouponSpring.Util;

import com.jb.GrouponSpring.User.UserDetails;
import com.jb.GrouponSpring.loginManager.ClientType;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.UserType;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTutil {
    //type of encryption
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    //creating our private key...
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey),this.signatureAlgorithm);

    //generate claims
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("clientType", userDetails.getClientType());
        claims.put("userId",userDetails.getUserId());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created : "+myToken);
        return myToken;
    }

    //create our token
    private String createToken(Map<String,Object> claims, String subject){
        //get our current time
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    //extract all our data
    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractUserEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpirationDate(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        try{
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException err){
            return true;
        }
    }

    public boolean validateToken(String token){
        //final String username = extractUserEmail(token);
        //return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
        return !isTokenExpired(token); // חסר validate signutare
    }
}