package com.example.AndroidBack.Controller;

import com.example.AndroidBack.Model.*;
import com.example.AndroidBack.Service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
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
    FifthDayWeatherService fifthDayWeatherService;
    SixthDayWeatherService sixthDayWeatherService;
    SeventhDayWeatherService seventhDayWeatherService;
    private FirstDayWeatherDTO firstDayWeatherDTO;
    private SecondDayWeatherDTO secondDayWeatherDTO;
    private ThirdDayWeatherDTO thirdDayWeatherDTO;
    private FourthDayWeatherDTO fourthDayWeatherDTO;
    private FifthDayWeatherDTO fifthDayWeatherDTO;
    private SixthDayWeatherDTO sixthDayWeatherDTO;
    private SeventhDayWeatherDTO seventhDayWeatherDTO;

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

    @GetMapping("/totalWeathers")
    public List<TotalWeatherDTO> getTotalWeathers() {
        List<String> obscodelist =  Arrays.asList(
                "DT_0054",
                "DT_0004",
                "DT_0005",
                "DT_0007",
                "DT_0012",
                "DT_0016",
                "DT_0027",
                "DT_0028",
                "DT_0029",
                "DT_0031",
                "DT_0035",
                "DT_0050",
                "DT_0056"
        );

        List<TotalWeatherDTO> totalWeatherDTO = new ArrayList<TotalWeatherDTO>(obscodelist.size());

        for(int i = 0; i < obscodelist.size(); i++) {
            String obscode = obscodelist.get(i);
            TotalWeatherDTO temp = new TotalWeatherDTO();
            List<TodayTideDTO> todayTideDTOS_2 = new ArrayList<TodayTideDTO>();

            todayTideDTOS_2 = todayTideService.getTodayTide(obscode);
            temp.setTodayTideDTOList(todayTideDTOS_2);

            FirstDayWeatherDTO firstDayWeatherDTO_2 = new FirstDayWeatherDTO();
            firstDayWeatherDTO_2 = firstDayWeatherService.getFirstDayWeather(obscode);
            temp.setFirstDayWeatherDTO(firstDayWeatherDTO_2);

            List<OtherDayWeatherDTO> otherDayWeatherDTOS_2 = new ArrayList<>();
            otherDayWeatherDTOS_2.add(secondDayWeatherService.getSecondDayWeather(obscode));
            otherDayWeatherDTOS_2.add(thirdDayWeatherService.getThirdDayWeather(obscode));
            otherDayWeatherDTOS_2.add(fourthDayWeatherService.getFourthDayWeather(obscode));
            otherDayWeatherDTOS_2.add(fifthDayWeatherService.getFifthDayWeather(obscode));
            otherDayWeatherDTOS_2.add(sixthDayWeatherService.getSixthDayWeather(obscode));
            otherDayWeatherDTOS_2.add(seventhDayWeatherService.getSeventhDayWeather(obscode));
            temp.setOtherDayWeatherDTOList(otherDayWeatherDTOS_2);

            totalWeatherDTO.add(temp);
        }

        return totalWeatherDTO;
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
            thirdDayWeatherService.clearThirdDayWeather();
            fourthDayWeatherService.clearFourthDayWeather();
            fifthDayWeatherService.clearFifthDayWeather();
            sixthDayWeatherService.clearSixthDayWeather();
            seventhDayWeatherService.clearSeventhDayWeather();

            String serviceKey = "/FFdZti8UpV2Ku/EnEYvg==";
            String serviceKey2 = "0DZUAX87M9kJWvxPJWL3raL5m9BYWp2N%2FzlC8zZYrvAg6Lwvv7WqwI4%2Bvb729zpp8rxMBKyp29N7kJEzNwrdhQ%3D%3D";
            String serviceKey3 = "0DZUAX87M9kJWvxPJWL3raL5m9BYWp2N%2FzlC8zZYrvAg6Lwvv7WqwI4%2Bvb729zpp8rxMBKyp29N7kJEzNwrdhQ%3D%3D";
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

            for (int i = 0; i < obsCodeList.size(); i++) {
                Integer base = Integer.valueOf(today_hour);
                String basetime = "";
                //System.out.println(base);
                Calendar cal2 = Calendar.getInstance();
                today_time = simpleDateFormat.format(cal2.getTime());
                if (base >= 215 && base <= 514) basetime = "0200";
                else if (base >= 515 && base <= 814) basetime = "0500";
                else if (base >= 815 && base <= 1114) basetime = "0800";
                else if (base >= 1115 && base <= 1414) basetime = "1100";
                else if (base >= 1415 && base <= 1714) basetime = "1400";
                else if (base >= 1715 && base <= 2014) basetime = "1700";
                else if (base >= 2015 && base <= 2314) basetime = "2000";
                else if (base >= 2315 && base <= 2400) basetime = "2300";
                else {
                    cal2.add(Calendar.DATE, -1);
                    today_time = simpleDateFormat.format(cal2.getTime());
                    basetime = "2300";
                }
                StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
                urlBuilder2.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey2);
                urlBuilder2.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + "json");
                urlBuilder2.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + today_time);
                LatLon templl = latlonList.get(i);
                XYGrid tempxy = mapToGrid(templl.lat, templl.lon);
                urlBuilder2.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + Integer.parseInt(String.valueOf(Math.round(tempxy.x))));
                urlBuilder2.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + Integer.parseInt(String.valueOf(Math.round(tempxy.y))));
                urlBuilder2.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + "1");
                urlBuilder2.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + "1000");
                urlBuilder2.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + basetime);
                URL thisUrl2 = new URL(urlBuilder2.toString());
                System.out.println(urlBuilder2.toString());
                setSubPreTemperature(thisUrl2);

                StringBuilder urlBuilder3 = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa");
                urlBuilder3.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey3);
                urlBuilder3.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + 10);
                urlBuilder3.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + 1);
                urlBuilder3.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + "json");
                urlBuilder3.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + regionList.get(i));

                Calendar cal3 = Calendar.getInstance();
                String cal3Time = simpleDateFormat2.format(cal3.getTime());
                int cal3TimeInt = Integer.parseInt(cal3Time);
                String cal3TimeStr = null;
                if(cal3TimeInt >= 605 && cal3TimeInt <= 1804) cal3TimeStr = simpleDateFormat.format(cal3.getTime()) + "0600";
                else if(cal3TimeInt >= 1805 && cal3TimeInt <= 2359) cal3TimeStr = simpleDateFormat.format(cal3.getTime()) + "1800";
                else {
                    cal3.add(Calendar.DATE, -1);
                    cal3TimeStr = simpleDateFormat.format(cal3.getTime()) + "1800";
                }
                urlBuilder3.append("&" + URLEncoder.encode("tmFc") + "=" + cal3TimeStr);
                URL thisurl3 = new URL(urlBuilder3.toString());
                setSubPreTemperature2(thisurl3);

                for(int day = 0; day < 7; day++) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, day);
                    today_time = simpleDateFormat.format(cal.getTime());
                    System.out.println(today_time);
                    StringBuilder urlBuilder = new StringBuilder("http://www.khoa.go.kr/api/oceangrid/tideObsPreTab/search.do");
                    urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
                    urlBuilder.append("&" + URLEncoder.encode("ObsCode", "UTF-8") + "=" + obsCodeList.get(i));
                    urlBuilder.append("&" + URLEncoder.encode("Date", "UTF-8") + "=" + today_time);
                    urlBuilder.append("&" + URLEncoder.encode("ResultType", "UTF-8") + "=" + "json");
                    URL thisUrl = new URL(urlBuilder.toString());
                    setSubPreWeather(thisUrl, day);
                }
            }

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }
    }

    public void setSubPreWeather(URL url, int day) {
        try {
            System.out.println("HERE TIDE1");
            HttpURLConnection conn = null;
            // 조석 //
            conn = (HttpURLConnection) url.openConnection();
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

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject resultCol  = (JSONObject) jsonObject.get("result");
            JSONArray dataCol    = (JSONArray) resultCol.get("data");
            JSONObject metadata = (JSONObject) resultCol.get("meta");
            String obsCode = ((JSONObject) metadata).get("obs_post_id").toString();


            if(day == 0) {
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                firstDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if (i == 0) {
                        firstDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if (i == 1) {
                        firstDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if (i == 2) {
                        firstDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if (i == 3) {
                        firstDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        firstDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        firstDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    firstDayWeatherDTO.setTidelevelOne(null);
                    firstDayWeatherDTO.setTidetimeOne(null);
                    firstDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    firstDayWeatherDTO.setTidelevelTwo(null);
                    firstDayWeatherDTO.setTidetimeTwo(null);
                    firstDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    firstDayWeatherDTO.setTidelevelThree(null);
                    firstDayWeatherDTO.setTidetimeThree(null);
                    firstDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    firstDayWeatherDTO.setTidelevelFour(null);
                    firstDayWeatherDTO.setTidetimeFour(null);
                    firstDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(firstDayWeatherDTO);
                firstDayWeatherService.setFirstDayWeather(firstDayWeatherDTO);
            } else if(day == 1) {
                secondDayWeatherDTO.setObscode(obsCode);
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        secondDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        secondDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        secondDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        secondDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        secondDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        secondDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    secondDayWeatherDTO.setTidelevelOne(null);
                    secondDayWeatherDTO.setTidetimeOne(null);
                    secondDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    secondDayWeatherDTO.setTidelevelTwo(null);
                    secondDayWeatherDTO.setTidetimeTwo(null);
                    secondDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    secondDayWeatherDTO.setTidelevelThree(null);
                    secondDayWeatherDTO.setTidetimeThree(null);
                    secondDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    secondDayWeatherDTO.setTidelevelFour(null);
                    secondDayWeatherDTO.setTidetimeFour(null);
                    secondDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(secondDayWeatherDTO);
                secondDayWeatherService.setSecondDayWeather(secondDayWeatherDTO);
            } else if(day == 2) {
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                thirdDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        thirdDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        thirdDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        thirdDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        thirdDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        thirdDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        thirdDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    thirdDayWeatherDTO.setTidelevelOne(null);
                    thirdDayWeatherDTO.setTidetimeOne(null);
                    thirdDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    thirdDayWeatherDTO.setTidelevelTwo(null);
                    thirdDayWeatherDTO.setTidetimeTwo(null);
                    thirdDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    thirdDayWeatherDTO.setTidelevelThree(null);
                    thirdDayWeatherDTO.setTidetimeThree(null);
                    thirdDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    thirdDayWeatherDTO.setTidelevelFour(null);
                    thirdDayWeatherDTO.setTidetimeFour(null);
                    thirdDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(thirdDayWeatherDTO);
                thirdDayWeatherService.setThirdDayWeather(thirdDayWeatherDTO);
            } else if(day == 3) {
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                fourthDayWeatherDTO.setObscode(obsCode);
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        fourthDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        fourthDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        fourthDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        fourthDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fourthDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fourthDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    fourthDayWeatherDTO.setTidelevelOne(null);
                    fourthDayWeatherDTO.setTidetimeOne(null);
                    fourthDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    fourthDayWeatherDTO.setTidelevelTwo(null);
                    fourthDayWeatherDTO.setTidetimeTwo(null);
                    fourthDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    fourthDayWeatherDTO.setTidelevelThree(null);
                    fourthDayWeatherDTO.setTidetimeThree(null);
                    fourthDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    fourthDayWeatherDTO.setTidelevelFour(null);
                    fourthDayWeatherDTO.setTidetimeFour(null);
                    fourthDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(fourthDayWeatherDTO);
                fourthDayWeatherService.setFourthDayWeather(fourthDayWeatherDTO);
            } else if(day == 4) {
                fifthDayWeatherDTO.setObscode(obsCode);
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        fifthDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fifthDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fifthDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        fifthDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fifthDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fifthDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        fifthDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fifthDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fifthDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        fifthDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        fifthDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        fifthDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    fifthDayWeatherDTO.setTidelevelOne(null);
                    fifthDayWeatherDTO.setTidetimeOne(null);
                    fifthDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    fifthDayWeatherDTO.setTidelevelTwo(null);
                    fifthDayWeatherDTO.setTidetimeTwo(null);
                    fifthDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    fifthDayWeatherDTO.setTidelevelThree(null);
                    fifthDayWeatherDTO.setTidetimeThree(null);
                    fifthDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    fifthDayWeatherDTO.setTidelevelFour(null);
                    fifthDayWeatherDTO.setTidetimeFour(null);
                    fifthDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(fifthDayWeatherDTO);
                fifthDayWeatherService.setFifthDayWeather(fifthDayWeatherDTO);
            } else if(day == 5) {
                sixthDayWeatherDTO.setObscode(obsCode);
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        sixthDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        sixthDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        sixthDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        sixthDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        sixthDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        sixthDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        sixthDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        sixthDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        sixthDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        sixthDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        sixthDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        sixthDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    sixthDayWeatherDTO.setTidelevelOne(null);
                    sixthDayWeatherDTO.setTidetimeOne(null);
                    sixthDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    sixthDayWeatherDTO.setTidelevelTwo(null);
                    sixthDayWeatherDTO.setTidetimeTwo(null);
                    sixthDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    sixthDayWeatherDTO.setTidelevelThree(null);
                    sixthDayWeatherDTO.setTidetimeThree(null);
                    sixthDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    sixthDayWeatherDTO.setTidelevelFour(null);
                    sixthDayWeatherDTO.setTidetimeFour(null);
                    sixthDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(sixthDayWeatherDTO);
                sixthDayWeatherService.setSixthDayWeather(sixthDayWeatherDTO);
            } else if(day == 6) {
                seventhDayWeatherDTO.setObscode(obsCode);
                Boolean checkone = false;
                Boolean checktwo = false;
                Boolean checkthree = false;
                Boolean checkfour = false;
                for(int i = 0; i < dataCol.size(); i++) {
                    if(i == 0) {
                        seventhDayWeatherDTO.setTidelevelOne(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        seventhDayWeatherDTO.setTidetimeOne(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        seventhDayWeatherDTO.setTidetypeOne(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkone = true;
                    } else if(i == 1) {
                        seventhDayWeatherDTO.setTidelevelTwo(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        seventhDayWeatherDTO.setTidetimeTwo(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        seventhDayWeatherDTO.setTidetypeTwo(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checktwo = true;
                    } else if(i == 2) {
                        seventhDayWeatherDTO.setTidelevelThree(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        seventhDayWeatherDTO.setTidetimeThree(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        seventhDayWeatherDTO.setTidetypeThree(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkthree = true;
                    } else if(i == 3) {
                        seventhDayWeatherDTO.setTidelevelFour(((JSONObject) dataCol.get(i)).get("tph_level").toString());
                        seventhDayWeatherDTO.setTidetimeFour(((JSONObject) dataCol.get(i)).get("tph_time").toString());
                        seventhDayWeatherDTO.setTidetypeFour(((JSONObject) dataCol.get(i)).get("hl_code").toString());
                        checkfour = true;
                    }
                }
                if(!checkone) {
                    seventhDayWeatherDTO.setTidelevelOne(null);
                    seventhDayWeatherDTO.setTidetimeOne(null);
                    seventhDayWeatherDTO.setTidetypeOne(null);
                }
                if(!checktwo) {
                    seventhDayWeatherDTO.setTidelevelTwo(null);
                    seventhDayWeatherDTO.setTidetimeTwo(null);
                    seventhDayWeatherDTO.setTidetypeTwo(null);
                }
                if(!checkthree) {
                    seventhDayWeatherDTO.setTidelevelThree(null);
                    seventhDayWeatherDTO.setTidetimeThree(null);
                    seventhDayWeatherDTO.setTidetypeThree(null);
                }
                if(!checkfour) {
                    seventhDayWeatherDTO.setTidelevelFour(null);
                    seventhDayWeatherDTO.setTidetimeFour(null);
                    seventhDayWeatherDTO.setTidetypeFour(null);
                }
                System.out.println(seventhDayWeatherDTO);
                seventhDayWeatherService.setSeventhDayWeather(seventhDayWeatherDTO);
            }
            jsonReader.close();
            conn.disconnect();

        } catch(Exception e) {
            e.getMessage();
        }

    }

    public void setSubPreTemperature(URL url2) {
        // 기온 //

        try {
            System.out.println("HERE TEMPER1");
            HttpURLConnection conn2 = null;
            conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("GET");
            conn2.setRequestProperty("Content-type", "application/json");

            BufferedReader jsonReader2;
            JSONParser jsonParser2 = new JSONParser();

            if (conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
                jsonReader2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
            } else {
                jsonReader2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
            }

            StringBuilder temp2 = new StringBuilder();
            String line2;
            while ((line2 = jsonReader2.readLine()) != null) {
                temp2.append(line2);
            }
            String result2 = temp2.toString();

            JSONObject jsonObject2 = (JSONObject) jsonParser2.parse(result2);
            JSONObject jsonResponse = (JSONObject) jsonObject2.get("response");
            JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
            JSONObject jsonItems = (JSONObject) jsonBody.get("items");
            JSONArray jsonArray = (JSONArray) jsonItems.get("item");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            String firstDay = simpleDateFormat.format(cal2.getTime());
            cal2.add(Calendar.DATE, 1);
            String secondDay = simpleDateFormat.format(cal2.getTime());
            cal2.add(Calendar.DATE, 1);
            String thirdDay = simpleDateFormat.format(cal2.getTime());

            boolean firstDayCond = true;
            boolean secondDayCond1 = true;
            boolean secondDayCond2 = true;
            boolean thirdDayCond1 = true;
            boolean thirdDayCond2 = true;

            for (Object jo : jsonArray) {
                if (((JSONObject) jo).get("fcstDate").toString().equals(firstDay)
                        && ((JSONObject) jo).get("category").toString().equals("TMP") && firstDayCond) {
                    String temperature = ((JSONObject) jo).get("fcstValue").toString();
                    firstDayWeatherDTO.setNowtemp(temperature);
                    firstDayCond = false;
                } else if (((JSONObject) jo).get("fcstDate").toString().equals(secondDay)
                        && ((JSONObject) jo).get("category").toString().equals("TMX") && secondDayCond1) {
                    String temperature = String.valueOf(Integer.parseInt(String.valueOf(Math.round(Double.valueOf(((JSONObject) jo).get("fcstValue").toString())))));
                    secondDayWeatherDTO.setMaxtemp(temperature);
                    secondDayCond1 = false;
                } else if (((JSONObject) jo).get("fcstDate").toString().equals(secondDay)
                        && ((JSONObject) jo).get("category").toString().equals("TMN") && secondDayCond2) {
                    String temperature = String.valueOf(Integer.parseInt(String.valueOf(Math.round(Double.valueOf(((JSONObject) jo).get("fcstValue").toString())))));
                    secondDayWeatherDTO.setMintemp(temperature);
                    secondDayCond2 = false;
                } else if (((JSONObject) jo).get("fcstDate").toString().equals(thirdDay)
                        && ((JSONObject) jo).get("category").toString().equals("TMX") && thirdDayCond1) {
                    String temperature = String.valueOf(Integer.parseInt(String.valueOf(Math.round(Double.valueOf(((JSONObject) jo).get("fcstValue").toString())))));
                    thirdDayWeatherDTO.setMaxtemp(temperature);
                    thirdDayCond1 = false;
                } else if (((JSONObject) jo).get("fcstDate").toString().equals(thirdDay)
                        && ((JSONObject) jo).get("category").toString().equals("TMN") && thirdDayCond2) {
                    String temperature = String.valueOf(Integer.parseInt(String.valueOf(Math.round(Double.valueOf(((JSONObject) jo).get("fcstValue").toString())))));
                    thirdDayWeatherDTO.setMintemp(temperature);
                    thirdDayCond2 = false;
                }
                if (!firstDayCond && !secondDayCond1 && !secondDayCond2 && !thirdDayCond1 && !thirdDayCond2) break;
            }
            jsonReader2.close();
            conn2.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSubPreTemperature2(URL url3) {
        // 기온 //

        try {
            System.out.println("HERE TEMPER2");
            System.out.println(url3.toString());
            HttpURLConnection conn3 = null;
            conn3 = (HttpURLConnection) url3.openConnection();
            conn3.setRequestMethod("GET");
            conn3.setRequestProperty("Content-type", "application/json");

            BufferedReader jsonReader3;
            JSONParser jsonParser3 = new JSONParser();

            if (conn3.getResponseCode() >= 200 && conn3.getResponseCode() <= 300) {
                jsonReader3 = new BufferedReader(new InputStreamReader(conn3.getInputStream()));
            } else {
                jsonReader3 = new BufferedReader(new InputStreamReader(conn3.getErrorStream()));
            }

            StringBuilder temp3 = new StringBuilder();
            String line3;
            while ((line3 = jsonReader3.readLine()) != null) {
                temp3.append(line3);
            }
            String result3 = temp3.toString();

            JSONObject jsonObject3 = (JSONObject) jsonParser3.parse(result3);
            JSONObject jsonResponse2 = (JSONObject) jsonObject3.get("response");
            JSONObject jsonBody2 = (JSONObject) jsonResponse2.get("body");
            JSONObject jsonItems2 = (JSONObject) jsonBody2.get("items");
            JSONArray jsonArray2 = (JSONArray) jsonItems2.get("item");

            JSONObject preTemper = (JSONObject) jsonArray2.get(0);

            fourthDayWeatherDTO.setMaxtemp(preTemper.get("taMax3").toString());
            fourthDayWeatherDTO.setMintemp(preTemper.get("taMin3").toString());
            fifthDayWeatherDTO.setMaxtemp(preTemper.get("taMax4").toString());
            fifthDayWeatherDTO.setMintemp(preTemper.get("taMin4").toString());
            sixthDayWeatherDTO.setMaxtemp(preTemper.get("taMax5").toString());
            sixthDayWeatherDTO.setMintemp(preTemper.get("taMin5").toString());
            seventhDayWeatherDTO.setMaxtemp(preTemper.get("taMax6").toString());
            seventhDayWeatherDTO.setMintemp(preTemper.get("taMin6").toString());

            jsonReader3.close();
            conn3.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e); }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }

    @GetMapping("/getPreWeatherFirstDay")
    public FirstDayWeatherDTO getFirstDayWeatherDTO(@RequestParam("obscode") String obscode) {
        return firstDayWeatherService.getFirstDayWeather(obscode);
    }

    @GetMapping("/getPreWeatherOtherDay")
    public List<OtherDayWeatherDTO> getOtherDayWeatherDTO(@RequestParam("obscode") String obscode) {
        List<OtherDayWeatherDTO> otherDayWeatherDTOS = new ArrayList<>();
        otherDayWeatherDTOS.add(secondDayWeatherService.getSecondDayWeather(obscode));
        otherDayWeatherDTOS.add(thirdDayWeatherService.getThirdDayWeather(obscode));
        otherDayWeatherDTOS.add(fourthDayWeatherService.getFourthDayWeather(obscode));
        otherDayWeatherDTOS.add(fifthDayWeatherService.getFifthDayWeather(obscode));
        otherDayWeatherDTOS.add(sixthDayWeatherService.getSixthDayWeather(obscode));
        otherDayWeatherDTOS.add(seventhDayWeatherService.getSeventhDayWeather(obscode));
        return otherDayWeatherDTOS;
    }
}