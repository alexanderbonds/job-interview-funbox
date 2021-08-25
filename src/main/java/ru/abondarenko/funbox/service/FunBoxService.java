package ru.abondarenko.funbox.service;

import ru.abondarenko.funbox.dto.LinksGetDto;
import ru.abondarenko.funbox.entity.Link;
import ru.abondarenko.funbox.repository.FunBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunBoxService {
    private final FunBoxRepository funBoxRepository;

    public Optional<Link> findById(Long id) {
        return funBoxRepository.findById(id);
    }

    public LinksGetDto findAll(Specification<Link> spec){
        Set<String> set = funBoxRepository.findAll(spec).stream().map(Link::getName).collect(Collectors.toSet());
        return new LinksGetDto(set, "OK");
    }

    public List<Link> saveOrUpdate(Set<String> links) {
        Set<Link> linkSet = links.stream().map(url -> {
            Link l = new Link();
            l.setName(url);
            l.setVisitedAt(System.currentTimeMillis() / 1000L);
            return l;
        }).collect(Collectors.toSet());
        return funBoxRepository.saveAll(linkSet);
    }

}
