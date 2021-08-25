package ru.abondarenko.funbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinksGetDto {
    private Set<String> domains;
    private String status;
}
