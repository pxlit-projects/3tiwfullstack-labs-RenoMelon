package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    List<EmployeeResponse> getAllEmployees();

    void addEmployee(EmployeeRequest request);

    List<EmployeeResponse> findEmployeesByIds(List<Long> ids);

    Optional<EmployeeResponse> findEmployeeById(Long id);

    List<EmployeeResponse> findEmployeesByDepartment(Long departmentId);

    List<EmployeeResponse> findEmployeesByOrganization(Long organizationId);


}


