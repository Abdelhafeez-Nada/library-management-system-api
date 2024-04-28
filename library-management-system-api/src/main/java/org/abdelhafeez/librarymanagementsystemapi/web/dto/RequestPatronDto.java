package org.abdelhafeez.librarymanagementsystemapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RequestPatronDto {
    private String name;
    private String contactInfo;
}
