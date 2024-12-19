<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>index.jsp</title>
</head>
<body>
<h1>Spring - MyBatis Project</h1>

<pre>
    MyBatis : JAVA 어플리케이션과 DB 상호 작용을 간편하게 하기 위한 영속성(none volatile) 프레임워크

    MyBatis 특징)

    1. 자동 매핑 : DB 수행 결과를 JAVA 객체로 자동으로 매핑하는 기술
    2. XML 파일에 SQL 을 작성 : SQL 이 JAVA 코드와 분리되어 유지보수 및 가독성 증대
    3. 동적 SQL 지원 : if, foreach, choose ~ when ~ otherwise, when
    4. SQL 재사용 : XML 에 작성된 하나의 SQL 을 여러곳에서 사용이 가능
 </pre>

<hr>
<%--
    아래와 같이 JSTL 과 EL 을 같이 사용하게 편리

    prefix(c) 를 쓰는게 JSTL
    ${} 을 쓰는게 EL

    - page, request, session, application 의 scope 가 존재
    - 아래처럼 scope 를 명시하지 안흐면 범위가 좁은 scope 의 attribute 에 순차적으로 접근
    - loginMember 는 Controller 에서 로그인시 HttpSession 에 등록한 Key(문자열) 를 의미 하다
 --%>
<c:if test="${empty sessionScope.loginMember}">
    <form action="/member/login.kh" method="post">
        아이디 : <input type="text" name="memberId"><br>
        비밀번호 : <input type="password" name="memberPw"><br>
        <input type="submit" value="로그인">
        <a href="/member/joinFrm.kh">회원가입</a>
    </form>
</c:if>

<c:if test="${not empty sessionScope.loginMember}">
    <h3>[${loginMember.memberName}]님 환영합니다</h3>
    <h3><a href="/member/logout.kh">로그아웃</a></h3>
    <h3><a href="/member/delete.kh?memberId=${loginMember.memberId}">회원탈퇴</a></h3>
    <h3><a href="/member/mypage.kh">마이페이지</a></h3>
    <h3><a href="/member/allMember.kh">전체회원 조회</a></h3>
    <h3><a href="/notice/getList.kh?reqPage=1">게시글 보기</a></h3>
</c:if>

<hr>

<%--
API : Application Programming Interface
    소프츠웨어 응용 프로그램에서 다른 소프트웨어 구성 요소 또는 서비스와 상호 작용하기 위해 인터페이스를 제공하는 기능

공공 API : 공공기관에서 제공
--%>

<h3><a href="/api/publicData?reqPage=busanFood">부산 맛집 정보</a></h3>
<h3><a href="/api/publicData?reqPage=financial">한국수출입 환율 정보</a></h3>

</body>
</html>
