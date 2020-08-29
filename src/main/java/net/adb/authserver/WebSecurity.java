package net.adb.authserver;

import net.adb.authlibs.filter.JWTAuthorizationFilter;
import net.adb.authserver.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@Component
class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Value("${application.jwt.secrer}")
	private String SECRET;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
					.and()
				.csrf()
					.disable()
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/signup","/login").permitAll()
				.anyRequest().authenticated()
					.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(),SECRET))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(),SECRET))
				//this disables session creation on Spring Security
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}
}
