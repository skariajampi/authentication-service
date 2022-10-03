package com.skaria.aws.security.filter;


import com.skaria.aws.security.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Spring security filter for authenticating requests by token.
 */
@AllArgsConstructor
public class AuthenticationTokenFilter extends GenericFilterBean {

    private final AuthorizationService authorizationService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        /*final Authentication authentication = authorizationService.authorize(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/

        filterChain.doFilter(request, response);
        SecurityContextHolder.clearContext();
    }
}
