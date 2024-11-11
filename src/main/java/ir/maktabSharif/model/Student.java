package ir.maktabSharif.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@NamedQuery(name="Student.findByFirstname",query = "FROM Student where firstName=?1")

public class Student extends BaseModel {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name ="national_code",unique = true)
    private String nationalCode;
    @Enumerated
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date dob;
    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "j_student_course"
            ,joinColumns ={@JoinColumn(name ="fk_student")}
            ,inverseJoinColumns = {@JoinColumn(name = "fk_course")}
    )
    private List<Course> courseList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "j_student_exam",
            joinColumns = {@JoinColumn(name = "fk_student")},
            inverseJoinColumns = {@JoinColumn(name = "fk_exam")}
    )
    private List<Exam> examList = new ArrayList<>();
}
