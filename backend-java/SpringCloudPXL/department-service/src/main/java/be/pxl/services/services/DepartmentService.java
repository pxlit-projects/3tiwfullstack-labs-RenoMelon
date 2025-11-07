package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();

        //TODO change this to returning responses instead of the main domain model
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



    public DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .name(department.getName())
                .position(department.getPosition())
                .organizationId(department.getOrganizationId())
                .employeeIds(department.getEmployeeIds())
                .build();
    }
}
