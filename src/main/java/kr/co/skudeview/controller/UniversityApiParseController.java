package kr.co.skudeview.controller;

import kr.co.skudeview.domain.University;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;


import kr.co.skudeview.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UniversityApiParseController {

    private final UniversityRepository universityRepository;

    @GetMapping("/api/load")
    public ResponseFormat<Void> loadJsonFromApi() {
        List<University> universities = new ArrayList<>();
        long start = System.nanoTime();
        String result = "";
        for (int j = 1; j <= 53; j++) {
            try {
                System.out.println("j ============================================================ " + j);
                URL url = new URL("https://api.odcloud.kr/api/15014632/v1/uddi:6939f45b-1283-4462-b394-820c26e1445d?page=" + j + "&perPage=1000&serviceKey=7ZwOtbHTUQk2pJsydUaqKYShLVfbpH%2FtsehWIdHMi3hnTdkGQFXd1cH%2FPC8NHzqArTxWnA5%2FML3f3%2FjDcFKBTw%3D%3D");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoInput(true);


                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = br.readLine();

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");


                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    String univName = (String) object.get("학교명");
                    String major = (String) object.get("학부_과(전공)명");
                    University university = new University(univName, major);
                    universities.add(university);
                }


            } catch (Exception e) {
                log.info("log = {}", e.getMessage());
                return ResponseFormat.error(ResponseStatus.FAIL_BAD_REQUEST);
            }
        }
        universityRepository.saveAll(universities);
        long end = System.nanoTime();
        System.out.println("수행시간: " + (end - start) + " ns");
        System.out.println("universities.size = " + universities.size());
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}

