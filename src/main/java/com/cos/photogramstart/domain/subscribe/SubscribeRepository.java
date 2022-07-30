package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{
		
	@Modifying
	@Query(value="INSERT INTO subscribe(fromUserId,toUserId,createDate) "
			+ "VALUES(:fromUserId,:toUserId,now())",nativeQuery = true)
	void mSubscribe(int fromUserId,int toUserId);
	
	@Modifying
	@Query(value="DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserId=:toUserId ",nativeQuery = true)
	void mUnSubscribe(int fromUserId,int toUserId);
	
	//구독 상태 확인
	@Query(value="SELECT COUNT(*) FROM subscribe "
			+ "WHERE fromUserId=:principalId AND toUserId=:pageUserId",nativeQuery = true)
	int mSubscribeState(int principalId,int pageUserId);
	
	//해당 유저가 구독한 사람 수 카운트.
	@Query(value="SELECT COUNT(*) FROM subscribe "
			+ "WHERE fromUserId=:pageUserId",nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
