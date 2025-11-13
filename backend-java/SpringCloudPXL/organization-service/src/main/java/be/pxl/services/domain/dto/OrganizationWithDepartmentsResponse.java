package be.pxl.services.domain.dto;

import java.util.List;

public record OrganizationWithDepartmentsResponse(Long id, String name, List<DepartmentDTO> departments) {
}
