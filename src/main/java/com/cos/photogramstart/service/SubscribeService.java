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

@Service
@RequiredArgsConstructor
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	//모든 Repository는 EntityManager를 구현한 구현체 입니다.
	//=>그래서 직접 EntityManager로 구현하는 것이다. 
	
	//select만 하므로 readOnly=true
	@Transactional(readOnly=true)
	public List<SubscribeDto> 구독리스트(int principalId,int pageUserId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id,u.username,u.profileImageUrl, ");
		sb.append("IF ((SELECT 1 FROM subscribe WHERE fromuserId=? AND toUserId=u.id),1,0) subscribeState, ");
		sb.append("(SELECT IF(?=u.id,1,0)) eqaulUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id=s.toUserId ");
		sb.append("WHERE s.fromUserId=?");//세미콜론 첨부하면 안됨
	
		//1.물음표 : principalId(로그인한 아이디)
		//2.물음표 : principalId(로그인한 아이디)
		//3.마지막 물음표 : pageUserId(페이지 주인)
		
		//이제 바인딩 시작 => 완성된 쿼리.
		Query query = em.createNativeQuery(sb.toString())
						.setParameter(1, principalId)
						.setParameter(2, principalId)
						.setParameter(3, pageUserId);
		//쿼리 실행하기
		JpaResultMapper result = new JpaResultMapper();
		//Query를 하고, SubscribeDto로 return받겟다.
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId,int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);			
		} catch (Exception e) {
			throw new CustomApiException("이미 구독 하셨습니다.");
		}
	}	
	
	@Transactional
	public void 구독취소하기(int fromUserId,int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
