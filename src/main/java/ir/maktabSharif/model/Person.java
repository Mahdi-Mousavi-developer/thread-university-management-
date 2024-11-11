package ir.maktabSharif.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="School_Person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends BaseModel{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name ="national_code")
    private String nationalCode;
    @Enumerated
    private Gender gender;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Embedded
    private Address address;
}
