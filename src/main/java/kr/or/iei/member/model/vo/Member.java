package kr.or.iei.member.model.vo;

public class Member {
  private String memberId;
  private String memberPw;
  private String memberName;
  private String memberPhone;
  private String memberAge;
  private String memberGender;
  private String enrollDate;

  public Member() {
    super();
  }

  public Member(String memberId, String memberPw, String memberName, String memberPhone, String memberAge,
                String memberGender, String enrollDate) {
    super();
    this.memberId = memberId;
    this.memberPw = memberPw;
    this.memberName = memberName;
    this.memberPhone = memberPhone;
    this.memberAge = memberAge;
    this.memberGender = memberGender;
    this.enrollDate = enrollDate;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getMemberPw() {
    return memberPw;
  }

  public void setMemberPw(String memberPw) {
    this.memberPw = memberPw;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getMemberPhone() {
    return memberPhone;
  }

  public void setMemberPhone(String memberPhone) {
    this.memberPhone = memberPhone;
  }

  public String getMemberAge() {
    return memberAge;
  }

  public void setMemberAge(String memberAge) {
    this.memberAge = memberAge;
  }

  public String getMemberGender() {
    return memberGender;
  }

  public void setMemberGender(String memberGender) {
    this.memberGender = memberGender;
  }

  public String getEnrollDate() {
    return enrollDate;
  }

  public void setEnrollDate(String enrollDate) {
    this.enrollDate = enrollDate;
  }

  @Override
  public String toString() {
    return "Member [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName
        + ", memberPhone=" + memberPhone + ", memberAge=" + memberAge + ", memberGender=" + memberGender
        + ", enrollDate=" + enrollDate + "]";
  }
}
