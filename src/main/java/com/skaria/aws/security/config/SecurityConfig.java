package com.skaria.aws.security.config;


import com.skaria.aws.security.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security configuration.
 */
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
@EnableConfigurationProperties({JwtConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthorizationService tokenAuthorizationService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/auth").permitAll()
                .antMatchers("/api/v1/signup").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/login**", "/signin/**",
                        "/authenticate/**", "/connect/**", "/social/authenticate").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/rest/users/login").permitAll()
                .antMatchers("/rest/users/authorize").permitAll()
                .antMatchers("/**/*.css",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.json",
                        "/**/*.bmp",
                        "/**/*.jpeg",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.ttf",
                        "/**/*.eot",
                        "/**/*.svg",
                        "/**/*.woff",
                        "/**/*.woff2").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                /*.addFilterBefore(
                        new AuthenticationTokenFilter(tokenAuthorizationService),
                        UsernamePasswordAuthenticationFilter.class)*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
}
