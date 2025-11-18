package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.clientDto.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {


    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;



    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public void addEmployee(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .age(request.getAge())
                .name(request.getName())
                .position(request.getPosition())
                .build();
        employeeRepository.save(employee);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee " + employee.getName() + " has been created")
                .sender("Bart")
                .build();
        notificationClient.sendNotification(notificationRequest);
    }

    @Override
    public List<EmployeeResponse> findEmployeesByIds(List<Long> ids) {
        return employeeRepository.findAllById(ids)
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public Optional<EmployeeResponse> findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToEmployeeResponse);
    }

    @Override
    public List<EmployeeResponse> findEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> findEmployeesByOrganization(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId)
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }


    public EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();

    }
}
