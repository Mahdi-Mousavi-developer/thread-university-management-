package ir.maktabSharif.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userrs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseModel {
    @Column(name ="user_name")
    private String username;

    private String password;

    @Column(name="user_role")
    private String userRole;
}
