package com.github.pateluday07.graphqlspringbootdemo.config.security;

import com.github.pateluday07.graphqlspringbootdemo.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(Constants.AUTHORIZATION_HEADER_KEY);

        if (StringUtils.isBlank(authorizationHeader) || !authorizationHeader.startsWith(Constants.JWT_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(7);
        String userName = jwtUtil.extractUsername(jwt);

        if (StringUtils.isNotBlank(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
