package com.jwt.demo.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record AuthResponse(String accessToken,String refreshToken) {

}
