package kr.or.iei.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// 통합테스트: 각 Unit들이 통합된 프로그램과 데이터베이스간의 상호작용 및 컨택스트 로드를 테스트

@RunWith(SpringJUnit4ClassRunner.class) // Spring 에서 JUnit 프레임워크를 사용하기 위함
@WebAppConfiguration // 웹 관련 테스트 환경 설정
@ContextConfiguration( // 컨첵스트 로드 파일 경로 설정(배열)
		locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
				"file:src/main/webapp/WEB-INF/spring/root-context.xml", "classpath:applicationContext.xml" })
public class MemberControllerTest {
	// 로깅 처리 객체( 글 메세지 출력시 MemberController 이름이 로그 메시지에 포함되어 출력된다)
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	// Spring MVC 구성에 필요한 DispatcherServlet 을 포함한 모든 요소(각 컨트롤러, 서비스 리포지토리)들을 관리
	@Autowired
	private WebApplicationContext wac;

	// MockMvc 를 이용하면, Tomcat 구동 없이 비즈니스 로직 테스트 가능
	private MockMvc mockMvc;

	// 각 Test 메소드가 실행되기 이전에 수정되는 코드를 작성
	@Before
	public void setup() {
		logger.info("===== setup() 실행 =====");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test // 데스트 대상 메소드
	public void memberLoginTest() throws Exception {
		logger.info("===== MemberController.memberLogin Test(로그인) =====");

		try {

			mockMvc.perform(post("/member/login.kh") // 로그인 메소드 요청 방식이 post 이므로
					.param("memberId", "admin").param("memberPw", "1234")).andDo(print()) // 실행 과정 출력
					.andExpect(status().isFound()); // HTTP 응답 코드가 302 코드이다

			logger.info("===== 회원 로그인 테스트 수행 성공 =====");
		} catch (Exception e) {
			logger.error("===== 회원 로그인 테스트 수행 실패 =====");
			e.printStackTrace();
		}
	}

	@Test
	public void memberJoinTest() throws Exception {
		logger.info("===== MemberController.joinFrm Test(회원가입 페이지 이동) =====");

		try {
			mockMvc.perform(get("/member/joinFrm.kh"))
			.andDo(print())
			.andExpect(status().isOk()); // HTTP 응답 코드가 200

			logger.info("===== 회원 가입 페이지 이동 테스트 성공 =====");
		} catch (Exception e) {
			logger.error("===== 회원 가입 페이지 이동 테스트 실패 =====");
			e.printStackTrace();
		}
	}
}