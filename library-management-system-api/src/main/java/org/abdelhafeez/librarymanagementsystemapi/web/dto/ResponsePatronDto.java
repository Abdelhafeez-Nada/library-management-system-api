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
public class ResponsePatronDto {
    private Long id;
    private String name;
    private String contactInfo;
    private Date createdAt;
    private Date updatedAt;
}
