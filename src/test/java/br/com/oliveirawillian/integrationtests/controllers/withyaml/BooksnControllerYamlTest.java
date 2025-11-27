package br.com.oliveirawillian.integrationtests.controllers.withyaml;

import br.com.oliveirawillian.config.TestConfigs;
import br.com.oliveirawillian.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.oliveirawillian.integrationtests.dto.AccountCredentialsDTO;
import br.com.oliveirawillian.integrationtests.dto.BooksDTO;
import br.com.oliveirawillian.integrationtests.dto.TokenDTO;
import br.com.oliveirawillian.integrationtests.dto.wrappers.xml.PageModelBooks;
import br.com.oliveirawillian.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.sql.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BooksnControllerYamlTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;
    private static BooksDTO booksDTO;
    private static TokenDTO tokenDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
        tokenDTO = new TokenDTO();

        booksDTO = new BooksDTO();


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


    }



    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBooks();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION,"Bearer " + tokenDTO.getAccessToken())

                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var createdBooksDTO = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(booksDTO,objectMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(BooksDTO.class, objectMapper);

        booksDTO = createdBooksDTO;

        assertNotNull(createdBooksDTO.getId());
        assertTrue(createdBooksDTO.getId() > 0);

        assertEquals("Willian Oliveira",createdBooksDTO.getAuthor());
        assertEquals("Java a Alma da Progamação",createdBooksDTO.getTitle());
        assertEquals(100.00,createdBooksDTO.getPrice());
        assertEquals(Date.valueOf("2025-11-15"),createdBooksDTO.getLaunchDate());




    }
    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        booksDTO.setAuthor("Benedict Torvalds");


        var createdBooksDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(booksDTO,objectMapper)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                .as(BooksDTO.class, objectMapper);

        booksDTO = createdBooksDTO;

        assertNotNull(createdBooksDTO.getId());
        assertTrue(createdBooksDTO.getId() > 0);

        assertEquals("Benedict Torvalds",createdBooksDTO.getAuthor());
        assertEquals("Java a Alma da Progamação",createdBooksDTO.getTitle());
        assertEquals(100.00,createdBooksDTO.getPrice());
        assertEquals(Date.valueOf("2025-11-15"),createdBooksDTO.getLaunchDate());




    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {



        var createdBooksDTO = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )

                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", booksDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BooksDTO.class, objectMapper);

        booksDTO = createdBooksDTO;

        assertNotNull(createdBooksDTO.getId());
        assertTrue(createdBooksDTO.getId() > 0);


        assertEquals("Benedict Torvalds",createdBooksDTO.getAuthor());
        assertEquals("Java a Alma da Progamação",createdBooksDTO.getTitle());
        assertEquals(100.00,createdBooksDTO.getPrice());
        assertNotNull(createdBooksDTO.getLaunchDate());
    }



    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {
                given(specification)
                .pathParam("id", booksDTO.getId())
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
                .queryParam("page", 0,"size",12,"direction","asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PageModelBooks.class, objectMapper);
        List<BooksDTO> books =  response.getContent();
        BooksDTO booksOne = books.get(0);

        assertNotNull(booksOne.getId());
        assertTrue(booksOne.getId() > 0);

        assertEquals("Craig Larman",booksOne.getAuthor());
        assertEquals("Craig Larman",booksOne.getAuthor());
        assertEquals("Agile and Iterative Development: A Manager’s Guide",booksOne.getTitle());
        assertEquals(56.47,booksOne.getPrice());
        assertNotNull(booksOne.getLaunchDate());



        //BooksDTO booksFour = books.stream().filter(p -> p.getId().equals(4)).findFirst().orElseThrow(() -> new RuntimeException("Books with ID no Found"));



        BooksDTO booksFour = books.get(4);
        assertNotNull(booksFour.getId());
        assertTrue(booksFour.getId() > 0);

        assertEquals("Craig Larman",booksFour.getAuthor());
        assertEquals("Agile and Iterative Development: A Manager’s Guide",booksFour.getTitle());
        assertEquals(43.82,booksFour.getPrice());
        assertNotNull(booksFour.getLaunchDate());
    }




    private void mockBooks() {
        booksDTO.setAuthor("Willian Oliveira");
        booksDTO.setTitle("Java a Alma da Progamação");
        booksDTO.setLaunchDate(Date.valueOf("2025-11-15"));
        booksDTO.setPrice(100.00);

    }
}
