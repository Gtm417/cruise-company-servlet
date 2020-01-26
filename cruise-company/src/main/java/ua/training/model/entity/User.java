package ua.training.model.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private long id;
    private String login;
    private String password;

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    private ROLE role;
}
