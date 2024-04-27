package org.abdelhafeez.librarymanagementsystemapi.web.dto;

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
    private String title;
    private String author;
    private Short publicationYear;
    private String isbn;
}
