package com.rx.security.utili;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
@Component
@Slf4j
public class JwtUtil {

    @Value("${app.secret}")
    private String SECRET;

    private static final long VALIDITY = 30*60*60*1000;//30 hours validity
    private final Map<String,Object> claims = new HashMap<>();

    public String generateToken(UserDetails user){
        log.warn("UserName -> {}",user.getUsername());

        return Jwts.builder().setClaims(claims).
                setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+VALIDITY))
                .signWith(SignatureAlgorithm.HS512,this.SECRET)
                .compact();
    }

    private <T> T extractClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(this.SECRET)
                .parseClaimsJws(token).getBody();
    }

    public String getUserName(String token){
        return extractClaimFromToken(token,Claims::getSubject);
    }

    public Date getExpiration(String token){
        return extractClaimFromToken(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }


    public boolean validate(String token, UserDetails user){
        final String userName = user.getUsername();
        return userName.equals(getUserName(token)) && !isTokenExpired(token);

    }
}
