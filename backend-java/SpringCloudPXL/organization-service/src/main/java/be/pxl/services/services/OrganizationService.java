package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.*;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
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
        return null;
    }

    @Override
    public OrganizationWithEmployeesResponse findByIdWithEmployees(Long id) {
        return null;
    }

    @Override
    public OrganizationWithDepartmentsAndEmployeesResponse findByIdWithDepartmentsAndEmployees(Long id) {
        return null;
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
