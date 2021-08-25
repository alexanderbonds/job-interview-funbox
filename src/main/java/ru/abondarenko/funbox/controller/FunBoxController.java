package ru.abondarenko.funbox.controller;

import ru.abondarenko.funbox.dto.LinksGetDto;
import ru.abondarenko.funbox.dto.LinksPostDto;
import ru.abondarenko.funbox.dto.PostResponseDto;
import ru.abondarenko.funbox.entity.Link;
import ru.abondarenko.funbox.repository.specifications.LinksSpecifications;
import ru.abondarenko.funbox.service.FunBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
public class FunBoxController {
    private final FunBoxService funBoxService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto saveNewUrl(@RequestBody LinksPostDto linksPostDto) {
        funBoxService.saveOrUpdate(linksPostDto.getLinks());
        return new PostResponseDto("OK");
    }

    @GetMapping
    public LinksGetDto findAll(@RequestParam MultiValueMap<String, String> params){
        return funBoxService.findAll(LinksSpecifications.build(params));
    }

    @GetMapping("/{id}")
    public Link findProductById(@PathVariable Long id) {
        return funBoxService.findById(id).orElseThrow(() -> new RuntimeException("URL with id: " + id + " doesn't exist"));
    }
}
