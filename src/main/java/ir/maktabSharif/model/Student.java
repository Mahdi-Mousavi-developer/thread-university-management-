package ir.maktabSharif.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@NamedQuery(name="Student.findByFirstname",query = "FROM Student where firstName=?1")
public class Student extends Person {


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
