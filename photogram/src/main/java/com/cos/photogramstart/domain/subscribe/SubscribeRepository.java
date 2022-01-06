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
	
	 //52강~ : 구독정보 뷰 렌더링하기,네이티브쿼리씀 
	//select만하므로 @Modifying필없다
	
	//로그인한id,pageId를 받음	
	//구독상태 확인
	@Query(value= "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId",nativeQuery = true)
	int mSubscribeState(int principalId,int pageUserId);
	//해당 유저의 구독자 수 카운트
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId",nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
