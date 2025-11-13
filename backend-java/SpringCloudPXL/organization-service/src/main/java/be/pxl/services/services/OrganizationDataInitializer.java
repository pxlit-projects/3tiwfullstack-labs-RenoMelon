package be.pxl.services.services;
import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationDataInitializer implements ApplicationRunner {

    private final OrganizationRepository organizationRepository;
    private static final Logger log = LoggerFactory.getLogger(OrganizationDataInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (organizationRepository.count() == 0) {
            log.info("Database is empty. Initializing organization data...");

            Organization org1 = Organization.builder()
                    .name("Pixel Dynamics Inc.")
                    .address("123 Binary Lane, Techville")
                    .departmentIds(List.of(1L, 2L))
                    .employeeIds(List.of(1L, 2L, 3L))
                    .build();

            organizationRepository.save(org1);
            log.info("Finished initializing organization data.");
        }
    }
}

