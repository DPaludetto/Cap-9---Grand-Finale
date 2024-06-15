package br.com.palutec.controller;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.palutec.core.util.UtilCollection;
import br.com.palutec.model.SystemUser;
import br.com.palutec.service.SystemUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	private static final String SECRET_KEY = "x2HSPMXRAc49sPzz2GKAVKcndFZNeYj8pXjYudD6Th3QMpNafNynP7bbguBBERuyrJX6249gVDpZ6ktfkbt6SRsTFGYFrFvuN5zLHAPjLGCQkj9Pn5ZS6qV9mtAqYn8W";
	
	
	@Autowired
	private SystemUserService userService; 
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody MultiValueMap<String, String> form) {
		Map<String, Object> userInfo = new HashMap<>();
		
		SystemUser user = userService.getUserByAuthentication((String)UtilCollection.getFirst(form.get("username")), (String)UtilCollection.getFirst(form.get("password")));

		Map<String, Object> response = new HashMap<>();
		String token = createJWT(user.getId(), "palutec.com.br", user.getName(), 60*60*1000, userInfo);
		response.put("access_token", token);
		response.put("refresh_token", token);
		response.put("token_type", "bearer");
		response.put("scope", "READ WRITE");
		response.put("credential_expired", false);
		
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(response);
	}
	
	@PostMapping(value = "/get-user-info")
	public ResponseEntity<Object> getUser(@RequestBody Map<String, String> form) {
		SystemUser user = userService.getUserByAuthentication((String)UtilCollection.getFirst(form.get("username")), (String)UtilCollection.getFirst(form.get("password")));
		user.setPassword(null);
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(user);
	} 
	
	private ResponseEntity getError(int httpCode, String message){
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("errorCode", httpCode);
		return ResponseEntity.status(HttpStatusCode.valueOf(httpCode)).body(response);
		
	}
	
	public static String createJWT(String id, String issuer, String subject, long ttlMillis, Map<String, Object> userInfoMap) {
		
		if(userInfoMap == null) {
			userInfoMap = new HashMap<>();
		}
		  
		Instant now = Instant.now();
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		String jwtToken = Jwts.builder()
		        .setClaims(userInfoMap)
		        .setSubject(subject)
		        .setId(id)
		        .setIssuer(issuer)
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
		        .signWith(signingKey)
		        .compact();
		
		return jwtToken;
	}	
	
//	public static void main(String[] args) {
//		Map<String, Object> userInfoMap = new HashMap<String, Object>();
//		userInfoMap.put("info", new String[]{"informação qualqer", "xxx"});
//		userInfoMap.put("info2", "informação qualqe3r");
//		System.out.println(createJWT("userid-1234", "martelab.com.br", "Diego Amaral", 60*60*1000, userInfoMap));
//	
//	}
}
