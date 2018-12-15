package util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtil {
	
	@Value("${jwt.key}")
	private String key ;
	@Value("${jwt.ttl}")
	private long ttl;//一个小时
	
	
	
	
	/**
	* 生成JWT
	*
	* @param id
	* @param subject
	* @return
	*/
	public String createJWT(String id, String subject, String roles) {
			
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder()
				.setId(id)
				.setSubject(subject)
				.setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS256, key)
				.claim("roles",roles);
		
		if (ttl > 0) {
				builder.setExpiration( new Date( nowMillis + ttl));
		}

		return builder.compact();
	
	}
	
	
	/**
	* 解析JWT
	* @param jwtStr
	* @return
	*/
	public Claims parseJWT(String jwtStr){
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwtStr)
				.getBody();
	}
	
	
}
