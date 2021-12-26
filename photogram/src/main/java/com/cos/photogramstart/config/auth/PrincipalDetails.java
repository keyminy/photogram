package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//권한을 가져오는 함수 (타입 : 컬렉션, 권한은 한개가 아닐수도 있으니)
		//ex.어떤 사람은 3개 이상의 권한을 가지고 있으니
		//return user.getRole(); //String이라 안되요
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()->{return user.getRole();});
		//자바에서는 함수를 파라매터로 넘기고 싶으면 함수형인터페이스를 넘기게 되어있음.
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		//이계정이 만료가 되었나?
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 이 계정이 잠겻나?
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비번 안바꾼지 1년 된지 아닌지?
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 이 계정이 활성화 되어있는지?
		return true;
	}
	
}
