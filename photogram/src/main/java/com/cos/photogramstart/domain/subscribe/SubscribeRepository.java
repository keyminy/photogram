package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{
	//INSERT,DELETE,UPDATE를 네이티브쿼리로 작성하려면 해당 어노테이션이 필요하다
	@Modifying //db의 변경을주는 네이티브쿼리에는 @Modifying이 필요하다
	@Query(value ="INSERT INTO subscribe(fromUserId,toUserId,createDate) VALUES(:fromUserId,:toUserId,now())",nativeQuery = true)
	void mSubscribe(int fromUserId,int toUserId); //성공하면 1(변경된 행의 개수만큼), 실패시 -1 리턴되서 int형 반환함
	
	@Modifying
	@Query(value ="DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId=:toUserId",nativeQuery = true)
	void mUnSubscribe(int fromUserId,int toUserId);
	// :은 매개변수를 바인딩해서 넣겟다
}
