package com.beyond.basic.b2_board.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MemberListRes {

    private Long id;
    private String name;
    private String email;
}
