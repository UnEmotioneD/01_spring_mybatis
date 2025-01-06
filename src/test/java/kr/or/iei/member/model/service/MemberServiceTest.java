package kr.or.iei.member.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;

/*
 * 단의 테스트: 프로그렘에서 가장 작은 단위를 테스트(클래스, 메소드)
 *
 *  Controller, service, Dao 를 여러 개발자가 나누어 개발 진행 시
 *  특정 계층에 대혀어 테스틀 진행
 */

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    // 서비스 계층에서 의존하고 있는 DAO 객체를 가짜 객체(Mock)로 생성
    @Mock
    private MemberDao dao;

    // 테스트 대상 클래스
    @InjectMocks
    private MemberService service;

    @Before
    public void setup() {
        // Mock, InjectMock 어노테이션으로 선언된 객체 주입
        MockitoAnnotations.openMocks(this);
    }

    // MemberService.memberLogin() == 개발 완료
    // MemberDao.memberLogin() == 미진행 (인터페이스를 implements 하여 구조만 존재하는 상태)

    @Test
    public void memberLoginTest() {
        // 1. 가상의 테이터 설정 - Service 의 메소드 호출 시 전달할 매개변수
        Member argMember = new Member();
        argMember.setMemberId("admin");
        argMember.setMemberPw("1234");

        // 2. 가상의 데이터 설정 - DAO 메소드가 반환할 값
        Member daoReturnMember = new Member();
        daoReturnMember.setMemberId("admin");
        daoReturnMember.setMemberPw("1234");
        daoReturnMember.setMemberName("관리자");
        daoReturnMember.setMemberPhone("010-1111-2222");
        daoReturnMember.setMemberAge("10");
        daoReturnMember.setMemberGender("M");
        daoReturnMember.setEnrollDate("24/12/02");

        // 3. Mock 설정(의존하는 DAO 메소드에 대한 반환값 설정)
        // when: 의존하고 있는 메소드
        // thenReturn: 반환값
        when(dao.memberLogin(argMember)).thenReturn(daoReturnMember);

        // 4. 테스트 대상 메소드 실행
        Member testResult = service.memberLogin(argMember);

        // 5. 테스트 수행 검증
        assertNotNull(testResult); // 전달 매개변수가 null 이 아닌지?
        assertEquals(testResult.getMemberGender(), "남자"); // 틀린 값을 넣어서 테스트
    }

}
