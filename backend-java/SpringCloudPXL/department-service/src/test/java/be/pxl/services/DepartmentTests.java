package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
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
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DepartmentTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        departmentRepository.deleteAll();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    @Transactional
    public void createDepartmentTest() throws Exception {
        List<Long> employeeIds = new ArrayList<>();
        employeeIds.add(1L);
        employeeIds.add(2L);

        Department department = Department.builder()
                .name("Testing Department")
                .employeeIds(employeeIds)
                .organizationId(1L)
                .position("")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentString))
                .andExpect(status().isCreated());

        List<Department> departments = departmentRepository.findAll();
        assertEquals(1, departments.size());


        Department savedDepartment = departments.get(0);
        assertEquals("Testing Department", savedDepartment.getName());
        assertEquals(2, savedDepartment.getEmployeeIds().size());
        assertEquals(1L, savedDepartment.getEmployeeIds().get(0));

    }

    @Test
    @Transactional
    public void shouldFindDepartmentsWithEmployeesByOrganization() throws Exception {

        Department dept = departmentRepository.save(Department.builder()
                .name("Test Engineering")
                .organizationId(1L)
                .employeeIds(List.of(101L, 102L))
                .build());

        String expectedEmployeeJson = """
        [
            { "id": 101, "name": "Fake Ada", "position": "Principal" },
            { "id": 102, "name": "Fake Grace", "position": "Architect" }
        ]
        """;

        mockServer.expect(requestTo("http://localhost:8081/api/employee?ids=101,102"))
                .andExpect(method(HttpMethod.GET))

                .andRespond(withSuccess(expectedEmployeeJson, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/1/with-employees"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Engineering")))
                .andExpect(jsonPath("$[0].employees.length()", is(2)))
                .andExpect(jsonPath("$[0].employees[0].name", is("Fake Ada")))
                .andExpect(jsonPath("$[0].employees[1].name", is("Fake Grace")));

        mockServer.verify();
    }


}


