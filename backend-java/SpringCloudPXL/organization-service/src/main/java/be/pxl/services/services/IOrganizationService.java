package be.pxl.services.services;

import be.pxl.services.domain.dto.*;

import java.util.List;
import java.util.Optional;

public interface IOrganizationService {

    List<OrganizationResponse> getAllOrganizations();

    Optional<OrganizationResponse> findOrganizationById(Long id);
    // Definieer hier de response DTOs die je gaat gebruiken
    OrganizationWithDepartmentsResponse findByIdWithDepartments(Long id);
    OrganizationWithEmployeesResponse findByIdWithEmployees(Long id);
    OrganizationWithDepartmentsAndEmployeesResponse findByIdWithDepartmentsAndEmployees(Long id);

}
