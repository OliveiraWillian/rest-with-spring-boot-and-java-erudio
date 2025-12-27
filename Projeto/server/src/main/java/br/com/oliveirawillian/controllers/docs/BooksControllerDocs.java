package br.com.oliveirawillian.controllers.docs;

import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.data.dto.v1.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BooksControllerDocs {


    @Operation(summary = "Find All Books", description = "Finds All Books", tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BooksDTO.class)))}),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<BooksDTO>>> findAll(
            @RequestParam(value =  "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "size", defaultValue = "12") Integer size ,
            @RequestParam(value =  "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Find Book by Title", description = "Find Book by their title", tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))}),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<BooksDTO>>> findByTitle(
            @PathVariable("title") String title,
            @RequestParam(value =  "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "size", defaultValue = "12") Integer size ,
            @RequestParam(value =  "direction", defaultValue = "asc") String direction
    );


    @Operation(summary = "Find a Books", description = "Finds specific Books by your ID", tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BooksDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    BooksDTO findById(@PathVariable("id") Long id);


    @Operation(summary = "Create a Books", description = "Create Books", tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BooksDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    BooksDTO create(@RequestBody BooksDTO person);


    @Operation(summary = "Update a Books", description = "Update specific Books", tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BooksDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    BooksDTO update(@RequestBody BooksDTO person);



    @Operation(summary = "Delete a Books", description = "Delete specific Books by your ID", tags = {"Books"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BooksDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "No Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    })
    ResponseEntity<?> delete(@PathVariable("id") Long id);
}
