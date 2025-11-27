package br.com.oliveirawillian.integrationtests.controllers.withxml;

import br.com.oliveirawillian.config.TestConfigs;
import br.com.oliveirawillian.integrationtests.dto.PersonDTO;
import br.com.oliveirawillian.integrationtests.dto.wrappers.xml.PageModelPerson;
import br.com.oliveirawillian.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static XmlMapper objectMapper;
    private static PersonDTO personDTO;
    @BeforeAll
    static void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDTO = new PersonDTO();


    }





    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Linus",createdPersonDTO.getFirstName());
        assertEquals("Torvalds",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("Helsinki - Finland",createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getEnabled());



    }
    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        personDTO.setLastName("Benedict Torvalds");


        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(personDTO)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Linus",createdPersonDTO.getFirstName());
        assertEquals("Benedict Torvalds",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("Helsinki - Finland",createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getEnabled());



    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {



        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Linus",createdPersonDTO.getFirstName());
        assertEquals("Benedict Torvalds",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("Helsinki - Finland",createdPersonDTO.getAddress());
        assertTrue(createdPersonDTO.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {



        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;

        assertNotNull(createdPersonDTO.getId());
        assertTrue(createdPersonDTO.getId() > 0);

        assertEquals("Linus",createdPersonDTO.getFirstName());
        assertEquals("Benedict Torvalds",createdPersonDTO.getLastName());
        assertEquals("Male",createdPersonDTO.getGender());
        assertEquals("Helsinki - Finland",createdPersonDTO.getAddress());
        assertFalse(createdPersonDTO.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {
        given(specification)
                .pathParam("id", personDTO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);


    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .queryParam("page", 3,"size",12,"direction","asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();
        PageModelPerson wrapper = objectMapper.readValue(content,PageModelPerson.class);
        List<PersonDTO> people =  wrapper.getContent();
        //PersonDTO personOne = people.stream().filter(p -> p.getId().equals(1)).findFirst().orElseThrow(() -> new RuntimeException("Person with ID no Found"));
        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allayne",personOne.getFirstName());
        assertEquals("Silveston",personOne.getLastName());
        assertEquals("Male",personOne.getGender());
        assertEquals("Suite 3",personOne.getAddress());
        assertTrue(personOne.getEnabled());

        //PersonDTO personFour = people.stream().filter(p -> p.getId().equals(4)).findFirst().orElseThrow(() -> new RuntimeException("Person with ID no Found"));


        PersonDTO personFour = people.get(4);
        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Almire",personFour.getFirstName());
        assertEquals("Tschursch",personFour.getLastName());
        assertEquals("Room 1877",personFour.getAddress());
        assertEquals("Female",personFour.getGender());
        assertTrue(personFour.getEnabled());
    }
    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("firstName", "and")
                .queryParam("page", 0,"size",12,"direction","asc")
                .when()
                .get("findPeopleByName/{firstName}")

                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();
        PageModelPerson wrapper = objectMapper.readValue(content,PageModelPerson.class);
        List<PersonDTO> people =  wrapper.getContent();
        //PersonDTO personOne = people.stream().filter(p -> p.getId().equals(1)).findFirst().orElseThrow(() -> new RuntimeException("Person with ID no Found"));
        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Alejandro",personOne.getFirstName());
        assertEquals("Stable",personOne.getLastName());
        assertEquals("Male",personOne.getGender());
        assertEquals("PO Box 59523",personOne.getAddress());
        assertTrue(personOne.getEnabled());

        //PersonDTO personFour = people.stream().filter(p -> p.getId().equals(4)).findFirst().orElseThrow(() -> new RuntimeException("Person with ID no Found"));


        PersonDTO personFour = people.get(4);
        assertNotNull(personFour.getId());
        assertTrue(personFour.getId() > 0);

        assertEquals("Bertrand",personFour.getFirstName());
        assertEquals("Hasney",personFour.getLastName());
        assertEquals("Apt 161",personFour.getAddress());
        assertEquals("Male",personFour.getGender());
        assertFalse(personFour.getEnabled());
    }




    private void mockPerson() {
        personDTO.setFirstName("Linus");
        personDTO.setLastName("Torvalds");
        personDTO.setAddress("Helsinki - Finland");
        personDTO.setGender("Male");
        personDTO.setEnabled(true);
    }
}
