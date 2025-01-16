package com.beyond.basic.b2_board.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberUpdateDto {
    private String email;
    private String newPassword;
}