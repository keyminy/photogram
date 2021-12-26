package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration  
@EnableWebSecurity //해당파일로 Security 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http); //삭제 시 기존 시큐리티가 가지고 있는 기능이 다 비활성화가 된다.
		
		http.csrf().disable(); //csrf 토큰 비활성화됨
		
		//인증이 되지 않은 사용자를 로그인 페이지로 가게하기
		//.authenticated() : 앞에있는 주소로 갈땐 인증이 필요해
		//.anyRequest().permitAll() : 위에꺼 아닌 주소는 허용하겠따.
		//Access Denied : 사용자가 접근권한이 없는 페이지로 갈때 로그인 창 화면으로 가게 해보자 : .and()부터
		//.formLogin() : 인증이 필요한 페이지로 갈때 form 로그인을 할거다.
		//.loginPage("/auth/signin") : 그 formLogin페이지가 /auth/signin 이다.(get)
		//.loginProcessingUrl("/auto/signin") //post방식으로 로그인 요청시
		//.defaultSuccessUrl("/"); : 로그인이 정상적으로 됬으면 /로 가게하기
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin") //get
			.loginProcessingUrl("/auth/signin") //post
			.defaultSuccessUrl("/");
	}
}
