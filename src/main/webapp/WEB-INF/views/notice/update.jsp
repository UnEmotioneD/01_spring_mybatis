<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>notice/update.jsp</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<h1>게시글 - 게시글 수정</h1>

<hr>

<form action="/notice/update.kh" method="post"
      enctype="multipart/form-data">
    <%-- 게시글 정보 수정을 위한, 게시글 번호 --%>
    <input type="hidden" name="noticeNo" value="${notice.noticeNo}">

    <table border="1">
        <tr>
            <th>제목</th>
            <td>
                <input type="text" name="noticeTitle" value="${notice.noticeTitle}">
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td>
                <input type="text" name="noticeContent" value="${notice.noticeContent}">
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
                <%-- 신규 게시글 작성 시 첨부파일 업로드를 하지 안을 사용자는 수정 시 업로드 할 수 있도록 input type=file 태그 사용
                     HTML5 에서 기존 file 정보를 intut type=file 로 만들지 않는 이유는 보안적인(로컬 파일 시스탬 경로 조작) 이슈로 지원하지 않음 --%>
                <c:if test="${not empty notice.fileList}">
                    <c:forEach var="file" items="${notice.fileList}">
                        <span>${file.fileName}</span>
                    </c:forEach>
                    <a href="javascript:void(0)"
                       onclick="fileReUpload(this)">재업로드</a>
                </c:if>
                <c:if test="${empty notice.fileList}">
                    <input type="file" name="files" multiple>
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="수정하기">
            </td>
        </tr>
    </table>
</form>
<script>
    function fileReUpload(obj) {
        // obj : 클릭한 <a> 태그
        let tdEl = $(obj).parent(); // 그 상위에 있는 <td> 태그

        tdEl.find('span').remove(); // 기존 파일 정보 추력 <span> 태그 삭제

        let inputEl = $('<input>'); // 재업로드 할 수 있도록 input type=file 생성
        inputEl.attr('type', 'file');
        inputEl.attr('name', 'files');
        inputEl.attr('multiple', true);

        tdEl.append(inputEl);

        $(obj).remove();
    }
</script>
</body>
</html>
