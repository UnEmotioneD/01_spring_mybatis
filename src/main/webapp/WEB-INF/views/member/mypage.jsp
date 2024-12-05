<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>member/updateFrm.jsp</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<h1>회원관리 - 회원정보 수정 페이지</h1>

<hr>

<form action="/member/update.kh" method="post">
    <input type="hidden" name="memberId" value="${loginMember.memberId}">
    <table border="1">
        <tr>
            <th>아이디</th>
            <td>${loginMember.memberId}</td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td><input type="password" name="memberPw" value="${loginMember.memberPw}"></td>
        </tr>
        <tr>
            <th>이름</th>
            <td><input type="text" name="memberName" value="${loginMember.memberName}"></td>
        </tr>
        <tr>
            <th>나이</th>
            <td><input type="text" name="memberAge" value="${loginMember.memberAge}"></td>
        </tr>
        <tr>
            <th>전화번호</th>
            <td><input type="text" name="memberPhone" value="${loginMember.memberPhone}"></td>
        </tr>
        <tr>
            <th>성별</th>
            <td>
                <input type="radio" name="memberGender" value="M"
                       <c:if test="${loginMember.memberGender eq 'M'}">checked</c:if>
                >남자
                <input type="radio" name="memberGender" value="W"
                       <c:if test="${loginMember.memberGender eq 'W'}">checked</c:if>
                >여자
            </td>
        </tr>
        <tr>
            <th>가입일</th>
            <td>${loginMember.enrollDate}</td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="회원정보 수정">
            </td>
        </tr>
    </table>
</form>
<script>
    /*
    $(function () {
        let memberGender = "${loginMember.memberGender}";

        $("input[name=memberGender][value='+memberGender+']").prop("checked", true);
    })
    */
</script>
</body>
</html>
