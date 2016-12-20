package koreatech.cse.controller;

import io.swagger.annotations.Api;
import koreatech.cse.domain.SearchMovie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "swag-rest-controller")
@RequestMapping("/")
public class HomeController {
    @ModelAttribute("/")
    private String getName() {
        return "IamHomeControllerModelAttribute";
    }

    @RequestMapping(value = "/searchMovie/{movieTitle}",method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ArrayList<SearchMovie>> searchMovie(@PathVariable(value = "movieTitle") String movieTitle) {
        String clientId = "pgQ5ibsIIfdAa69JFON8";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "cFJ3k22PGe";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(movieTitle, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(),"UTF-8"));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject)jsonParser.parse(response.toString());
            JSONArray movieArray = (JSONArray)jsonObj.get("items");

            con.disconnect();

            System.out.println(1);
            for(int i=0; i<movieArray.size(); i++){
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                JSONObject movieObject = (JSONObject) movieArray.get(i);
                String directorNameString = movieObject.get("director").toString();

                System.out.println(2);

                System.out.println("directorNameString = " + directorNameString);
                String[] nameArray = directorNameString.split("[|]");
                System.out.println("nameArray.length = " + nameArray.length);

                for(int j=0;j<nameArray.length;j++){
                    String directorName = nameArray[j].trim();
                    String filmString="";

                    System.out.println(3);

                    List<JSONObject> directorRelatedMovies=new ArrayList<JSONObject>();
                    if(directorName.length()>0) {

                        System.out.println(4);

                        String peopleNm = directorName;
//                   -----------------------------  kobis영화진흥위원회 API 호출 -----------------------------
                        String kobisKey = "bfaa180a0e65d9e9ea45f8b69fe54c58";
                        String kobisURL = String.format("http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?" +
                                "key=%s" +
                                "&peopleNm=%s", kobisKey, URLEncoder.encode(peopleNm, "UTF-8"));
                        url = new URL(kobisURL);
                        HttpURLConnection kobisCon = (HttpURLConnection) url.openConnection();
                        kobisCon.setRequestMethod("GET");
                        responseCode = kobisCon.getResponseCode();

                        if (responseCode == 200) { // 정상 호출
                            br = new BufferedReader(new InputStreamReader(kobisCon.getInputStream(), "UTF-8"));
                        } else {  // 에러 발생
                            br = new BufferedReader(new InputStreamReader(kobisCon.getErrorStream(), "UTF-8"));
                        }
                        response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }

                        System.out.println(5);

                        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
                        JSONObject peopleListResult = (JSONObject) jsonObject.get("peopleListResult");
                        if(((JSONArray)peopleListResult.get("peopleList")).size()<1)
                            continue;

                        JSONArray jsonArray = (JSONArray) peopleListResult.get("peopleList");
                        filmString = ((JSONObject) jsonArray.get(0)).get("filmoNames").toString();

                        System.out.println(6);

                        System.out.println("filmString = " + filmString);
                        String[] relatedMovieTitleArray = filmString.split("[|]");
                        System.out.println("filmStringSize = " + relatedMovieTitleArray.length);

                        for (int k = 0; k < relatedMovieTitleArray.length; k++) {

                            System.out.println(7);

                            DirectorRelatedMovie directorRelatedMovie = new DirectorRelatedMovie();
                            directorRelatedMovie.movieTitle=relatedMovieTitleArray[k];

                            System.out.println(8);
                            System.out.println("directorRelatedMovie.movieTitle = " + directorRelatedMovie.movieTitle);
                            text = URLEncoder.encode(directorRelatedMovie.movieTitle, "UTF-8");
                            if(text.trim().length()<1) continue;
                            apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text; // xml 결과
                            url = new URL(apiURL);
                            con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            System.out.println("apiURL = " + apiURL);
                            System.out.println(9);

                            responseCode = con.getResponseCode();
                            System.out.println("responseCode = " + responseCode);
                            if (responseCode == 200) { // 정상 호출
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                            } else {  // 에러 발생
                                br = new BufferedReader(new InputStreamReader(con.getErrorStream(),"UTF-8"));
                            }
                            response = new StringBuffer();
                            while ((inputLine = br.readLine()) != null) {
                                response.append(inputLine);
                            }

                            System.out.println(10);
                            System.out.println("response = " + response.toString());
                            JSONObject jsonObject1 = (JSONObject)jsonParser.parse(response.toString());
                            System.out.println("jsonObject1 = " + jsonObject1);

                            if(((JSONArray)jsonObject1.get("items")).size()<1)
                                continue;

                            JSONArray jsonArray1 = (JSONArray)jsonObject1.get("items");
                            String link = ((JSONObject) jsonArray1.get(0)).get("link").toString();
                            System.out.println("link = " + link);
                            String image = ((JSONObject) jsonArray1.get(0)).get("image").toString();
                            System.out.println("image = " + image);
                            directorRelatedMovie.link = link;
                            directorRelatedMovie.image = image;

                            System.out.println(11);

                            JSONObject tmpJSONObject = new JSONObject();
                            tmpJSONObject.put("title",directorRelatedMovie.movieTitle);
                            tmpJSONObject.put("link",link);
                            tmpJSONObject.put("image",image);

                            System.out.println(12);

                            directorRelatedMovies.add(tmpJSONObject);
                            con.disconnect();
                        }

                        System.out.println(13);

                        kobisCon.disconnect();
                    }

                    System.out.println(14);

                    movieObject.put("directorRelatedMovies",directorRelatedMovies);
                }
                System.out.println(15);
            }

            System.out.println(16);
            br.close();
            System.out.println(17);
            return new ResponseEntity<ArrayList<SearchMovie>>(movieArray, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<ArrayList<SearchMovie>>(HttpStatus.NOT_FOUND);
        }

    }

}


