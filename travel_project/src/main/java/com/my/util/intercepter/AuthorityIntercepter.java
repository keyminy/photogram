package com.my.util.intercepter;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.my.member.vo.LoginVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class AuthorityIntercepter extends HandlerInterceptorAdapter {
	
	//이 클래스에서만 사용하는 변수선언
	private String url = null;
	
	//url에 대한 권한 정보를 저장하는 Map
	//Map<url,gradeNo>
	private static Map<String, Integer> authMap = new HashMap<String, Integer>();
	
	//페이지에 대한 등급 정보를 저장하는 메서드
	//데이터를 넣는 방법 : static 초기화 블록
	static {
		//공지사항 - 등록,수정,삭제 - 관리자만 가능 : 9
		authMap.put("/notice/update.do", 9);
		authMap.put("/notice/write.do", 9);
		authMap.put("/notice/delete.do", 9);
		//qna게시판 - 질문하기,답변하기,수정,삭제 - 회원 : 1(로그인해야함) (일반회원도 답변가능)
		authMap.put("/qna/update.do", 1);
		authMap.put("/qna/updateForm.do", 1);
		authMap.put("/qna/write.do", 1);
		authMap.put("/qna/delete.do", 1);
		authMap.put("/qna/questionForm.do", 1);
		authMap.put("/qna/question.do", 1);
		authMap.put("/qna/answerForm.do", 1);
		authMap.put("/qna/answer.do", 1);
		//이미지 게시판 - 등록,수정,삭제 - 회원 : 1(로그인 했는지 체크)
		authMap.put("/image/write.do", 1);
		authMap.put("/image/update.do", 1);
		authMap.put("/image/updateFile.do", 1);
		authMap.put("/image/delete.do", 1);
		authMap.put("/imageAjax/like.do", 1);
		authMap.put("/imageAjax/likeCancle.do", 1);
		// 메시지 게시판
		authMap.put("/message/list.do", 1);
		authMap.put("/message/view.do", 1);
		authMap.put("/message/writeForm.do", 1);
		authMap.put("/message/write.do", 1);
		authMap.put("/message/delete.do", 1);
		authMap.put("/messageAjax/msgCount.do", 1);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		log.info("+[AuthorityIntercepter]-----------------------------+");
		
		url = request.getServletPath();
		log.info("Authority.preHandle.url : " + url);
		//로그인 객체 꺼내기(request객체에서 session꺼내기)
		HttpSession session = request.getSession();
		LoginVO vo = (LoginVO)session.getAttribute("login");

		if(!checkAuthority(vo)) {
			if(authMap.get(url) == 9) {
				response.sendRedirect(request.getContextPath()+ "/error/admin_only.do");
			}
			//오류페이지로 이동시킴
			response.sendRedirect(request.getContextPath()+ "/error/auth_error.do");
			return false;
		}
		
		//요청한 내용을 계속 진행시킴.
		return super.preHandle(request, response, handler);
	} //end preHandle
	
	private boolean checkAuthority(LoginVO vo) {
		//url정보가 authMap이 있는지 확인한다.
		//pageGradeNo 데이터가 없음(=null)이면 권한 체크가 필요없는 페이지 요청이다.
		Integer pageGradeNo = authMap.get(url);
		if(pageGradeNo==null) {
			log.info("AuthorityFilter.checkAuthority() - 권한이 필요없는 페이지 입니다.");
			return true;
		}
		//여기서 부터 로그인이 필요한 처리임, vo가 null이면 안된다.
		if(vo==null) {
			log.info("AuthorityFilter.checkAuthority() - 로그인이 필요합니다.");
			return false;
		}
		log.info("AuthorityFilter.checkAuthority().pageGradeNo : " + pageGradeNo);
		log.info("AuthorityFilter.checkAuthority().userGradeNo : " + vo.getGradeNo());
		
		//권한이 없는 페이지 요청에 대한 처리(관리자 권한 페이지인 경우)
		if(pageGradeNo > vo.getGradeNo()) {
			log.info("관리자만 가능합니다.");
			return false;
		}
		log.info("AuthorityFilter.checkAuthority() - 권한이 있습니다.");
		return true;
	}
}
