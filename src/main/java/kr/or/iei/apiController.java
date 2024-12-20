package kr.or.iei;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Controller
@RequestMapping("/api")
public class apiController {

    @GetMapping("/publicData")
    public String publicData(String reqPage) {
        return "publicData/" + reqPage;
    }

    // 부산 맛집 서비스 - XML - Jsoup 라이브러리 사용
    @GetMapping(value = "/busanFoodXml1", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String busanFoodXml1(String reqPage) {
        // busanFood.jsp 로 보내줄 JSON 문자열
        String resJsonStr = "";

        // 맛집 정보 서비스를 제공하는 URL 에서 한글로 문서를 받기
        String url = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";

        // 맛집 정보 서비스를 사용하기 위한 serviceKey
        // Jsoup 에서 자동으로 Encoding 을 주기때문에 Decoding 된 키를 가져옴
        String serviceKey = "9H3GYK5XXwOvghxvW5ANjSW92P0mjZ8Q15SyV1KPF1shMbJFQ25Aq2cDj6Ozpk0UzRGl/EW58wHSouNqqy26GQ==";

        try {
            Document document = Jsoup.connect(url)
                .data("ServiceKey", serviceKey)
                .data("numOfRows", "10")
                .data("pageNo", reqPage)
                .ignoreContentType(true)
                .get();

            Elements elements = document.select("item"); // 등답 데이터 중 item 태그만 선택
            ArrayList<FoodPlace> placeList = new ArrayList<>();

            for (Element element : elements) {
                // MAIN_TITLE 태그는 item 태그안에서 하나만 있으니까 .get(0) 으로 첫번째(하나만) 추출
                String placeTitle = element.select("MAIN_TITLE").get(0).text();
                String placeTel   = element.select("CNTCT_TEL").get(0).text();
                String placeHour  = element.select("USAGE_DAY_WEEK_AND_TIME").get(0).text();
                String placeAddr  = element.select("ADDR1").get(0).text();
                String placeImg   = element.select("MAIN_IMG_THUMB").get(0).text();

                FoodPlace place = new FoodPlace(placeTitle, placeTel, placeHour, placeAddr, placeImg);
                placeList.add(place);
            }
            Gson gson = new Gson();
            resJsonStr = gson.toJson(placeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resJsonStr;
    }

    @GetMapping(value = "/busanFoodXml2", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String busanFoodXml2(String reqPage) {
        String resJsonStr = "";
        String url = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
        String serviceKey = "9H3GYK5XXwOvghxvW5ANjSW92P0mjZ8Q15SyV1KPF1shMbJFQ25Aq2cDj6Ozpk0UzRGl%2FEW58wHSouNqqy26GQ%3D%3D";

        url += "?serviceKey=" + serviceKey;
        url += "&numOfRows=10";
        url += "&pageNo=" + reqPage;

        // 데이터 요청 및 응답
        try {
            org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);

            // 응답 테그 중에서 루트 태그 선택
            doc.getDocumentElement().normalize();

            // 응답 XML 중 item 태그들만 선택
            NodeList nodeList = doc.getElementsByTagName("item");
            ArrayList<FoodPlace> placeList = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                org.w3c.dom.Element el = (org.w3c.dom.Element) node;

                String placeTitle = el.getElementsByTagName("MAIN_TITLE").item(0).getTextContent();
                String placeTel   = el.getElementsByTagName("CNTCT_TEL").item(0).getTextContent();
                String placeHour  = el.getElementsByTagName("USAGE_DAY_WEEK_AND_TIME").item(0).getTextContent();
                String placeAddr  = el.getElementsByTagName("ADDR1").item(0).getTextContent();
                String placeImg   = el.getElementsByTagName("MAIN_IMG_THUMB").item(0).getTextContent();

                FoodPlace place = new FoodPlace(placeTitle, placeTel, placeHour, placeAddr, placeImg);
                placeList.add(place);
            }
            resJsonStr = new Gson().toJson(placeList);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
        return resJsonStr;
    }

    @GetMapping(value = "/financial", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String financial(String reqPage) {
        String reqUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
        reqUrl += "?authkey=exgofQSJ63NgJXrvmii8oMdgBYDMnDbi";
        reqUrl += "&searchdate=20241218"; // 날짜가 주말이면 안된다
        reqUrl += "&data=AP01";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder resStr = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    resStr.append(inputLine);
                }
                in.close();

                JsonArray jsonArr = JsonParser.parseString(resStr.toString()).getAsJsonArray();

                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = (JsonObject) jsonArr.get(i);

                    String curUnit = jsonObj.get("cur_unit").getAsString();
                    String curNm   = jsonObj.get("cur_nm").getAsString();
                    String bkPr    = jsonObj.get("bkpr").getAsString();

                    System.out.println(
                        "화폐 단위: "   + curUnit +
                        "\n화폐 이름: " + curNm +
                        "\n원 가치: "   + bkPr +
                        "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

}
