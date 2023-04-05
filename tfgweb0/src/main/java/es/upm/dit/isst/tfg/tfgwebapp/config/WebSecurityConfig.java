package es.upm.dit.isst.tfg.tfgwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
        .authorizeRequests()
			.antMatchers("/css/**", "/img/**", "/layouts/**").permitAll()
			.antMatchers("/", "/lista").permitAll()
			.antMatchers("/crear", "/guardar").permitAll()
			.anyRequest().authenticated()
        .and()
            .formLogin()
				.loginPage("/login")
				.permitAll()
		.and()
            .logout()
			.permitAll();
/*
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //NEW
        .and()
        .authorizeRequests()
			.antMatchers("/login").permitAll() // sustituye por formLogin y logout
			.antMatchers("/lista").permitAll()
            .anyRequest().authenticated()
        .and()
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
*/
	}
}