package be.pxl.services.controller;

import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.domain.dto.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.services.domain.dto.OrganizationWithDepartmentsResponse;
import be.pxl.services.domain.dto.OrganizationWithEmployeesResponse;
import be.pxl.services.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/all")
    public ResponseEntity getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findOrganizationById(@PathVariable Long id){
        return organizationService.findOrganizationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/with-departments")
    public ResponseEntity<OrganizationWithDepartmentsResponse> findByIdWithDepartments(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.findByIdWithDepartments(id));
    }

    @GetMapping("/{id}/with-employees")
    public ResponseEntity<OrganizationWithEmployeesResponse> findByIdWithEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.findByIdWithEmployees(id));
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public ResponseEntity<OrganizationWithDepartmentsAndEmployeesResponse> findByIdWithDepartmentsAndEmployees(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.findByIdWithDepartmentsAndEmployees(id));
    }

}



