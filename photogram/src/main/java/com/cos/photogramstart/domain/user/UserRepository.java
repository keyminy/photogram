package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 JpaRepository를 상속하면 IOC등록이 자동으로됨
public interface UserRepository extends JpaRepository<User, Integer> {
	//<User, Integer> : <object,pk의 타입>
	//JPA query method
	User findByUsername(String username);
}
