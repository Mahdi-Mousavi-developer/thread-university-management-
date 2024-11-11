package ir.maktabSharif.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQuery(name="Teacher.findByFirstname",query = "FROM Teacher where firstName=?1")

public class Teacher extends BaseModel {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "national_code", unique = true)
    private String nationalCode;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Enumerated
    private Gender gender;
    @Embedded
    private Address address;
    @OneToOne
    @JoinColumn(name = "fk_course")
    private Course course;
}
