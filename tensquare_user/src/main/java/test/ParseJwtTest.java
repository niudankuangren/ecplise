package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJwtTest {
	public static void main(String[] args) {
		String token=""
+ "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NDQ1OTI4MDIsImV4cCI6MTU0NDU5Mjg2Mn0.ytH2V7uTvE6lZlDq0wHyerh7gDXaLVEc6qtrRm0l-Bk";
		Claims claims =Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
		System.out.println("id:"+claims.getId());
		System.out.println("subject:"+claims.getSubject());
		System.out.println("IssuedAt:"+claims.getIssuedAt());
		System.out.println("roles:"+claims.get("roles"));
		System.out.println("logo:"+claims.get("logo"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
		System.out.println("签发时间:"+sdf.format(claims.getIssuedAt()));
		System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
		System.out.println("当前时间:"+sdf.format(new Date()) );
		}
	
}
