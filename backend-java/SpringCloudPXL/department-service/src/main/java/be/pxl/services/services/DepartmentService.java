package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;
import be.pxl.services.domain.dto.EmployeeDTO;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();

    }

    @Override
    public void addDepartment(DepartmentRequest request) {
        Department department = Department.builder()
                .position(request.getPosition())
                .employeeIds(request.getEmployeeIds())
                .name(request.getName())
                .organizationId(request.getOrganizationId())
                .build();
        departmentRepository.save(department);
    }

    @Override
    public Optional<DepartmentResponse> findDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::mapToDepartmentResponse);
    }

    @Override
    public List<DepartmentResponse> findDepartmentsByOrganization(Long organizationId) {
        return departmentRepository.findDepartmentsByOrganizationId(organizationId)
                .stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    @Override
    public List<DepartmentWithEmployeesResponse> findDepartmentsByOrganizationWithEmployees(Long organizationId) {

        List<Department> departments = departmentRepository.findDepartmentsByOrganizationId(organizationId);

        // Verzamel alle employees van deze departementen in 1 grote lijst

        List<Long> allEmployeeIds = departments.stream()
                .flatMap(department -> department.getEmployeeIds().stream())
                .distinct()
                .toList();

        String employeeUrl = "http://localhost:8081/api/employee?ids=" + String.join(",",
                allEmployeeIds.stream().map(String::valueOf).toList());
        EmployeeDTO[] employeesArray = restTemplate.getForObject(employeeUrl, EmployeeDTO[].class);
        List<EmployeeDTO> employees = (employeesArray != null) ? Arrays.asList(employeesArray) : List.of();

        Map<Long, EmployeeDTO> employeeMap = employees.stream()
                .collect(Collectors.toMap(EmployeeDTO::id, employee -> employee));

        return departments.stream().map(department -> {
            List<EmployeeDTO> departmentEmployees = department.getEmployeeIds()
                    .stream()
                    .map(employeeMap::get)
                    .filter(java.util.Objects::nonNull)
                    .toList();
            return new DepartmentWithEmployeesResponse(department.getId(), department.getName(), departmentEmployees);
        }).toList();
    }

    public DepartmentWithEmployeesResponse findDepartmentWithEmployees(Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        List<Long> employeeIds = department.getEmployeeIds();

        if(employeeIds == null || employeeIds.isEmpty()){
            // Geen werknemers binnen het departement, dus we returnen meteen
            return new DepartmentWithEmployeesResponse(department.getId(), department.getName(), List.of());
        }

        String employeeUrl = "http://localhost:8081/api/employee?ids=" + String.join(",",
                employeeIds.stream().map(String::valueOf).toList());

        EmployeeDTO[] employees = restTemplate.getForObject(employeeUrl, EmployeeDTO[].class);

        return new DepartmentWithEmployeesResponse(
                department.getId(),
                department.getName(),
                (employees != null) ? Arrays.asList(employees) : List.of()

        );

    }


    public DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .name(department.getName())
                .position(department.getPosition())
                .organizationId(department.getOrganizationId())
                .employeeIds(department.getEmployeeIds())
                .build();
    }



}
