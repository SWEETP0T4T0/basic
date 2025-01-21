package com.beyond.basic.b2_board.dtos;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCreateDto {
    @NotEmpty
    private String title;
    private String content;
    private Long MemberId;

    // 빌더 패턴은 엔티티명.builder().필드명(필드값).build();
    public Post toEntity(Member member) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .member(member)
                .build();
    }
}