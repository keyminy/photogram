 package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
	//매개변수로 들어오는 username이 있는지 없는지 검사하기위해 userRepository 주입
	private final UserRepository userRepository;
	//1.패스워드를 알아서 체킹하니까 신경쓸 필요가 없다.
	//2.리턴이 잘되면 자동으로 UserDetails타입을 세션으로 만든다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println("나 실행되?" + username);
		User userEntity = userRepository.findByUsername(username);
		if(userEntity==null) {
			return null;			
		}else {
			return new PrincipalDetails(userEntity);
		}
	}
	
}
