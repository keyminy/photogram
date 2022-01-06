package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId,int pageUserId){
		//쿼리만들기위해 Strinrgbuffer
		StringBuffer sb = new StringBuffer();
		//쿼리 준비 : 집어넣을떄 한칸 뛰워주세요 조심..
		sb.append("SELECT u.id,u.username,u.profileImageUrl ");
		sb.append(",if((SELECT 1 FROM subscribe WHERE fromuserId=? AND toUserId=u.id),1,0) subscribeState");
		sb.append(",if((?=u.id),1,0) equalUserState ");
		sb.append("FROM user u JOIN subscribe s ");
		sb.append("ON u.id=s.toUserId ");
		sb.append("WHERE s.fromUserId=?");
		//1.principalId,2.principalId,pageUserId(페이지의 주인정보)
		
		//쿼리완성
		//Query는 javax.persistence 임포트 조심
		Query query = em.createNativeQuery(sb.toString())
						.setParameter(1,principalId)
						.setParameter(2,principalId)
						.setParameter(3,pageUserId);
		
		//쿼리 실행(qlrm라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
		JpaResultMapper result = new JpaResultMapper();
		//result. 메소드에서 list(여러건 리턴),uniqueRequeslt(한건 리턴받을때)
		List<SubscribeDto> subscribeDtos = result.list(query,SubscribeDto.class); // SubscribeDto.class로 리턴받겠다..
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId,int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId,int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
	
}
