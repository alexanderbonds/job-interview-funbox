package ru.abondarenko.funbox.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
public class LinksPostDto {
    private Set<String> links;
}
