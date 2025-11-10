package be.pxl.services.domain.dto;
// Velden die de department-service nodig heeft
public record EmployeeDTO(
        Long id,
        String name,
        String position
) { }
