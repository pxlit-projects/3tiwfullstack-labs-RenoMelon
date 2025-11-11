package be.pxl.services.domain.dto;

import java.util.List;

public record DepartmentDTO(Long id, String name, List<Long> employeeIds) {
}
