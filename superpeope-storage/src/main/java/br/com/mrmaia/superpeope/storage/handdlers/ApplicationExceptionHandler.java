package br.com.mrmaia.superpeope.storage.handdlers;

import br.com.mrmaia.superpeope.storage.apis.dto.responses.errors.ErrorResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.errors.ErrorSpecificationDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorResponseDTO> InvalidNameExceptionHandler(InvalidNameException exception) {
        log.info("invalid name");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder()
                        .data(ErrorSpecificationDTO.builder()
                                .errorCode("400")
                                .errorMessage(exception.getMessage())
                                .build())
                        .build());
    }

    @ExceptionHandler(TotalBattleAttributesOverThirtyException.class)
    public ResponseEntity<ErrorResponseDTO> TotalBattleAttributesOverThirtyExceptionHandler(
            TotalBattleAttributesOverThirtyException exception) {
        log.info("Total battle attributes must not exceed 30.");

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(ErrorResponseDTO.builder()
                        .data(ErrorSpecificationDTO.builder()
                                .errorCode("412")
                                .errorMessage(exception.getMessage())
                                .build())
                        .build());
    }

    @ExceptionHandler(BattleAttributeWithValueZeroException.class)
    public ResponseEntity<ErrorResponseDTO> BattleAttributeWithValueZeroExceptionHandler(
            BattleAttributeWithValueZeroException exception) {
        log.info("All battle attributes must have a value of at least 1.");

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(ErrorResponseDTO.builder()
                        .data(ErrorSpecificationDTO.builder()
                                .errorCode("412")
                                .errorMessage(exception.getMessage())
                                .build())
                        .build());
    }
}
