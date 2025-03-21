package com.jwt.demo.jwt;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record AuthRequest(@NotEmpty String username, @NotEmpty String password) {

}
