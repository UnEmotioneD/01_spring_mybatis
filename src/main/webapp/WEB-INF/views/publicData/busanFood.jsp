<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>busanFood.jsp</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<h1>부산 맛집 정보 서비스</h1>

<hr>

<table border="1">
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
                for (let i = 0; i < resData.length; i++) {
                    let title = resData[i].placeTitle;
                    let tel = resData[i].placeTel;
                    let hour = resData[i].placeHour;
                    let addr = resData[i].placeAddr;
                    let img = resData[i].placeImg;

                    const trEl = document.createElement('tr');

                    const titleTd = document.createElement('td');
                    titleTd.textContent = title;
                    trEl.appendChild(titleTd);

                    const telTd = document.createElement('td');
                    telTd.textContent = tel;
                    trEl.appendChild(telTd);

                    const hourTd = document.createElement('td');
                    hourTd.textContent = hour;
                    trEl.appendChild(hourTd);

                    const addrTd = document.createElement('td');
                    addrTd.textContent = addr;
                    trEl.appendChild(addrTd);

                    const imgTd = document.createElement('td');
                    const imgEl = document.createElement('img');

                    imgEl.src = img;
                    imgEl.alt = title + " 이미지";
                    imgEl.style.height = '100px';
                    imgTd.appendChild(imgEl);
                    trEl.appendChild(imgEl);

                    const tableEl = document.getElementsByTagName('table')[0];
                    tableEl.appendChild(trEl);
                }

            },
            error: function () {
                console.log("ajax failed");
            }
        })
    });
</script>

</body>
</html>
