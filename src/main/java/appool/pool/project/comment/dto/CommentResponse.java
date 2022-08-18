package appool.pool.project.comment.dto;

import appool.pool.project.brand_user.dto.BrandUserInfoDto;
import appool.pool.project.comment.Comment;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.dto.UserInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String body;
    private UserInfoDto writer;
    private LocalDateTime create_date;

    public CommentResponse(Comment comment) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .username(comment.getWriter().getUsername())
                .nickName(comment.getWriter().getNickName())
                .userStatus(comment.getWriter().getUserStatus())
                .brandUserInfoDto(BrandUserInfoDto.builder().build())
                .build();
        this.id = comment.getId();
        this.body = comment.getBody();
        this.writer = userInfoDto;
        this.create_date = comment.getCreateDate();
    }

}
