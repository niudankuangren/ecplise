package test;

import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CreateJwtTest {
	
	public static void main(String[] args) {
		
		
		//为了方便测试，我们将过期时间设置为1分钟
		long now = System.currentTimeMillis();//当前时间
		long exp = now + 1000*60;//过期时间为1分钟
		
		
		JwtBuilder builder= Jwts.builder()
				.setId("888")//载荷
				.setSubject("小白")//载荷
				.setIssuedAt(new Date()) //用于设置签发时间
				.signWith(SignatureAlgorithm.HS256,"itcast")//用于设置签名秘钥
				.setExpiration(new Date(exp))
				
				
				//自定义claims
				.claim("roles","admin")
				.claim("logo","logo.png");
				
				System.out.println( builder.compact() );
	}

}
