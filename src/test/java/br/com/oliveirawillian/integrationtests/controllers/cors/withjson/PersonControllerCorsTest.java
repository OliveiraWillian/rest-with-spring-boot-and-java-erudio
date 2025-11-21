package br.com.oliveirawillian.integrationtests.controllers.cors.withjson;

import br.com.oliveirawillian.config.TestConfigs;
import br.com.oliveirawillian.integrationtests.dto.PersonDTO;
import br.com.oliveirawillian.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerCorsTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;
    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDTO = new PersonDTO();


    }



    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(403)
                .extract()
                    .body()
                        .asString();

        assertEquals("Invalid CORS request",content);



    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertNotNull(createdPersonDTO.getFirstName());
        assertNotNull(createdPersonDTO.getLastName());
        assertNotNull(createdPersonDTO.getGender());
        assertNotNull(createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Richard",createdPersonDTO.getFirstName());
        assertEquals("Stallman",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("new work city",createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getEnabled());



    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertNotNull(createdPersonDTO.getFirstName());
        assertNotNull(createdPersonDTO.getLastName());
        assertNotNull(createdPersonDTO.getGender());
        assertNotNull(createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Richard",createdPersonDTO.getFirstName());
        assertEquals("Stallman",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("new work city",createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getEnabled());
    }
  @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

      assertEquals("Invalid CORS request",content);
    }


    private void mockPerson() {
        personDTO.setFirstName("Richard");
        personDTO.setLastName("Stallman");
        personDTO.setAddress("new work city");
        personDTO.setGender("Male");
        personDTO.setEnabled(true);
    }
}
