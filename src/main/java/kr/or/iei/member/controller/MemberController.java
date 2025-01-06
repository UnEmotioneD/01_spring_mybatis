package kr.or.iei.member.controller;

import com.google.gson.Gson;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/member/")
public class MemberController {

    @Autowired
    @Qualifier("memberService")
    private MemberService memberService;

    @PostMapping("login.kh")
    public String memberLogin(Member member, HttpSession session) {

        // MemberService 클래스 상단의
        // Service 어노테이션(컴포넌트)를 작성하여
        // 서버 구동시 자동으로 생성된 객체(Bean)을
        // AutoWired 를 통해서 주입받음
        // --> new 연산자 없이 사용가능

        Member loginMember = memberService.memberLogin(member);

        if (loginMember != null) {
            session.setAttribute("loginMember", loginMember);
            return "redirect:/";
        } else {
            return "member/loginFail";
        }
    }

    @GetMapping("joinFrm.kh")
    public String joinFrm() {
        return "member/join";
    }

    @PostMapping("join.kh")
    public String join(Member member) {
        int result = memberService.join(member);

        if (result == 1) {
            return "redirect:/";
        } else {
            return "member/joinFail";
        }
    }

    @GetMapping("idDuplicationCheck.kh")
    @ResponseBody
    public String idDuplicationCheck(String memberId) {
        int cnt = memberService.idDuplicationCheck(memberId);
        return String.valueOf(cnt);
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("delete")
    public String logout(String memberId) {
        int result = memberService.delete(memberId);

        if (result == 1) {
            return "redirect:/member/logout.kh";
        } else {
            return "/member/deleteFail";
        }
    }

    @GetMapping("mypage.kh")
    public String updateFrm() {
        return "/member/mypage";
    }

    @PostMapping("update.kh")
    public String update(Member member, HttpSession session) {
        int result = memberService.updateMember(member);

        if (result > 0) {
            Member loginMember = (Member) session.getAttribute("loginMember");
            loginMember.setMemberPw(member.getMemberPw());
            loginMember.setMemberName(member.getMemberName());
            loginMember.setMemberPhone(member.getMemberPhone());
            loginMember.setMemberGender(member.getMemberGender());

            return "redirect:/member/mypage.kh";
        } else {
            System.out.println("foobar");
            return "member/updateFail";
        }
    }

    @GetMapping("allMember.kh")
    public String allMember() {
        return "member/allMemberList";
    }

    @GetMapping(value = "allMemberList.kh", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String allMemberList() {
        ArrayList<Member> allMemberList = memberService.allMemberList();
        // 자동으로 자바스크립트 문자열로 만들어 준다
        return new Gson().toJson(allMemberList);
    }
}
