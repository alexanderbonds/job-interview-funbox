package ru.abondarenko.funbox.repository.specifications;

import ru.abondarenko.funbox.entity.Link;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public class LinksSpecifications {
    private static Specification<Link> dateGreaterOrEqualsThan(long from) {
        return (Specification<Link>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("visitedAt"), from);
    }

    private static Specification<Link> dateLesserOrEqualsThan(long to) {
        return (Specification<Link>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("visitedAt"), to);
    }

    public static Specification<Link> build(MultiValueMap<String, String> params) {
        Specification<Link> spec = Specification.where(null);
        if (params.containsKey("from") && !params.getFirst("from").isBlank()) {
            spec = spec.and(LinksSpecifications.dateGreaterOrEqualsThan(Long.parseLong(params.getFirst("from"))));
        }
        if (params.containsKey("to") && !params.getFirst("to").isBlank()) {
            spec = spec.and(LinksSpecifications.dateLesserOrEqualsThan(Long.parseLong(params.getFirst("to"))));
        }
        return spec;
    }
}
