package com.openclassrooms.mddapi.springSecurity;

import java.util.HashMap;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.CustomUserDetails;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;


@Component
public class JwtUtil {
     private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 heures
 @Autowired private UserRepository userRepository;
  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", user.getEmail());
    claims.put("name", user.getUsername());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token, User user) {
    final String email = extractEmail(token);
    return (email.equals(user.getEmail()) && !isTokenExpired(token));
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Date extractExpiration(String token) {
    return extractAllClaims(token).getExpiration();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public User getUserFromAuthent(Authentication auth)
  {
    CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
    User user = userRepository
        .findByEmail(customUserDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return user;
  }
    
}
