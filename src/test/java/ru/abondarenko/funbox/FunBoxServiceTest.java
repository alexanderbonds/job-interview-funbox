package ru.abondarenko.funbox;

import ru.abondarenko.funbox.entity.Link;
import ru.abondarenko.funbox.repository.FunBoxRepository;
import ru.abondarenko.funbox.service.FunBoxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class FunBoxServiceTest {
    @Autowired
    private FunBoxService service;

    @MockBean
    private FunBoxRepository repository;

    private static final String LINK_NAME = "https://ya.ru";
    private static final Long LINK_ID = 1L;
    private static final Long LINK_VISITED_AT = 1627832506L;

    @BeforeEach
    public void init() {
        Link link = new Link();
        link.setId(LINK_ID);
        link.setName(LINK_NAME);
        link.setVisitedAt(LINK_VISITED_AT);

        Mockito.doReturn(Optional.of(link)).when(repository).findById(LINK_ID);
    }

    @Test
    public void findByIdTest() {
        Optional<Link> link = service.findById(LINK_ID);

        Assertions.assertTrue(link.isPresent());
        Assertions.assertEquals(LINK_ID.intValue(), link.get().getId());
        Assertions.assertEquals(LINK_NAME, link.get().getName());
        Assertions.assertEquals(LINK_VISITED_AT.toString(), link.get().getVisitedAt().toString());
    }
}
