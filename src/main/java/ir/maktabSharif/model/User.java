package ir.maktabSharif.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "userrs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@NamedQuery(name="User.findByUsernameAndPassword",query = "FROM User where username=?1 and password=?2")

public class User extends BaseModel {
    @Column(name ="user_name")
    private String username;

    private String password;

    @Column(name="user_role")
    private String userRole;
}
