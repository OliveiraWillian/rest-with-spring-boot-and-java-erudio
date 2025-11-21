package br.com.oliveirawillian.unittetests.services;

import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.exception.RequiredObjectIsNullException;

import br.com.oliveirawillian.mapper.BooksMapper;
import br.com.oliveirawillian.model.Books;
import br.com.oliveirawillian.repository.BooksRepository;
import br.com.oliveirawillian.services.BooksService;
import br.com.oliveirawillian.unittetests.mapper.mocks.MockBooks;
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
class BooksServiceTest {


    private MockBooks mockBooks;

    @Mock
    private BooksRepository booksRepository;
    @Mock
    private BooksMapper booksMapper;

    @InjectMocks
    private BooksService booksService;


    @BeforeEach
    void setUp() {
        mockBooks = new MockBooks();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findById() {
        Books entityBooksMock = mockBooks.mockEntity(1);
        BooksDTO dtoBooksMock = mockBooks.mockDTO(1);

        when(booksRepository.findById(1L)).thenReturn(Optional.of(entityBooksMock));
        when(booksMapper.toDTO(entityBooksMock)).thenReturn(dtoBooksMock);
        var result = booksService.findById(1L);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.00, result.getPrice());

    }



//    @Test
//    void create() {
//        Employee employee = input.mockEntity(1);
//        employee.setId(null);
//
//        EmployeeDTO persistedEmployeeDTO = input.mockDTO(1);
//        EmployeeDTO employeeDTO = input.mockDTO(1);
//        employeeDTO.setId(null);
//
//        when(repository.save(employee)).thenReturn(employee);
//        when(mapper.toEntity(employeeDTO)).thenReturn(employee);
//        when(mapper.toDTO(employee)).thenReturn(persistedEmployeeDTO);
//
//        var result = service.create(employeeDTO);
//
//        assertNotNull(result);
//        assertEquals(persistedEmployeeDTO, result);
//        hasLinkHateoas(result);
//
//        verify(repository).save(employee);
//        verify(mapper).toDTO(employee);
//        verify(mapper).toEntity(employeeDTO);
//        verifyNoMoreInteractions(repository, mapper);
//    }







    @Test
    void create() {

        Books etityBooksMock = mockBooks.mockEntity(1);
        BooksDTO dtoBooksMock = mockBooks.mockDTO(1);

        Books entityPersistedMock = etityBooksMock;
        BooksDTO dtoPersistedBooksMock = dtoBooksMock;


        when(booksMapper.toEntity(dtoBooksMock)).thenReturn(etityBooksMock); //1
        when(booksRepository.save(etityBooksMock)).thenReturn(entityPersistedMock);//2
        when(booksMapper.toDTO(etityBooksMock)).thenReturn(dtoPersistedBooksMock); //3

        var result = booksService.create(dtoBooksMock);



        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.00, result.getPrice());



    }
    @Test
    void testCreateWithNullBooks() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {booksService.create(null);});
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {

        Books etityBooksMock = mockBooks.mockEntity(1);
        BooksDTO dtoBooksMock = mockBooks.mockDTO(1);

        Books entityPersistedMock = etityBooksMock;
        BooksDTO dtoPersistedBooksMock = dtoBooksMock;

        when(booksRepository.findById(dtoBooksMock.getId())).thenReturn(Optional.of(etityBooksMock));
        when(booksRepository.save(etityBooksMock)).thenReturn(entityPersistedMock);
        when(booksMapper.toDTO(entityPersistedMock)).thenReturn(dtoPersistedBooksMock);

        var result = booksService.update(dtoBooksMock);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.00, result.getPrice());


    }
    @Test
    void testUpdateWithNullBooks() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {booksService.create(null);});
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {

        Books etityBooksMock = mockBooks.mockEntity(1);

        when(booksRepository.findById(1L)).thenReturn(Optional.of(etityBooksMock));
        booksService.delete(etityBooksMock.getId());

        verify(booksRepository, times(1)).findById(anyLong());
        verify(booksRepository, times(1)).delete(any(Books.class));
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    @Disabled("REASON: Stil under Development")
    void findAll() {

        List<Books> entityBooksMockList = mockBooks.mockEntityList();
        List<BooksDTO> dtoBooksMockList = mockBooks.mockDTOList();

        when(booksRepository.findAll()).thenReturn(entityBooksMockList);
        when(booksMapper.toDTOList(entityBooksMockList)).thenReturn(dtoBooksMockList);

        List<BooksDTO> listDtoMockBooks = new ArrayList<>() ;//booksService.findAll(); // chama o service  e inicia o processo.

        assertNotNull(listDtoMockBooks);
        assertEquals(14, listDtoMockBooks.size());

        var booksOne = listDtoMockBooks.get(1);


        assertNotNull(booksOne);
        assertNotNull(booksOne.getId());
        assertNotNull(booksOne.getLinks());
        assertTrue(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/books/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", booksOne.getAuthor());
        assertEquals("Title Test1", booksOne.getTitle());
        assertNotNull(booksOne.getLaunchDate());

        assertEquals(1.00, booksOne.getPrice());


        var booksFour = listDtoMockBooks.get(4);

        assertNotNull(booksFour);
        assertNotNull(booksFour.getId());
        assertNotNull(booksFour.getLinks());
        assertTrue(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/books/v1/4")
                        && link.getType().equals("GET")));

        assertTrue(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/books/v1/4")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test4", booksFour.getAuthor());
        assertEquals("Title Test4", booksFour.getTitle());
        assertNotNull(booksFour.getLaunchDate());

        assertNotNull(booksFour.getLaunchDate());

        var booksSeven = listDtoMockBooks.get(7);

        assertNotNull(booksSeven);
        assertNotNull(booksSeven.getId());
        assertNotNull(booksSeven.getLinks());
        assertTrue(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().contains("/api/books/v1/7")
                        && link.getType().equals("GET")));

        assertTrue(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("GET")));

        assertTrue(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("POST")));

        assertTrue(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().contains("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertTrue(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().contains("/api/books/v1/7")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test7", booksSeven.getAuthor());
        assertEquals("Title Test7", booksSeven.getTitle());
        assertNotNull(booksSeven.getLaunchDate());

        assertEquals(7.00, booksSeven.getPrice());


    }
}
