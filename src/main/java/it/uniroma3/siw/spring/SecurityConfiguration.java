

package it.uniroma3.siw.spring;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.spring.service.UserService;

import javax.sql.DataSource;
import static it.uniroma3.siw.spring.model.Credentials.ADMIN_ROLE;
//import static it.uniroma3.siw.spring.model.Credentials.DEFAULT_ROLE;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 inMemoryConfigurer()
	        .withUser("user")
	            .password("{noop}papere")
	            .authorities("ADMIN")
	        .and()
	        .configure(auth);
		 auth.jdbcAuthentication()
         //use the autowired datasource to access the saved credentials
         .dataSource(this.dataSource)
         //retrieve username and role
         .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
         //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
         .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}

	private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>
    inMemoryConfigurer() {
	return new InMemoryUserDetailsManagerConfigurer<>();
}
    	
    @Override
    protected void configure(HttpSecurity http) throws Exception{
    	
    	http
		.authorizeRequests()
		.antMatchers("/","/index","/artista","/artisti","/artista/**","/collezione","/collezione/**","/collezioni","/informazioni","/opera","/opera/**","/opere","/css/**","/img/**").permitAll()
			.antMatchers("/editCollezione", "/editOpera", "InserisciArtista", "InserisciCollezione", "InserisciCuratore", "InserisciOpera", "registerUser", "registrationSuccessful", "RimuoviCollezione", "RimuoviCuratore", "RimuoviOpera").access("hasAuthority('ADMIN')")
			.anyRequest().authenticated()
		.and()
		.formLogin()
		.and()
		.logout().logoutUrl("/logout")
		.logoutSuccessUrl("/index")
			.permitAll();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    	
}
