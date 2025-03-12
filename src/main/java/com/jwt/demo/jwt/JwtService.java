package com.jwt.demo.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService
{

    public static final String SECRET_KEY ="HomLB/DrAXI5CIV6KrGegB6gHJA8+SRDLF4enHQ5r2I=";

    public String generateToken(UserDetails userDetails){
       return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(), SignatureAlgorithm.ES256)
               .compact();

    }

    public <T> T parseToken(String token, Function<Claims, T> claimsResolver){

        return  claimsResolver.apply(Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody());

    }

    public String getUserNameFromToken(String token){
            return  parseToken(token, Claims::getSubject);
    }
    public boolean isTokenExpired(String token){
        Date date= parseToken(token,Claims::getExpiration);
        return  date.before(new Date());
    }
    public Key getKey(){
      byte[] bytes= Decoders.BASE64.decode(SECRET_KEY);
     return Keys.hmacShaKeyFor(bytes);

    }
}
