package telran.courses.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CoursesSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Bean //the returned object is bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Value("${app.security.enable: true}")
	private boolean securityEnable;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.cors().and().csrf().disable();
		http.authorizeHttpRequests().antMatchers("/login").permitAll();
		//http.authorizeHttpRequests().antMatchers(HttpMethod.GET).permitAll(); // all "gets" are permitted
		//http.authorizeHttpRequests().anyRequest().authenticated(); //all other operations need to be authenticated
		if(securityEnable) {
			http.authorizeHttpRequests().antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN"); 
			http.authorizeHttpRequests().anyRequest().hasRole("ADMIN");
		}else {
			http.authorizeHttpRequests().anyRequest().permitAll();
		}
	}
}
