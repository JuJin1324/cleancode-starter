package com.starter.cleancode.chapter3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// UserService.java
@Service
public class UserServiceOld {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 사용자 정보를 업데이트하는 메소드
    public User updateUserProfile(Long userId, UserProfileUpdateRequestDto requestDto) {
        // 1. 사용자 ID로 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));

        // 2. 이메일 변경 요청 시: 이메일 형식 검증 및 중복 확인
        if (requestDto.getEmail() != null && !requestDto.getEmail().equals(user.getEmail())) {
            if (!requestDto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new InvalidEmailFormatException("잘못된 이메일 형식입니다.");
            }
            if (userRepository.existsByEmail(requestDto.getEmail())) {
                throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
            }
            user.setEmail(requestDto.getEmail());
            System.out.println("사용자 ID " + userId + "의 이메일이 " + requestDto.getEmail() + "로 변경되었습니다.");
        }

        // 3. 비밀번호 변경 요청 시: 새 비밀번호 유효성 검사 및 암호화하여 설정
        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {
            if (requestDto.getNewPassword().length() < 8) {
                throw new InvalidPasswordException("비밀번호는 8자 이상이어야 합니다.");
            }
            // 여기에 더 복잡한 비밀번호 정책 검사가 들어갈 수 있음 (예: 대소문자, 숫자, 특수문자 포함)
            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
            System.out.println("사용자 ID " + userId + "의 비밀번호가 변경되었습니다.");
        }

        // 4. 주소 변경 요청 시: 주소 정보 업데이트
        if (requestDto.getAddress() != null) {
            user.setAddress(requestDto.getAddress());
            System.out.println("사용자 ID " + userId + "의 주소가 변경되었습니다.");
        }

        // 5. 변경된 사용자 정보 저장
        User updatedUser = userRepository.save(user);
        System.out.println("사용자 ID " + userId + "의 프로필 정보가 성공적으로 업데이트되었습니다.");

        return updatedUser;
    }
}

