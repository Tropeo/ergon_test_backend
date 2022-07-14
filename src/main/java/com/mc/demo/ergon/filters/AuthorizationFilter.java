package com.mc.demo.ergon.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.repositories.UserRepository;
import com.mc.demo.ergon.services.security.JwtProvider;
import com.mc.demo.ergon.utils.JWT_CONSTS;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(JWT_CONSTS.AUTH_HEADER);

        if (header != null && header.startsWith(JWT_CONSTS.AUTH_HEADER_VALUE_PREFIX)) {
            final DecodedJWT decoded = JwtProvider.verifyJwt(header.replace(JWT_CONSTS.AUTH_HEADER_VALUE_PREFIX, "").trim());
            
            //---NOTA---
            //Inserire qui tutti i claim e authorities

            ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("generic_user"));
            final String username = decoded.getClaim("user").asString();
            
            //-----------

            //NOTE: Questa azione per quanto inefficente al momento rimarrà in quanto l'autenticazione è a scopo dimostrativo
            //In un ambiente reale è necessario estendere i dati nel token e/o implementare un sistema di caching rapido
            this.userRepository.findByUsername(username).ifPresent(user -> {
                UserDto userData = new UserDto(
                    user.getId(), 
                    user.getUsername(), 
                    "", 
                    user.getFirstName(),
                    user.getLastName(), 
                    user.getCreationDate()
                );

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userData, null, authorities));
            });
        }

        chain.doFilter(request, response);
    }
}