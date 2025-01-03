package kr.or.iei.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.or.iei.member.model.vo.Member;

@Repository("memberDao")
public class MemberDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public Member memberLogin(Member member) {
		return sqlSession.selectOne("member.selectOneMember", member);
	}

	public int join(Member member) {
		return sqlSession.insert("member.joinMember", member);
	}

	public int idDuplicationCheck(String memberId) {
		return sqlSession.selectOne("member.idDuplicationCheck", memberId);
	}

	public int deleteMember(String memberId) {
		return sqlSession.delete("member.deleteMember", memberId);
	}

	public int updateMember(Member member) {
		return sqlSession.update("member.updateMember", member);
	}

	public List<Member> allMemberList() {
		return sqlSession.selectList("member.allMemberList");
	}
}
