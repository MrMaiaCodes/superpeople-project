package br.com.mrmaia.superpeope.storage.apis.dto.responses.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

    private ErrorSpecificationDTO data;
}
