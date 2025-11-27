package br.com.oliveirawillian.integrationtests.controllers.cors.withjson;

import br.com.oliveirawillian.config.TestConfigs;
import br.com.oliveirawillian.integrationtests.dto.BooksDTO;
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

import java.sql.Date;
import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BooksControllerCorsTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static BooksDTO booksDTO;
    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        booksDTO = new BooksDTO();


    }



    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(booksDTO)
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
        mockBooks();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(booksDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        BooksDTO createdBooksDTO = objectMapper.readValue(content, BooksDTO.class);
        booksDTO = createdBooksDTO;

        assertNotNull(createdBooksDTO.getId());
        assertNotNull(createdBooksDTO.getAuthor());
        assertNotNull(createdBooksDTO.getTitle());
        assertNotNull(createdBooksDTO.getLaunchDate());
        assertNotNull(createdBooksDTO.getPrice());
        assertTrue(createdBooksDTO.getId() > 0);

        assertEquals("Willian Oliveira",createdBooksDTO.getAuthor());
        assertEquals("Java a Alma da Progamação",createdBooksDTO.getTitle());
        assertEquals(100.00,createdBooksDTO.getPrice());
        assertEquals(Date.valueOf("2025-11-15"),createdBooksDTO.getLaunchDate());



    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", booksDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BooksDTO createdBooksDTO = objectMapper.readValue(content, BooksDTO.class);
        booksDTO = createdBooksDTO;

        assertNotNull(createdBooksDTO.getId());
        assertNotNull(createdBooksDTO.getAuthor());
        assertNotNull(createdBooksDTO.getTitle());
        assertNotNull(createdBooksDTO.getLaunchDate());
        assertNotNull(createdBooksDTO.getPrice());
        assertTrue(createdBooksDTO.getId() > 0);

        assertEquals("Willian Oliveira",createdBooksDTO.getAuthor());
        assertEquals("Java a Alma da Progamação",createdBooksDTO.getTitle());
        assertEquals(100.00,createdBooksDTO.getPrice());
        assertNotNull(createdBooksDTO.getLaunchDate());

    }
  @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", booksDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

      assertEquals("Invalid CORS request",content);
    }


    private void mockBooks() {
        booksDTO.setAuthor("Willian Oliveira");
        booksDTO.setTitle("Java a Alma da Progamação");
        booksDTO.setLaunchDate(Date.valueOf("2025-11-15"));
        booksDTO.setPrice(100.00);

    }
}
