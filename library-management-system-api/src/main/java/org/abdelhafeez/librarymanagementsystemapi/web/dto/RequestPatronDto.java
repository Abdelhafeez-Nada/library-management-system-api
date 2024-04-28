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
public class RequestPatronDto {
    @NotBlank
    private String name;
    @NotBlank
    private String contactInfo;
}
