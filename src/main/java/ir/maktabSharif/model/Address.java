package ir.maktabSharif.model;


import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
   private String country;
   private String city;
   private String street;
   private String zipCode;


}
