package com.starter.cleancode.chapter3;

import lombok.AllArgsConstructor;
import lombok.Getter;

// UserProfileUpdateRequestDto.java (가상 DTO)
@AllArgsConstructor
@Getter
public class UserProfileUpdateRequestDto {
    private String email;
    private String newPassword;
    private String address;
    
    public boolean hasAddress() {
        return address != null && !address.isEmpty();
    }
    
    public boolean hasNewPassword() {
        return newPassword != null && !newPassword.isEmpty();
    }
    
    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }
}