package com.noter.api.security;

import com.noter.api.users.service.UserService;
import static com.noter.api.security.UserPermission.NOTE_CREATE;
import static com.noter.api.security.UserPermission.NOTE_UPDATE;
import static com.noter.api.security.UserPermission.USER_CREATE;
import static com.noter.api.security.UserPermission.USER_READ;
import static com.noter.api.security.UserRole.ADMIN;
import com.noter.api.shared.EndpointPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@Autowired
	public SecurityConfig(final PasswordEncoder passwordEncoder,
						  final UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
			.csrf()
			.disable()
            .authorizeRequests()
				.antMatchers(HttpMethod.DELETE, EndpointPath.NOTES + "/**", EndpointPath.USERS + "/**").hasRole(ADMIN.name())
				.antMatchers(HttpMethod.POST, EndpointPath.NOTES).hasAuthority(NOTE_CREATE.getPermission())
				.antMatchers(HttpMethod.POST, EndpointPath.USERS).hasAuthority(USER_CREATE.getPermission())
				.antMatchers(HttpMethod.PUT, EndpointPath.NOTES).hasAuthority(NOTE_UPDATE.getPermission())
				.antMatchers(HttpMethod.GET, EndpointPath.USERS + "/**").hasAuthority(USER_READ.getPermission())
				.antMatchers(HttpMethod.GET, EndpointPath.NOTES + "/**").permitAll()
			.anyRequest()
			.authenticated()
            .and()
			.formLogin()
				.defaultSuccessUrl(EndpointPath.NOTES, true)
			.and()
			.logout()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login");
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userService);

		return provider;
	}
}
