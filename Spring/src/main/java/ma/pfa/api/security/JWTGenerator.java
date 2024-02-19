package ma.pfa.api.security;

import java.util.Date;

import io.jsonwebtoken.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTGenerator {
	private static final Logger logger = LoggerFactory.getLogger(JWTGenerator.class);

	private final SecretKey jwtSecret;

	@Value("${expiration_delay}")
	private int delaiExpiration;
	public JWTGenerator() {
		this.jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}
	public String generateJwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Map<String, Object> credentials = new HashMap<>();
		credentials.put("roles", userPrincipal.getAuthorities().stream().
				map(GrantedAuthority::getAuthority).
				collect(Collectors.toList()));
		credentials.put("sub", userPrincipal.getUsername());
		return Jwts.builder().setClaims(credentials).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + delaiExpiration))
				.signWith(jwtSecret,SignatureAlgorithm.HS256).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}


}
