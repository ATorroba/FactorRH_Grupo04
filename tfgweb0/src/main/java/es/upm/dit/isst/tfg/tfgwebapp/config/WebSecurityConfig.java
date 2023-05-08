package es.upm.dit.isst.tfg.tfgwebapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	/*
	 * 
	 * @Bean
	 * 
	 * 
	 * 
	 * String usersByUsernameQue
	 * y = "select email, password from empleado where username
	 * String authsByUserQuery = "select idEmpleado, idRol from rol where idE
	 * JdbcUserDetailsManager users = new JdbcUserDetailsMa
	 * users.setUsersByUsernameQuery(usersByUsernameQuery);
	 * users.setAuth
	 * 
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/remesas/**").hasAnyRole("HABILITADO")
				.antMatchers("/incidencias_n/**").hasAnyRole("HABILITADO")
				
				.antMatchers("/css/**", "/img/**", "/layouts/**").permitAll()
				.antMatchers("/", "/lista").authenticated()
				.antMatchers("/datos").hasRole("EMPLEADO")

				.antMatchers("/crear", "/guardar").permitAll()
				.anyRequest().authenticated()
				.and()

				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and().exceptionHandling().accessDeniedPage("/403")
				.and()
				.logout()
				.permitAll();
		/*
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 * //NEW
		 * .and()
		 * .authorizeRequests()
		 * .antMatchers("/login").permitAll() // sustituye por formLogin y logout
		 * .antMatchers("/lista").permitAll()
		 * .anyRequest().authenticated()
		 * .and()
		 * .addFilterBefore(jwtAuthorizationFilter,
		 * UsernamePasswordAuthenticationFilter.class);
		 */
	}
}