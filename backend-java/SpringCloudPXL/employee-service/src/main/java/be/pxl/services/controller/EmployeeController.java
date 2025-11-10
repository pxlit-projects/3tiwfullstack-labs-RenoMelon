package be.pxl.services.controller;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.services.IEmployeeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {


    private final IEmployeeService employeeService;


    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity getEmployees(){
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequest request){
        employeeService.addEmployee(request);


    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> findEmployeesByIds(@RequestParam List<Long> ids){
        List<EmployeeResponse> employeeResponses = employeeService.findEmployeesByIds(ids);
        return ResponseEntity.ok().body(employeeResponses);
    }

    // Endpoints voor Lab

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable Long id){
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> findEmployeesByDepartment(@PathVariable Long departmentId){
        return ResponseEntity.ok(employeeService.findEmployeesByDepartment(departmentId));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<EmployeeResponse>> findEmployeesByOrganization(@PathVariable Long organizationId){
        return ResponseEntity.ok(employeeService.findEmployeesByOrganization(organizationId));
    }


}
