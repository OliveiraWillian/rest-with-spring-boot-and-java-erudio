package br.com.oliveirawillian.unittetests.services;

import br.com.oliveirawillian.data.dto.v1.PersonDTO;
import br.com.oliveirawillian.exception.RequiredObjectIsNullException;
import br.com.oliveirawillian.mapper.PersonMapper;
import br.com.oliveirawillian.model.Person;
import br.com.oliveirawillian.repository.PersonRepository;
import br.com.oliveirawillian.services.PersonService;
import br.com.oliveirawillian.unittetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {


    private MockPerson mockPerson;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonMapper personMapper;


    @BeforeEach
    void setUp() {
        mockPerson = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findById() {
        Person entityPersonMock = mockPerson.mockEntity(1);
        PersonDTO dtoPersonMock = mockPerson.mockDTO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entityPersonMock));
        when(personMapper.toDTO(entityPersonMock)).thenReturn(dtoPersonMock);
        var result = personService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {

        Person etityPersonMock = mockPerson.mockEntity(1);
        PersonDTO dtoPersonMock = mockPerson.mockDTO(1);

        Person entityPersistedMock = etityPersonMock;
        PersonDTO dtoPersistedPersonMock = dtoPersonMock;


        when(personMapper.toEntity(dtoPersonMock)).thenReturn(etityPersonMock); //1
        when(personRepository.save(etityPersonMock)).thenReturn(entityPersistedMock);//2
        when(personMapper.toDTO(etityPersonMock)).thenReturn(dtoPersistedPersonMock); //3

        var result = personService.create(dtoPersonMock);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());


    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personService.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {

        Person etityPersonMock = mockPerson.mockEntity(1);
        PersonDTO dtoPersonMock = mockPerson.mockDTO(1);

        Person entityPersistedMock = etityPersonMock;
        PersonDTO dtoPersistedPersonMock = dtoPersonMock;

        when(personRepository.findById(dtoPersonMock.getId())).thenReturn(Optional.of(etityPersonMock));
        when(personRepository.save(etityPersonMock)).thenReturn(entityPersistedMock);
        when(personMapper.toDTO(entityPersistedMock)).thenReturn(dtoPersistedPersonMock);

        var result = personService.update(dtoPersonMock);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());


    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personService.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {

        Person etityPersonMock = mockPerson.mockEntity(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(etityPersonMock));
        personService.delete(etityPersonMock.getId());

        verify(personRepository, times(1)).findById(anyLong());
        verify(personRepository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Disabled("REASON : Still Under Development")
    @Test
    void findAll() {

        List<Person> entityPersonMockList = mockPerson.mockEntityList();
        List<PersonDTO> dtoPersonMockList = mockPerson.mockDTOList();

        when(personRepository.findAll()).thenReturn(entityPersonMockList);
        when(personMapper.toDTOList(entityPersonMockList)).thenReturn(dtoPersonMockList);

        List<PersonDTO> listDtoMockPerson = new ArrayList<>();//personService.findAll(pageable); // chama o service  e inicia o processo.
        assertNotNull(listDtoMockPerson);
        assertEquals(14, listDtoMockPerson.size());

        var personOne = listDtoMockPerson.get(1);


        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());


        var personFour = listDtoMockPerson.get(4);

        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());
        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/4")
                        && link.getType().equals("GET")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/4")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = listDtoMockPerson.get(7);

        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());
        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/person/v1/7")
                        && link.getType().equals("GET")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("GET")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("POST")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/person/v1/7")
                        && link.getType().equals("DELETE")));

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());


    }
}
