package mckayGroupID.mvnBookingProject.service;

import java.security.Key;
import java.util.Base64;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mckayGroupID.mvnBookingProject.entity.Credentials;
import mckayGroupID.mvnBookingProject.entity.User;
import mckayGroupID.mvnBookingProject.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepo;
	
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public User register(Credentials credentials) throws AuthenticationException {
		User user = new User();
		user.setUsername(credentials.getUsername());
		user.setHash(BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()));
		try {
			userRepo.save(user);
			return user;
		} catch (DataIntegrityViolationException e){
			throw new AuthenticationException("Username not avalible");
		}
	}
	
	public User login(Credentials credentials) throws AuthenticationException {
		User foundUser = userRepo.findByUsername(credentials.getUsername());
			if (foundUser != null && BCrypt.checkpw(credentials.getPassword(), foundUser.getHash())) {
				foundUser.setJwt(createJwt(foundUser));
				return foundUser;
			}
			
			throw new AuthenticationException("Incorrect username or password.");
	
	}
	
	public boolean isValidJwt(HttpServletRequest request) {
		
		return Jwts.parser().setSigningKey(key).parseClaimsJws(extractJwt(request))
				.getBody().getSubject().equals("Booking");
	}

	public boolean isAdmin(HttpServletRequest request) {
		String role = (String) Jwts.parser().setSigningKey(key).parseClaimsJws(extractJwt(request))
				.getBody().get("role");
				
		return role != null &&	role.equals("Admin");
	}
	
	private String extractJwt(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer ")) {
			return "";
		}
		return header.substring(7);
		
	}
	
	private String createJwt(User user) {
        
        return Jwts.builder().claim("role", user.getRole())
        		.setSubject("Booking").signWith(key).compact();
	}
	
} 
