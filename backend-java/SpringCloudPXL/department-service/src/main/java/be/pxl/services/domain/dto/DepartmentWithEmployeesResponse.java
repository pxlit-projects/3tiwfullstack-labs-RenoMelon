package be.pxl.services.domain.dto;

import java.util.List;

public record DepartmentWithEmployeesResponse(
        Long id,
        String name,
        List<EmployeeDTO> employees
) {

}
