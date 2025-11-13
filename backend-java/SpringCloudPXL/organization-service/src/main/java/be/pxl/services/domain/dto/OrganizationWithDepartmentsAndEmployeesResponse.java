package be.pxl.services.domain.dto;

import java.util.List;

public record OrganizationWithDepartmentsAndEmployeesResponse(Long id, String name, List<DepartmentWithEmployeesDTO> departments) {
}
