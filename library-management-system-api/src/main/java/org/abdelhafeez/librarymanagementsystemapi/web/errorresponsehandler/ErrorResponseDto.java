package org.abdelhafeez.librarymanagementsystemapi.web.errorresponsehandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponseDto {
    private String code;
    private String message;
}
