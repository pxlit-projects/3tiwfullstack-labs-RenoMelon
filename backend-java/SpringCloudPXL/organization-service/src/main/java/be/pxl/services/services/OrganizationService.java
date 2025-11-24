package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.*;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final RestTemplate restTemplate;
    private final static Logger log = LoggerFactory.getLogger(OrganizationService.class);

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        log.info("Fetching all organizations from database...");
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(organization -> mapToOrganizationResponse(organization)).toList();
    }

    @Override
    public Optional<OrganizationResponse> findOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .map(this::mapToOrganizationResponse);
    }

    // Hier verder gaan (Lab bijna klaar)
    @Override
    public OrganizationWithDepartmentsResponse findByIdWithDepartments(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        String deptUrl = "http://localhost:8082/api/department/organization/" + organization.getId();

        DepartmentDTO[] departmentsArray = restTemplate.getForObject(deptUrl, DepartmentDTO[].class);
        List<DepartmentDTO> departments = (departmentsArray != null) ? Arrays.asList(departmentsArray) : List.of();

        return new OrganizationWithDepartmentsResponse(
                organization.getId(),
                organization.getName(),
                departments
        );

    }

    @Override
    public OrganizationWithEmployeesResponse findByIdWithEmployees(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        String empUrl = "http://localhost:8081/api/employee/organization/" + organization.getId();
        EmployeeDTO[] employeesArray = restTemplate.getForObject(empUrl, EmployeeDTO[].class);
        List<EmployeeDTO> employees = (employeesArray != null) ? Arrays.asList(employeesArray) : List.of();

        return new OrganizationWithEmployeesResponse(organization.getId(), organization.getName(), employees);
    }

    @Override
    public OrganizationWithDepartmentsAndEmployeesResponse findByIdWithDepartmentsAndEmployees(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        String url = "http://localhost:8082/api/department/organization/" + organization.getId() + "/with-employees";
        DepartmentWithEmployeesDTO[] deptsWithEmpsArray = restTemplate.getForObject(url, DepartmentWithEmployeesDTO[].class);
        List<DepartmentWithEmployeesDTO> departmentsWithEmployees = (deptsWithEmpsArray != null) ? Arrays.asList(deptsWithEmpsArray) : List.of();

        return new OrganizationWithDepartmentsAndEmployeesResponse(organization.getId(), organization.getName(), departmentsWithEmployees);

    }


    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .address(organization.getAddress())
                .name(organization.getName())
                .departmentIds(organization.getDepartmentIds())
                .employeeIds(organization.getEmployeeIds())
                .build();

    }
}
