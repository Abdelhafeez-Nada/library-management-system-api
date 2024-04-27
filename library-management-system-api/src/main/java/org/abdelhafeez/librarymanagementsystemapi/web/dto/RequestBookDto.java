package org.abdelhafeez.librarymanagementsystemapi.web.dto;

import org.abdelhafeez.librarymanagementsystemapi.web.validation.PublicationYear;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RequestBookDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @PublicationYear
    private Short publicationYear;
    @NotBlank
    private String isbn;
}
