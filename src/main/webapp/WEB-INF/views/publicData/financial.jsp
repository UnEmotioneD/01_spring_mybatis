<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>financial.jsp</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<h1>환율정보 조회 서비스</h1>

<hr>

<button id="searchFinancial">환율 조회</button>

<script>
    $("#searchFinancial").on("click", function () {
        $.ajax({
            url: "/api/financial",
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
