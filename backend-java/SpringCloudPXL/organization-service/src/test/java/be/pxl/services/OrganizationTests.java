// In: organization-service/src/test/java/be/pxl/services/OrganizationTests.java
package be.pxl.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrganizationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Container
    private static final MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        organizationRepository.deleteAll();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    @Transactional
    void shouldFindOrganizationByIdWhenExists() throws Exception {

        Organization savedOrg = organizationRepository.save(Organization.builder()
                .name("Pixel Dynamics Inc.")
                .address("123 Binary Lane")
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + savedOrg.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pixel Dynamics Inc.")))
                .andExpect(jsonPath("$.address", is("123 Binary Lane")));
    }

    @Test
    @Transactional
    void shouldFindOrganizationWithDepartmentsAndEmployees() throws Exception {

        Organization savedOrg = organizationRepository.save(Organization.builder()
                .name("Pixel Dynamics Inc.")
                .address("123 Binary Lane")
                .build());

        String expectedUrl = "http://localhost:8082/api/department/organization/" + savedOrg.getId() + "/with-employees";

        String mockDepartmentResponse = """
        [
            {
                "id": 1,
                "name": "Test R&D",
                "employees": [
                    { "id": 101, "name": "Fake Coder", "position": "Developer" },
                    { "id": 102, "name": "Fake Tester", "position": "QA Engineer" }
                ]
            }
        ]
        """;

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockDepartmentResponse, MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + savedOrg.getId() + "/with-departments-and-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pixel Dynamics Inc.")))
                .andExpect(jsonPath("$.departments.length()", is(1)))
                .andExpect(jsonPath("$.departments[0].name", is("Test R&D")))
                .andExpect(jsonPath("$.departments[0].employees.length()", is(2)))
                .andExpect(jsonPath("$.departments[0].employees[0].name", is("Fake Coder")));

        mockServer.verify();
    }

}