package br.com.oliveirawillian.mapper;

import br.com.oliveirawillian.data.dto.v1.PersonDTO;
import br.com.oliveirawillian.model.Books;
import br.com.oliveirawillian.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T17:17:21-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO toDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId( person.getId() );
        personDTO.setFirstName( person.getFirstName() );
        personDTO.setLastName( person.getLastName() );
        personDTO.setAddress( person.getAddress() );
        personDTO.setGender( person.getGender() );
        personDTO.setEnabled( person.getEnabled() );
        personDTO.setPhotoUrl( person.getPhotoUrl() );
        personDTO.setProfileUrl( person.getProfileUrl() );
        List<Books> list = person.getBooks();
        if ( list != null ) {
            personDTO.setBooks( new ArrayList<Books>( list ) );
        }

        return personDTO;
    }

    @Override
    public Person toEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( personDTO.getId() );
        person.setFirstName( personDTO.getFirstName() );
        person.setLastName( personDTO.getLastName() );
        person.setAddress( personDTO.getAddress() );
        person.setGender( personDTO.getGender() );
        person.setEnabled( personDTO.getEnabled() );
        person.setPhotoUrl( personDTO.getPhotoUrl() );
        person.setProfileUrl( personDTO.getProfileUrl() );
        List<Books> list = personDTO.getBooks();
        if ( list != null ) {
            person.setBooks( new ArrayList<Books>( list ) );
        }

        return person;
    }

    @Override
    public List<PersonDTO> toDTOList(List<Person> personList) {
        if ( personList == null ) {
            return null;
        }

        List<PersonDTO> list = new ArrayList<PersonDTO>( personList.size() );
        for ( Person person : personList ) {
            list.add( toDTO( person ) );
        }

        return list;
    }

    @Override
    public List<Person> toEntityList(List<PersonDTO> personDTOList) {
        if ( personDTOList == null ) {
            return null;
        }

        List<Person> list = new ArrayList<Person>( personDTOList.size() );
        for ( PersonDTO personDTO : personDTOList ) {
            list.add( toEntity( personDTO ) );
        }

        return list;
    }
}
