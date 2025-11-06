package be.pxl.services.domain;

import jakarta.persistence.*;

@Entity
@Table(name="employee")

public class Employee {

    public Employee() {
    }

    public Employee(Long organizationId, Long departmentId, String name, int age, String position) {
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long organizationId;
    @Column(nullable = true)
    private Long departmentId;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true)
    private int age;
    @Column(nullable = true)
    private String position;

    public Long getId() {
        return id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
