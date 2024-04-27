package org.abdelhafeez.librarymanagementsystemapi.web.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseBookDto {

    private Long id;
    private String title;
    private String author;
    private Short publicationYear;
    private String isbn;
    private Boolean available;
    private Boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
