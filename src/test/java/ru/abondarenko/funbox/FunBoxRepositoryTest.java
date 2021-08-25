package ru.abondarenko.funbox;

import ru.abondarenko.funbox.entity.Link;
import ru.abondarenko.funbox.repository.FunBoxRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class FunBoxRepositoryTest {
    @Autowired
    private FunBoxRepository repository;

    private static final Long ID = 1L;
    private static final String URL = "https://ya.ru";
    private static final String FILTER_PARAMETER = "visitedAt";
    private static final Long REQUIRED_DATE = 1627832507L;
    private static final Long ID_GREATER_THAN_REQUIRED_DATE = 2L;

    @Test
    public void findByIdTest() {
        Link link = repository.getById(ID);

        assertNotNull(link);
        assertEquals(ID, link.getId());
        assertEquals(URL, link.getName());
    }

    @Test
    public void findAllWithFilterTest() {
        Specification<Link> spec = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(FILTER_PARAMETER), REQUIRED_DATE);

        List<Link> linksWithDateAfterRequired = repository.findAll(spec);
        Link linkWithDateGreaterThanRequired = repository.getById(ID_GREATER_THAN_REQUIRED_DATE);
        Link linkWithDateBeforeRequired = repository.getById(ID);

        assertNotNull(linksWithDateAfterRequired);
        assertEquals(2, linksWithDateAfterRequired.size());
        assertTrue(linksWithDateAfterRequired.contains(linkWithDateGreaterThanRequired));
        assertFalse(linksWithDateAfterRequired.contains(linkWithDateBeforeRequired));
    }
}
