package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<DepartmentResponse> getAllDepartments();

    void addDepartment(DepartmentRequest request);

    Optional<DepartmentResponse> findDepartmentById(Long id);

    List<DepartmentResponse> findDepartmentsByOrganization(Long organizationId);

    List<DepartmentWithEmployeesResponse> findDepartmentsByOrganizationWithEmployees(Long organizationId);
}
