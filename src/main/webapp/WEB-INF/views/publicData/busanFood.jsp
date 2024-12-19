<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>busanFood.jsp</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<h1>부산 맛집 정보 서비스</h1>

<hr>

<table>
    <tr>
        <th style="width:15%;">상호명</th>
        <th style="width:10%;">전화번호</th>
        <th style="width:15%;">영업시간</th>
        <th style="width:15%;">주소</th>
        <th style="width:15%;">이미지</th>
    </tr>
</table>

<div>
    <button id="more-btn">더보기</button>
</div>

<script>
    let reqPage = 0;
    $("#more-btn").click(function () {
        reqPage++;

        $.ajax({
            url: "/api/busanFoodXml2",
            data: {
                "reqPage": reqPage
            },
            success: function (resData) {
                console.log(resData);
            },
            error: function () {
                console.log("ajax failed");
            }
        })
    });
</script>

</body>
</html>
