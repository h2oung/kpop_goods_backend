package com.example.kpop_goods_backend.controller;

import com.example.kpop_goods_backend.entity.User;
import com.example.kpop_goods_backend.repository.UserRepository;
import com.example.kpop_goods_backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/api/mypage")
    public ResponseEntity<Map<String, Object>> getMyPage(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String kakaoId = jwtService.validateTokenAndGetSubject(token);

            Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);
            if (userOpt.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "nickname", userOpt.get().getNickname()
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "status", "error",
                        "message", "사용자를 찾을 수 없습니다."
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "토큰이 유효하지 않습니다."
            ));
        }
    }
}
