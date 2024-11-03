package ir.maktabSharif.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends BaseModel {

    @Column(name = "course_title")
    private String title;

    private String unit;


    @ManyToMany(mappedBy = "courseList")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Exam> exams = new ArrayList<>();

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", unit='" + unit + '\'' +
                ", students=" + students +
                ", exams=" + exams +
                '}';
    }
}

