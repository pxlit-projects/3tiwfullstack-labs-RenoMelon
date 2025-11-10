package be.pxl.services.controller;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;
import be.pxl.services.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
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
}



