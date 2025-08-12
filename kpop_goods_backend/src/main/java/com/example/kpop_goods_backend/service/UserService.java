package com.example.kpop_goods_backend.service;

import com.example.kpop_goods_backend.entity.User;
import com.example.kpop_goods_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveOrUpdateUser(String kakaoId, String nickname) {
        Optional<User> existingUser = userRepository.findByKakaoId(kakaoId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNickname(nickname);
            return userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .build();
            return userRepository.save(newUser);
        }
    }
}
