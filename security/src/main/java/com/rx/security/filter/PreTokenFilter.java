package com.rx.security.filter;

import com.rx.security.utili.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class PreTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil util;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userName = null;
        String token = null;
        if(authToken != null && authToken.startsWith("Bearer ")){
           token = authToken.substring(7);
           try {
               userName = util.getUserName(token);
           }catch (Exception ex){
               ex.printStackTrace();
           }

           if(userName !=null && SecurityContextHolder.getContext().getAuthentication() == null){
               UserDetails user = userDetailsService.loadUserByUsername(userName);
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                     user,null,user.getAuthorities()
               );
               authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                       .buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }else{
               log.error("Invalid Token :{}",token);
           }
        }
        filterChain.doFilter(request,response);
    }
}
