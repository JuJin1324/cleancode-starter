package com.starter.cleancode.chapter3;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    // 개선 1.final 키워드와 생성자를 통한 빈 주입 방식으로 변경
    // * 스프링 팀에서 공식적으로 추천하는 방식 사용.
    // * 이를 통해 서비스 내의 빈 주입된 객체들이 변화되지 않는 안정성 제공
    // * 단위 테스트 시에도 Mock 객체 주입이 Setter 대비 수월
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 사용자 정보를 업데이트하는 메소드
    public User updateUserProfile(Long userId, UserProfileUpdateRequestDto requestDto) {
        // 1. 사용자 ID로 사용자 조회
        // 개선 1.조회 메서드 분리: 여기서는 JPA 엔티티를 서비스에서 직접 사용하는 방식이기 때문에 조회 메서드 분리를 적용하였다.
        // 하지만 DDD 를 적용한 경우 JPA 엔티티를 직접 서비스에서 직접 사용하지 않기 때문에 분리한 조회 메서드는 
        // UserRepository 내에서 구현해야한다. 
        User user = findUserById(userId);

        // 2. 이메일 변경 요청 시: 이메일 형식 검증 및 중복 확인
        // 개선 2.Email 검증 로직 private 메서드로 분리
        if (requestDto.hasEmail()) {
            validateEmailFormat(requestDto.getEmail());
            // 개선 3.Repository(DB) 작업을 Format 검증 아래 둬서 불필요한 DB 조회를 줄인다.
            validateEmailAvailability(requestDto.getEmail(), userId);
            user.setEmail(requestDto.getEmail());
        }

        // 3. 비밀번호 변경 요청 시: 새 비밀번호 유효성 검사 및 암호화하여 설정
        // 개선 3.비밀번호 검증 로직 private 메서드로 분리
        if (requestDto.hasNewPassword()) {
            validatePasswordFormat(requestDto.getNewPassword());
            user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        }

        // 5. 변경된 사용자 정보 저장
        // 개선 4.JPA 는 @Transactional 애노테이션을 붙인 경우에 신규 엔티티 저장이 아닌
        // 기존 엔티티의 업데이트인 경우 굳이 repository.save 메서드를 호출할 필요없음.
        // 기존 엔티티는 수정이 일어나면 @Transactional 이 자동으로 flush 를 호출하여 DB에 반영함.
//        User updatedUser = userRepository.save(user);

        // 개선 4.dto 에 의미를 명확화하기 위한 hasAddress 메서드 추가
        if (requestDto.hasAddress()) {
            user.setAddress(requestDto.getAddress());
        }
        System.out.println("사용자 ID " + userId + "의 프로필 정보가 성공적으로 업데이트되었습니다.");

        return user;
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
    }

    private void validatePasswordFormat(String newPassword) {
        if (newPassword.length() < 8) {
            throw new InvalidPasswordException("비밀번호는 8자 이상이어야 합니다.");
        }
    }

    private void validateEmailFormat(String newEmail) {
        if (!newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidEmailFormatException("잘못된 이메일 형식입니다.");
        }
    }

    private void validateEmailAvailability(String email, Long userId) {
        if (userRepository.existsByEmailExceptForUserId(email, userId)) {
            throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
        }
    }
}

