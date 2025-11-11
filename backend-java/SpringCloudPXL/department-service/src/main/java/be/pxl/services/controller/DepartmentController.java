package be.pxl.services.controller;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;
import be.pxl.services.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDepartment(@RequestBody DepartmentRequest request) {
        departmentService.addDepartment(request);
    }

    @GetMapping("/{departmentId}/with-employees")
    public ResponseEntity<DepartmentWithEmployeesResponse> findDepartmentWithEmployees(@PathVariable Long departmentId){
        DepartmentWithEmployeesResponse response = departmentService.findDepartmentWithEmployees(departmentId);
        return ResponseEntity.ok(response);
    }

    // Lab oefeningen endpoints

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> findDepartmentById(@PathVariable Long id){
        return departmentService.findDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DepartmentResponse>> findDepartmentsByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(departmentService.findDepartmentsByOrganization(organizationId));
    }

    @GetMapping("organization/{organizationId}/with-employees")
    public ResponseEntity<List<DepartmentWithEmployeesResponse>> findDepartmentsByOrganizationWithEmployees(@PathVariable Long organizationId){
        return ResponseEntity.ok(departmentService.findDepartmentsByOrganizationWithEmployees(organizationId));
    }
}



