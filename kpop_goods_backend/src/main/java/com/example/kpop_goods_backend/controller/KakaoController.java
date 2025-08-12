package com.example.kpop_goods_backend.controller;

import com.example.kpop_goods_backend.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/api/login/kakao")
    public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestParam("code") String code) {
        try {
            String jwtToken = kakaoService.kakaoLogin(code);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "로그인 성공",
                    "token", jwtToken,
                    "redirectUrl", "http://localhost:3000/login-success"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "로그인 실패: " + e.getMessage()
            ));
        }
    }
}
