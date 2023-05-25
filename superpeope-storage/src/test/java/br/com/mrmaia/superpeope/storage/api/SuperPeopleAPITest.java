package br.com.mrmaia.superpeope.storage.api;

import br.com.mrmaia.superpeope.storage.apis.api.SuperPeopleAPI;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleListResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleResponseDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.model.SuperPeopleBuilder;
import br.com.mrmaia.superpeope.storage.model.SuperPeopleDTOBuilder;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class SuperPeopleAPITest {

    @Mock
    ISuperPeopleService superPeopleService;

    @InjectMocks
    SuperPeopleAPI superPeopleAPI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSuccess() throws InvalidNameException, BattleAttributeWithValueZeroException,
            ExcessiveTotalBattleAttributesException {
        var heroTested = SuperPeopleBuilder.superPeopleSuccessBuilder();
        var heroTestedDTO = SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder();
        when(superPeopleService.save(any())).thenReturn(heroTested);

        SuperPeopleResponseDTO result = superPeopleAPI.add(heroTestedDTO);

        Assertions.assertNotNull(result);
    }
/*
    @Test
    void testAddSuperPeopleNotFoundExceptionError() throws InvalidNameException,
            BattleAttributeWithValueZeroException, ExcessiveTotalBattleAttributesException {
        var heroAddedDTO = SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder();
        when(superPeopleService.save(any()))
                .thenThrow(new SuperPeopleNotFoundException("S01", "not found"));
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () -> {
                    superPeopleAPI.add(heroAddedDTO);
                });
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }
*/
    @Test
    void testAddInvalidNameExceptionError() throws InvalidNameException,
            BattleAttributeWithValueZeroException, ExcessiveTotalBattleAttributesException {
        var heroAddedDTO = SuperPeopleDTOBuilder.superPeopleDTOInvalidNameExceptionErrorBuilder();
        when(superPeopleService.save(any())).thenThrow(new InvalidNameException("S02", "invalid name"));
        InvalidNameException thrown = Assertions.assertThrows(InvalidNameException.class, () -> {
            superPeopleAPI.add(heroAddedDTO);
        });
        Assertions.assertEquals("S02", thrown.getCode());
        Assertions.assertEquals("invalid name", thrown.getMessage());
    }

    @Test
    void testBattleAttributeWithValueZeroException() throws InvalidNameException,
            BattleAttributeWithValueZeroException, ExcessiveTotalBattleAttributesException {
        var heroAddedDTO = SuperPeopleDTOBuilder
                .superPeopleDTOBattleAttributeWithValueZeroExceptionErrorBuilder();
        when(superPeopleService.save(any()))
                .thenThrow(new BattleAttributeWithValueZeroException("S03", "attribute with zero value"));
        BattleAttributeWithValueZeroException thrown = Assertions.assertThrows(
                BattleAttributeWithValueZeroException.class, () -> {
                    superPeopleAPI.add(heroAddedDTO);
                });
        Assertions.assertEquals("S03", thrown.getCode());
        Assertions.assertEquals("attribute with zero value", thrown.getMessage());
    }

    @Test
    void testExcessiveTotalBattleAttributesExceptionError() throws
            InvalidNameException, BattleAttributeWithValueZeroException,
            ExcessiveTotalBattleAttributesException {
        var heroAddedDTO = SuperPeopleDTOBuilder
                .superPeopleDTOExcessiveTotalBattleAttributesExceptionErrorBuilder();
        when(superPeopleService.save(any()))
                .thenThrow(new ExcessiveTotalBattleAttributesException("S04", "excessive battle attributes"));
        ExcessiveTotalBattleAttributesException thrown = Assertions.assertThrows(
                ExcessiveTotalBattleAttributesException.class, () -> {
                    superPeopleAPI.add(heroAddedDTO);
                });
        Assertions.assertEquals("S04", thrown.getCode());
        Assertions.assertEquals("excessive battle attributes", thrown.getMessage());
    }

    @Test
    void testFindSuccess() throws SuperPeopleNotFoundException {
        var heroFound = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleService.findSuperPeopleByName(any())).thenReturn(List.of(heroFound));
        SuperPeopleListResponseDTO result = superPeopleAPI.find("Big Dude");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getData().size());
    }

    @Test
    void testFindSuperPeopleNotFoundExceptionError() throws SuperPeopleNotFoundException {
        when(superPeopleService.findSuperPeopleByName(any()))
                .thenThrow(new SuperPeopleNotFoundException("S01", "not found"));
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () -> {superPeopleAPI.find("Big Man");});
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }
}
