package ir.maktabSharif.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Teacher.findByFirstname",query = "FROM Teacher where firstName=?1")
@SuperBuilder
public class Teacher extends Person {


    @OneToOne
    @JoinColumn(name = "fk_course")
    private Course course;
}
