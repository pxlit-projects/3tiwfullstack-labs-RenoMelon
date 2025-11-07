package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.*;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")

public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;


}
