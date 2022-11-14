package com.rx.security.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.rx.security.model.AuthRequest;
import com.rx.security.model.User;
import com.rx.security.repository.UserRepository;
import com.rx.security.service.UserService;
import com.rx.security.utili.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {
	
	//org.slf4j.Logger logger= LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil util;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository repository;
    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) throws Exception {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
    	log.info("User Details Start : {}",user);
        try{
            userService.addNewUser(user);
        }catch(Exception ex){
            throw new SQLException(String.format("User with username %s already present.",user.getEmail()));
        }
        Map<String, String> success = new LinkedHashMap<>();
        success.put("message",String.format("Successfully registered user %s",user.getEmail()));
        log.info("User registration end : {}",success);
        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> doLogin(@Valid @RequestBody AuthRequest request){
    	log.info("Login Request Recived : {}",request);
        Map<String,Object> authResponse = new HashMap<>();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),request.getPassword()
                    )
            );
        }catch(Exception ex) {
            throw new UsernameNotFoundException("The Email or Password entered is invalid. Please try again.");
        }

        //authentication successful
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        List<String> role = new ArrayList<>();
        userDetails.getAuthorities().
                forEach(p->role.add(p.toString()));
        authResponse.put("role",role);
        authResponse.put("token",String.format("Bearer %s",util.generateToken(userDetails)));
        
        log.info("Login Process end, Token : {}",authResponse.get("token"));
        return ResponseEntity.ok(authResponse);
    }


    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/user/admin")
    public boolean checkIfAdmin() {
    	return true; //this method will return true only if the loggedin user is admin
    }
    
   
    @GetMapping(path = "/validate")
    public boolean doValidate(WebRequest request) {
    	String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    	log.info(auth);
    	boolean result = false;
    	if(auth != null && auth.startsWith("Bearer"))
    		result = util.isTokenExpired(auth.substring(7));
    	return result;
    }
    
    @GetMapping(path = "/validate/username")
    public boolean doValidateUsername(@RequestParam(name = "name")String name,WebRequest request) {
    	String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    	log.info(auth);
    	boolean result = false;
    	if(auth != null && auth.startsWith("Bearer"))
    		result = util.getUserName(auth.substring(7)).equalsIgnoreCase(name);
    	return result;
    }
    
    
    @SuppressWarnings("unused")
    @GetMapping("/get/user")
	public User getLoggedInUser(WebRequest request){
        return repository.findByEmail(
        		util.getUserName(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7))
        ).get();
    }
}
