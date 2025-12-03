package br.com.oliveirawillian.mapper;

import br.com.oliveirawillian.data.dto.v1.security.AccountCredentialsDTO;
import br.com.oliveirawillian.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-27T09:11:40-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class AccountCredentialsMapperImpl implements AccountCredentialsMapper {

    @Override
    public AccountCredentialsDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        AccountCredentialsDTO accountCredentialsDTO = new AccountCredentialsDTO();

        accountCredentialsDTO.setFullName( user.getFullName() );
        accountCredentialsDTO.setUserName( user.getUserName() );
        accountCredentialsDTO.setPassword( user.getPassword() );

        return accountCredentialsDTO;
    }

    @Override
    public User toEntity(AccountCredentialsDTO accountCredentialsDTO) {
        if ( accountCredentialsDTO == null ) {
            return null;
        }

        User user = new User();

        user.setFullName( accountCredentialsDTO.getFullName() );
        user.setUserName( accountCredentialsDTO.getUserName() );
        user.setPassword( accountCredentialsDTO.getPassword() );

        return user;
    }
}
