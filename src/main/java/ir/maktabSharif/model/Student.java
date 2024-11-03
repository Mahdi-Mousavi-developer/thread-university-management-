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
public class Student extends BaseModel {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name ="national_code",unique = true)
    private String nationalCode;

    @Temporal(TemporalType.DATE)
    private Date dob;

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
