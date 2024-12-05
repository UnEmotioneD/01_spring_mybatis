<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>notice/detailView.jsp</title>
</head>
<body>
<h1>게시글 - 게시글 상세 조회</h1>

<hr>

<table border="1">
    <tr>
        <th>번호</th>
        <td>${notice.noticeNo}</td>
    </tr>
    <tr>
        <th>제목</th>
        <td>${notice.noticeTitle}</td>
    </tr>
    <tr>
        <th>내용</th>
        <td>${notice.noticeContent}</td>
    </tr>
    <tr>
        <th>첨부파일</th>
        <td>
            <c:forEach var="file" items="${notice.fileList}">
                <a>${file.fileName}</a>
            </c:forEach>
        </td>
    </tr>
</table>
</body>
</html>
