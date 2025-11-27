package br.com.oliveirawillian.integrationtests.controllers.withyaml;

import br.com.oliveirawillian.config.TestConfigs;
import br.com.oliveirawillian.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.oliveirawillian.integrationtests.dto.AccountCredentialsDTO;
import br.com.oliveirawillian.integrationtests.dto.PersonDTO;
import br.com.oliveirawillian.integrationtests.dto.TokenDTO;
import br.com.oliveirawillian.integrationtests.dto.wrappers.xml.PageModelPerson;
import br.com.oliveirawillian.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYamlTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;
    private static PersonDTO personDTO;
    private static TokenDTO tokenDTO;
    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
        tokenDTO = new TokenDTO();
        personDTO = new PersonDTO();


    }


    @Test
    @Order(0)
    void signin() throws JsonProcessingException {
        AccountCredentialsDTO accountCredentialsDTO = new AccountCredentialsDTO("leandro", "admin123");

        tokenDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )


                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(accountCredentialsDTO,objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(TokenDTO.class, objectMapper);



        assertNotNull(tokenDTO.getAccessToken());
        assertNotNull(tokenDTO.getRefreshToken());

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION,"Bearer " + tokenDTO.getAccessToken())
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }


    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();


        var createdPersonDTO = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(personDTO,objectMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(PersonDTO.class, objectMapper);

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


        var createdPersonDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(personDTO,objectMapper)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                .as(PersonDTO.class, objectMapper);

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



        var createdPersonDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);

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



        var createdPersonDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);

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

        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParam("page", 3,"size",12,"direction","asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PageModelPerson.class, objectMapper);
        List<PersonDTO> people =  response.getContent();
        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Allayne",personOne.getFirstName());
        assertEquals("Silveston",personOne.getLastName());
        assertEquals("Male",personOne.getGender());
        assertEquals("Suite 3",personOne.getAddress());
        assertTrue(personOne.getEnabled());


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

        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("firstName", "and")
                .queryParam("page", 0,"size",12,"direction","asc")

                .when()
                .get("findPeopleByName/{firstName}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PageModelPerson.class, objectMapper);
        List<PersonDTO> people =  response.getContent();
        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

    assertEquals("Alejandro",personOne.getFirstName());
    assertEquals("Stable",personOne.getLastName());
    assertEquals("Male",personOne.getGender());
    assertEquals("PO Box 59523",personOne.getAddress());
    assertTrue(personOne.getEnabled());


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
        personDTO.setProfileUrl("https://pub.erudio.com.br/meus-cursos");
        personDTO.setPhotoUrl("https://pub.erudio.com.br/meus-cursos");
    }
}
