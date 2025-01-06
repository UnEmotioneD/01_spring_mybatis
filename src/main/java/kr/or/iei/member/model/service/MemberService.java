package kr.or.iei.member.model.service;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("memberService")
public class MemberService {

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    public Member memberLogin(Member member) {
        return memberDao.memberLogin(member);
    }

    public int join(Member member) {
        return memberDao.join(member);
    }

    public int idDuplicationCheck(String memberId) {
        return memberDao.idDuplicationCheck(memberId);
    }

    public int delete(String memberId) {
        return memberDao.deleteMember(memberId);
    }

    public int updateMember(Member member) {
        return memberDao.updateMember(member);
    }

    public ArrayList<Member> allMemberList() {
        return (ArrayList<Member>) memberDao.allMemberList();
    }

}