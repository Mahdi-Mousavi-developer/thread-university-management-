package ir.maktabSharif.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam extends BaseModel {

    @Column(name = "exam_title")
    private String examTitle;
    private float score;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exam_date")
    private Date examDate;

    @ManyToOne
    @JoinColumn(name ="fk_course")
    private Course course;

    @ManyToMany(mappedBy = "examList")

    private List<Student> students =new ArrayList<>();

}
