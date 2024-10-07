package backend.travel.domain.user.entity;

import backend.travel.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isDelete;

    @Builder
    public User(Long id, String username, String password, String nickname, UserRole role, Boolean isDelete) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.isDelete = isDelete;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void delete(){
        this.isDelete = true;
    }
}
