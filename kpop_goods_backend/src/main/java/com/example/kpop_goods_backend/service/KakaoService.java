package com.example.kpop_goods_backend.service;

import com.example.kpop_goods_backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String kakaoLogin(String code) {
        String accessToken = getAccessToken(code);
        JSONObject userInfo = getUserInfo(accessToken);

        String kakaoId = String.valueOf(userInfo.getLong("id"));
        String nickname = userInfo.getJSONObject("properties").getString("nickname");

        User user = userService.saveOrUpdateUser(kakaoId, nickname);

        return jwtService.generateToken(kakaoId);
    }

    public String getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = UriComponentsBuilder.newInstance()
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build()
                .toString()
                .substring(1);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = new JSONObject(response.getBody());

        return json.getString("access_token");
    }

    public JSONObject getUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return new JSONObject(response.getBody());
    }
}
