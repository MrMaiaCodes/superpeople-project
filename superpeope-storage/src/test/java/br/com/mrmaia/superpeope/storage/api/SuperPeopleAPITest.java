package br.com.mrmaia.superpeope.storage.api;

import br.com.mrmaia.superpeope.storage.apis.api.SuperPeopleAPI;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.BattleResultDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleListResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleResponseDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.mappers.ISuperPeopleMapper;
import br.com.mrmaia.superpeope.storage.model.BattleResultDTOBuilder;
import br.com.mrmaia.superpeope.storage.model.SuperPeopleBuilder;
import br.com.mrmaia.superpeope.storage.model.SuperPeopleDTOBuilder;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


// TODO Change all test methods to resemble save test method
public class SuperPeopleAPITest {

    @Mock
    ISuperPeopleService superPeopleService;


    @Mock
    ISuperPeopleMapper superPeopleMapper;

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
        var heroNoId = SuperPeopleBuilder.superPeopleSuccessBuilderNoId();
        when(superPeopleService.save(eq(heroNoId))).thenReturn(heroTested);

        superPeopleAPI.add(heroTestedDTO);
        verify(superPeopleMapper).convertToDto(heroTested);

    }

    @Test
    void testAddInvalidNameExceptionError() throws InvalidNameException,
            BattleAttributeWithValueZeroException, ExcessiveTotalBattleAttributesException {
        var heroAddedDTO = SuperPeopleDTOBuilder.superPeopleDTOInvalidNameExceptionErrorBuilder();
        var heroNoId = SuperPeopleBuilder.superPeopleSuccessBuilderNoId();
        when(superPeopleService.save(eq(heroNoId))).thenThrow(new InvalidNameException("S02", "invalid name"));
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
        when(superPeopleService.findSuperPeopleByName(anyString()))
                .thenReturn(List.of(heroFound));
        when(superPeopleMapper.convertToListDto(List.of(heroFound)))
                .thenReturn(
                        List.of(
                                SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder()
                        )
                );
        SuperPeopleListResponseDTO result = superPeopleAPI.find("Big Dude");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getData().size());
    }

    @Test
    void testFindSuperPeopleNotFoundExceptionError() throws SuperPeopleNotFoundException {
        when(superPeopleService.findSuperPeopleByName(any()))
                .thenThrow(new SuperPeopleNotFoundException("S01", "not found"));
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () -> {
                    superPeopleAPI.find("Big Man");
                });
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }

    @Test
    void testListAllSuccess() {
        var heroFind = SuperPeopleBuilder.superPeopleSuccessBuilder();
        var heroFind2 = SuperPeopleBuilder.superPeopleSuccessBuilder2();
        when(superPeopleService.listAll()).thenReturn(List.of(heroFind, heroFind2));
        when(superPeopleMapper.convertToListDto(List.of(heroFind, heroFind2)))
                .thenReturn(
                        List.of(
                                SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder(),
                                SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder()
                        )
                );
        SuperPeopleListResponseDTO result = superPeopleAPI.listAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getData().size());
    }

    @Test
    void testUpdateSuccess() throws SuperPeopleNotFoundException, InvalidNameException {
        var heroFind = SuperPeopleBuilder.superPeopleSuccessBuilder();
        var heroFindDTO = SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder();
        when(superPeopleService.update(any(), any())).thenReturn(heroFind);
        SuperPeopleResponseDTO result = superPeopleAPI.update(1L, heroFindDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void testUpdateSuperPeopleNotFoundExceptionError()
            throws SuperPeopleNotFoundException, InvalidNameException {
        var heroFindDTO = SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder();
        when(superPeopleService.update(any(), any()))
                .thenThrow(new SuperPeopleNotFoundException("S01", "not found"));
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () -> {
                    superPeopleAPI.update(1L, heroFindDTO);
                });
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }

    @Test
    void testUpdateInvalidNameExceptionError() throws SuperPeopleNotFoundException, InvalidNameException {
        var heroDeleted = SuperPeopleDTOBuilder.superPeopleDTOInvalidNameExceptionErrorBuilder();
        when(superPeopleService.update(any(), any()))
                .thenThrow(new InvalidNameException("S02", "invalid name"));
        InvalidNameException thrown = Assertions.assertThrows(InvalidNameException.class, () -> {
            superPeopleAPI.update(1L, heroDeleted);
        });
        Assertions.assertEquals("S02", thrown.getCode());
        Assertions.assertEquals("invalid name", thrown.getMessage());
    }

    @Test
    void testDeleteSuccess() throws SuperPeopleNotFoundException {
        var heroDelete = SuperPeopleBuilder.superPeopleSuccessBuilder();
        doNothing().when(superPeopleService).delete(heroDelete);
        assertDoesNotThrow(() -> superPeopleAPI.delete(1L));
    }

    @Test
    void testDeleteSuperPeopleNotFoundExceptionError() throws SuperPeopleNotFoundException {
        doThrow(new SuperPeopleNotFoundException("S01", "not found"))
                .when(superPeopleService).delete(SuperPeople.builder().id(1L).build());
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () -> {
                    superPeopleAPI.delete(1L);
                });
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }

    //@Test
    //    void testUpdateSuccess() throws SuperPeopleNotFoundException, InvalidNameException {
    //        var heroFind = SuperPeopleBuilder.superPeopleSuccessBuilder();
    //        var heroFindDTO = SuperPeopleDTOBuilder.superPeopleDTOSuccessBuilder();
    //        when(superPeopleService.update(any())).thenReturn(heroFind);
    //        SuperPeopleResponseDTO result = superPeopleAPI.update(heroFindDTO);
    //        Assertions.assertNotNull(result);
    //    }
    @Test
    void testExperienceAndLevelApplierWinnerSuccess() throws SuperPeopleNotFoundException {
        var winner = SuperPeopleBuilder.superPeopleSuccessBuilder();
        var loser = SuperPeopleBuilder.superPeopleSuccessBuilder2();
        var battleResultDTO = BattleResultDTOBuilder.battleResultDTOBuilderSuccess();
        when(superPeopleService.experienceAndLevelApplier(any(), eq(true))).thenReturn(winner);
        when(superPeopleService.experienceAndLevelApplier(any(), eq(false))).thenReturn(loser);

        ResponseEntity result = superPeopleAPI.battleExperienceAndLevelApplier(battleResultDTO);
        Assertions.assertNotNull(result);
    }

}
