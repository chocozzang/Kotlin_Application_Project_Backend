package com.example.AndroidBack.Controller;

import com.example.AndroidBack.Model.FirstDayWeatherDTO;
import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.ThirdDayWeather;
import com.example.AndroidBack.Model.TodayTideDTO;
import com.example.AndroidBack.Service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
class XYGrid {
    public double x;
    public double y;
}

@Getter
@Setter
@ToString
@AllArgsConstructor
class LatLon {
    public double lat;
    public double lon;
}

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
public class WeatherController {

    TodayTideService todayTideService;
    FirstDayWeatherService firstDayWeatherService;
    SecondDayWeatherService secondDayWeatherService;
    ThirdDayWeatherService thirdDayWeatherService;
    FourthDayWeatherService fourthDayWeatherService;
    private FirstDayWeatherDTO firstDayWeatherDTO;
    private OtherDayWeatherDTO secondDayWeatherDTO;
    private OtherDayWeatherDTO thirdDayWeatherDTO;
    private OtherDayWeatherDTO fourthDayWeatherDTO;
    private OtherDayWeatherDTO sixDayWeatherDTO;
    private OtherDayWeatherDTO seventhDayWeatherDTO;

    private XYGrid mapToGrid(double lat, double lon) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43;// 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)
        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        double x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        double y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return new XYGrid(x, y);
    }

    @GetMapping("/todayTide")
    public void setTodayTide() {
        try {
            todayTideService.clearTodayTideTable();
            String serviceKey = "/FFdZti8UpV2Ku/EnEYvg==";
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String today_time = simpleDateFormat.format(today);
            List<String> obsCodeList = new ArrayList<>(Arrays.asList("DT_0054", "DT_0004", "DT_0005", "DT_0007", "DT_0012", "DT_0016", "DT_0027", "DT_0028", "DT_0029", "DT_0031", "DT_0035", "DT_0050", "DT_0056"));

            for(String obs : obsCodeList) {
                StringBuilder urlBuilder = new StringBuilder("http://www.khoa.go.kr/api/oceangrid/tideObsPre/search.do");
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
                urlBuilder.append("&" + URLEncoder.encode("ObsCode", "UTF-8") + "=" + obs);
                urlBuilder.append("&" + URLEncoder.encode("Date", "UTF-8") + "=" + today_time);
                urlBuilder.append("&" + URLEncoder.encode("ResultType", "UTF-8") + "=" + "json");
                URL thisUrl = new URL(urlBuilder.toString());
                //System.out.println(urlBuilder.toString());
                setSubTodayTide(thisUrl);
            }

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }
    }

    @GetMapping("/getTodayTide")
    public List<TodayTideDTO> getTodayTide(@RequestParam("obscode") String obsCode) {
        return todayTideService.getTodayTide(obsCode);
    }

    public void setSubTodayTide(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader jsonReader;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                jsonReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                jsonReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder temp = new StringBuilder();
            String line;
            while((line = jsonReader.readLine()) != null) {
                temp.append(line);
            }
            String result = temp.toString();

            jsonReader.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject resultCol  = (JSONObject) jsonObject.get("result");
            JSONArray dataCol    = (JSONArray) resultCol.get("data");


            JSONObject metadata = (JSONObject) resultCol.get("meta");
            String obsCode = ((JSONObject) metadata).get("obs_post_id").toString();

            for(int i = 0; i < dataCol.size(); i++) {
                TodayTideDTO todayTideDTO = new TodayTideDTO();
                todayTideDTO.setCode(obsCode);
                todayTideDTO.setLevel(((JSONObject) dataCol.get(i)).get("tide_level").toString());
                todayTideService.setTodayTide(todayTideDTO);
            }

        } catch(Exception e) {
            e.getMessage();
        }
    }

    @GetMapping("/preWeather")
    public void setPreWeatherToday() {
        FirstDayWeatherDTO firstDayWeatherDTO = new FirstDayWeatherDTO();

        try {
            firstDayWeatherService.clearFirstDayWeather();
            secondDayWeatherService.clearSecondDayWeather();
            String serviceKey = "/FFdZti8UpV2Ku/EnEYvg==";
            String serviceKey2 = "0DZUAX87M9kJWvxPJWL3raL5m9BYWp2N%2FzlC8zZYrvAg6Lwvv7WqwI4%2Bvb729zpp8rxMBKyp29N7kJEzNwrdhQ%3D%3D";
            Date today = new Date();
            SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HHmm");
            String today_time = simpleDateFormat.format(today);
            String today_hour = simpleDateFormat2.format(today);
            List<String> obsCodeList = new ArrayList<>(Arrays.asList("DT_0054", "DT_0004", "DT_0005", "DT_0007", "DT_0012", "DT_0016", "DT_0027", "DT_0028", "DT_0029", "DT_0031", "DT_0035", "DT_0050", "DT_0056"));
            List<LatLon> latlonList  = new ArrayList<>(Arrays.asList(
                    new LatLon(35.147, 128.643), new LatLon(33.527, 126.543), new LatLon(35.096, 129.035),
                    new LatLon(34.779, 126.375), new LatLon(38.207, 128.594), new LatLon(34.747, 127.765),
                    new LatLon(34.315, 126.759), new LatLon(34.377, 126.308), new LatLon(34.801, 128.699),
                    new LatLon(34.028, 127.308), new LatLon(34.684, 125.435), new LatLon(36.913, 126.238),
                    new LatLon(35.077, 128.768)
            ));
            List<String> regionList  = new ArrayList<>(Arrays.asList("11H20301", "11G00201", "11H20201", "21F20801", "11D20401", "11F20401", "11F20301", "21F20201", "11H20403", "11F20401", "11F20701", "11C20102", "11H20201"));
            for(int day = 0; day < 4; day++) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, day);
                today_time = simpleDateFormat.format(cal.getTime());
                for(int i = 0; i < obsCodeList.size(); i++) {
                    StringBuilder urlBuilder = new StringBuilder("http://www.khoa.go.kr/api/oceangrid/tideObsPreTab/search.do");
                    urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
                    urlBuilder.append("&" + URLEncoder.encode("ObsCode", "UTF-8") + "=" + obsCodeList.get(i));
                    urlBuilder.append("&" + URLEncoder.encode("Date", "UTF-8") + "=" + today_time);
                    urlBuilder.append("&" + URLEncoder.encode("ResultType", "UTF-8") + "=" + "json");
                    URL thisUrl = new URL(urlBuilder.toString());
//                    Integer base = Integer.valueOf(today_hour);
//                    String basetime = "";
//                    //System.out.println(base);
//                    if(base >= 215 && base <= 514) basetime = "0200";
//                    else if(base >= 515 && base <= 814) basetime = "0500";
//                    else if(base >= 815 && base <= 1114) basetime = "0800";
//                    else if(base >= 1115 && base <= 1414) basetime = "1100";
//                    else if(base >= 1415 && base <= 1714) basetime = "1400";
//                    else if(base >= 1715 && base <= 2014) basetime = "1700";
//                    else if(base >= 2015 && base <= 2314) basetime = "2000";
//                    else if(base >= 2315 && base <= 2400) basetime = "2300";
//                    else {
//                        cal = Calendar.getInstance();
//                        cal.add(Calendar.DATE, -1);
//                        today_time = simpleDateFormat.format(cal.getTime());
//                        basetime = "2300";
//                    }
                    setSubPreWeather(thisUrl, day);
            }


//                StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
//                urlBuilder2.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey2);
//                urlBuilder2.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + "json");
//                urlBuilder2.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + today_time);
//                LatLon templl = latlonList.get(i);
//                XYGrid tempxy = mapToGrid(templl.lat, templl.lon);
//                urlBuilder2.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + Integer.parseInt(String.valueOf(Math.round(tempxy.x))));
//                urlBuilder2.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + Integer.parseInt(String.valueOf(Math.round(tempxy.y))));
//                urlBuilder2.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + "1");
//                urlBuilder2.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + "1000");
//                urlBuilder2.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + basetime);
//                URL thisUrl2 = new URL(urlBuilder2.toString());
//                cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, 1);

            }

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }
    }

    public void setSubPreWeather(URL url, int day) {
        try {
            // 조석 //
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader jsonReader;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                jsonReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                jsonReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder temp = new StringBuilder();
            String line;
            while((line = jsonReader.readLine()) != null) {
                temp.append(line);
            }
            String result = temp.toString();

            jsonReader.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject resultCol  = (JSONObject) jsonObject.get("result");
            JSONArray dataCol    = (JSONArray) resultCol.get("data");
            JSONObject metadata = (JSONObject) resultCol.get("meta");
            String obsCode = ((JSONObject) metadata).get("obs_post_id").toString();

//            // 기온 //
//            System.out.println("HERE TEMPER");
//            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
//            conn2.setRequestMethod("GET");
//            conn2.setRequestProperty("Content-type", "application/json");
//
//            BufferedReader jsonReader2;
//
//            if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
//                jsonReader2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
//            } else {
//                jsonReader2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
//            }
//
//            StringBuilder temp2 = new StringBuilder();
//            String line2;
//            while((line2 = jsonReader2.readLine()) != null) {
//                temp2.append(line2);
//            }
//            String result2 = temp2.toString();
//            jsonReader2.close();
//            conn2.disconnect();
//
//            JSONObject jsonObject2 = (JSONObject) jsonParser.parse(result2);
//            JSONObject jsonResponse= (JSONObject) jsonObject2.get("response");
//            JSONObject jsonBody    = (JSONObject) jsonResponse.get("body");
//            JSONObject jsonItems   = (JSONObject) jsonBody.get("items");
//            JSONArray  jsonArray   = (JSONArray)  jsonItems.get("item");
//
//            String temperature = null;
//            for(Object jo : jsonArray) {
//                if(((JSONObject) jo).get("category").toString().equals("TMP")) {
//                    System.out.println((JSONObject) jo);
//                    temperature = ((JSONObject) jo).get("fcstValue").toString();
//                    break;
//                }
//            }
//
//            firstDayWeatherDTO.setNowtemp(temperature);
            if(day == 0) {
                firstDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        firstDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 1) {
                        firstDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 2) {
                        firstDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 3) {
                        firstDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    }

                }
                System.out.println(firstDayWeatherDTO);
                firstDayWeatherService.setFirstDayWeather(firstDayWeatherDTO);
            } else if(day == 1) {
                secondDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        secondDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 1) {
                        secondDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 2) {
                        secondDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 3) {
                        secondDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    }

                }
                System.out.println(secondDayWeatherDTO);
                secondDayWeatherService.setSecondDayWeather(secondDayWeatherDTO);
            } else if(day == 2) {
                thirdDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        thirdDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 1) {
                        thirdDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 2) {
                        thirdDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 3) {
                        thirdDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    }

                }
                System.out.println(thirdDayWeatherDTO);
                thirdDayWeatherService.setThirdDayWeather(thirdDayWeatherDTO);
            } else if(day == 3) {
                fourthDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        fourthDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 1) {
                        fourthDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 2) {
                        fourthDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    } else if(i == 3) {
                        fourthDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                    }

                }
                System.out.println(fourthDayWeatherDTO);
                fourthDayWeatherService.setFourthDayWeather(fourthDayWeatherDTO);
            }


        } catch(Exception e) {
            e.getMessage();
        }
    }
}
