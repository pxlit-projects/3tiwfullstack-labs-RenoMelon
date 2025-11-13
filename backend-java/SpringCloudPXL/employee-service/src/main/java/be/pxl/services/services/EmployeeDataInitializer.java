package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class EmployeeDataInitializer implements ApplicationRunner {

    private final EmployeeRepository employeeRepository;
    private static final Logger log = LoggerFactory.getLogger(EmployeeDataInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (employeeRepository.count() == 0) {
            log.info("Database is empty. Initializing employee data...");

            Employee emp1 = Employee.builder()
                    .organizationId(1L)
                    .departmentId(1L)
                    .name("Ada Lovelace")
                    .age(36)
                    .position("Principal Engineer")
                    .build();

            Employee emp2 = Employee.builder()
                    .organizationId(1L)
                    .departmentId(1L)
                    .name("Grace Hopper")
                    .age(45)
                    .position("Lead Architect")
                    .build();

            Employee emp3 = Employee.builder()
                    .organizationId(1L)
                    .departmentId(2L)
                    .name("Richard Stallman")
                    .age(50)
                    .position("Chief Happiness Officer")
                    .build();

            employeeRepository.saveAll(List.of(emp1, emp2, emp3));
            log.info("Finished initializing employee data.");
        }
    }
}
