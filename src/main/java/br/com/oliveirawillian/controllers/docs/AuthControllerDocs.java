package br.com.oliveirawillian.controllers.docs;

import br.com.oliveirawillian.data.dto.v1.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthControllerDocs {
    @Operation(summary = "Authenticates an user and returns a token")
    ResponseEntity<?> signin( AccountCredentialsDTO accountCredentialsDTO) throws Exception;

    @Operation(summary = "Refresh token for authenticated use and returns a token")
    ResponseEntity<?> refreshToken( String userName,  String refreshToken) throws Exception;

    @Operation(summary = "Create authenticated use and returns a token")
    AccountCredentialsDTO create( AccountCredentialsDTO credentialsDTO);
}
