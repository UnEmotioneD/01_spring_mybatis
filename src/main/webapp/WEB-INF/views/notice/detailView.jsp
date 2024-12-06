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
                <%--
                <a href="javascript:void(0)" onclick="fileDown('${file.fileName}', '${file.filePath}')">${file.fileName}</a>
                --%>

                <%-- 더 간단하게 다운 받는 방법
                !!!하지만 url 에 그 경로가 유출 된다!!!
                --%>
                <a href="/resources/upload/notice/${file.filePath}" download>${file.fileName}</a>
            </c:forEach>
        </td>
    </tr>
</table>
<script>
    function fileDown(fileName, filePath) {
        fileName = encodeURIComponent(fileName);
        filePath = encodeURIComponent(filePath);
        location.href = "/notice/fileDown.kh?fileName=" + fileName + "&filePath=" + filePath;
    }
</script>
</body>
</html>
