package org.abdelhafeez.librarymanagementsystemapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private Short publicationYear;
    @NotBlank
    private String isbn;
}
