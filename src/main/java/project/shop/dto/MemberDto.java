package project.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private String name;
    private String email;
    private String picture;
    private String nickName;

    @Builder
    public MemberDto(String name, String email, String picture, String nickName) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nickName = nickName;
    }
}
