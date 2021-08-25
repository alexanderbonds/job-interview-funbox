package ru.abondarenko.funbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.abondarenko.funbox.controller.FunBoxController;
import ru.abondarenko.funbox.dto.LinksGetDto;
import ru.abondarenko.funbox.dto.LinksPostDto;
import ru.abondarenko.funbox.entity.Link;
import ru.abondarenko.funbox.service.FunBoxService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FunBoxController.class)
@ActiveProfiles("test")
public class FunBoxControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FunBoxService funBoxService;

    private static final String API_URL = "/api/v1/links";
    private static final String RESPONSE_STATUS = "OK";
    private static final Set<String> LINKS_WITHOUT_FILTER_SET = Set.of(new String[] {
            "https://ya.ru",
            "https://google.com"
    });
    private static final Set<String> LINKS_WITH_FILTER_SET = Set.of(new String[] {
            "https://ya.ru"
    });
    private static final Set<String> LINKS_TO_POST_SET = Set.of(new String[] {
            "https://ya.ru",
            "https://google.com",
            "https://vk.com"
    });

    private static LinksGetDto linksWithoutFilterDto;
    private static LinksGetDto linksWithFilterDto;
    private static LinksPostDto linksPostDto;
    private static final Specification<Link> emptySpec = Specification.where(null);
    private static final Specification<Link> fullSpec = Specification.where(null);

    @BeforeAll
    public static void init() {
        linksWithoutFilterDto = new LinksGetDto();
        linksWithoutFilterDto.setDomains(LINKS_WITHOUT_FILTER_SET);
        linksWithoutFilterDto.setStatus(RESPONSE_STATUS);

        linksWithFilterDto = new LinksGetDto();
        linksWithFilterDto.setDomains(LINKS_WITH_FILTER_SET);
        linksWithFilterDto.setStatus(RESPONSE_STATUS);

        linksPostDto = new LinksPostDto();
        linksPostDto.setLinks(LINKS_TO_POST_SET);
    }

    @Test
    public void getAllLinksWithoutFilterTest() throws Exception {
        given(funBoxService.findAll(emptySpec)).willReturn(linksWithoutFilterDto);
        mvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(RESPONSE_STATUS)))
                .andExpect(jsonPath("$.domains").isArray())
                .andExpect(jsonPath("$.domains", hasSize(LINKS_WITHOUT_FILTER_SET.size())));
    }

    @Test
    public void getLinksWithFilterTest() throws Exception {
        given(funBoxService.findAll(fullSpec)).willReturn(linksWithFilterDto);
        mvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(RESPONSE_STATUS)))
                .andExpect(jsonPath("$.domains").isArray())
                .andExpect(jsonPath("$.domains", hasSize(LINKS_WITH_FILTER_SET.size())));
    }

    @Test
    public void postNewLinksTest() throws Exception {
        given(funBoxService.saveOrUpdate(linksPostDto.getLinks())).willReturn(List.of());
        mvc.perform(post(API_URL)
                .content(mapper.writeValueAsString(linksPostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(RESPONSE_STATUS)));
    }
}

