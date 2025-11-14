package be.pxl.services.services;


import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
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
public class DepartmentDataInitializer implements ApplicationRunner {

    private final DepartmentRepository departmentRepository;
    private static final Logger log = LoggerFactory.getLogger(DepartmentDataInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (departmentRepository.count() == 0) {
            log.info("Database is empty. Initializing department data...");

            Department dept1 = Department.builder()
                    .organizationId(1L)
                    .name("Engineering")
                    .employeeIds(List.of(1L, 2L)) // Ada & Grace
                    .build();

            Department dept2 = Department.builder()
                    .organizationId(1L)
                    .name("Human Resources")
                    .employeeIds(List.of(3L)) // Richard
                    .build();

            departmentRepository.saveAll(List.of(dept1, dept2));
            log.info("Finished initializing department data.");
        }
    }
}
