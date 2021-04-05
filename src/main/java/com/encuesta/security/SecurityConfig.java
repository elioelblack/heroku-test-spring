package com.encuesta.security;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Value("${saphriv.authentication.url}")
	private String authenticationUrl;



	@Value("${saphriv.jwt.token.expiration.in.seconds}")
	private Long tokenExpirationInSeconds;

	@Value("${saphriv.jwt.signing.key.secret}")
	private String tokenSecret;

	private EncuestaUserDetailsService saphrivUserDetailsService;

	public SecurityConfig(EncuestaUserDetailsService saphrivUserDetailsService) {
		System.out.println("Entro constructor...");
		this.saphrivUserDetailsService = saphrivUserDetailsService;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and()
				.csrf().disable().authorizeRequests()				
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").permitAll() 
				.antMatchers(HttpMethod.POST, authenticationUrl).permitAll()
				.antMatchers("/error").permitAll()
				.antMatchers("/preba").permitAll()
				.anyRequest()
				.authenticated().and().addFilterBefore(
						new AuthenticationFilter(authenticationUrl, tokenExpirationInSeconds, tokenSecret, authenticationManager()),  UsernamePasswordAuthenticationFilter.class)
				.addFilter(new AuthorizationFilter(tokenSecret,authenticationManager()))
				;

		httpSecurity
	    .exceptionHandling()
	    .authenticationEntryPoint((request, response, e) ->
	    {
	        response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.getWriter().write(new JSONObject()
	                .put("status",HttpServletResponse.SC_FORBIDDEN)
	                .put("message", "Acceso Denegado")
	                .toString());
	    });

	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(saphrivUserDetailsService).passwordEncoder(passwordEncoder());
	}

	/*@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}*/
	

	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        //config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
