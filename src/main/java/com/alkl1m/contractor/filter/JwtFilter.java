package com.alkl1m.contractor.filter;

import com.alkl1m.contractor.service.impl.UserDetailsImpl;
import com.alkl1m.contractor.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = WebUtils.getCookie(request, "jwt");

        if (cookie != null) {
            String jwt = cookie.getValue();
            try {
                Claims claims = jwtUtils.parseJwt(jwt);

                if (jwtUtils.isTokenExpired(claims)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT токен истек");
                    return;
                }

                UserDetailsImpl userDetails = UserDetailsImpl.build(
                        String.valueOf(jwtUtils.getIdFromClaims(claims)),
                        jwtUtils.getLoginFromClaims(claims),
                        jwtUtils.getAuthoritiesFromClaims(claims)
                );

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("Не получается выполнить аутентификацию юзеру: {}", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}