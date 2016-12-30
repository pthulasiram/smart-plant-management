package com.archius.cosmos.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @author Thulasiram Ponnam
 *
 */
//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*http
        .authorizeRequests()
            .antMatchers("/resources/**", "/registration").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .permitAll();*/
		http.authorizeRequests().requestMatchers(CorsUtils::isCorsRequest).permitAll().anyRequest().authenticated()
				.and().httpBasic().and().addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//	}

}