package br.com.mrmaia.superpeope.storage.service;

import br.com.mrmaia.superpeope.storage.exceptions.*;
import br.com.mrmaia.superpeope.storage.model.SuperPeopleBuilder;
import br.com.mrmaia.superpeope.storage.services.ISuperPowerService;
import br.com.mrmaia.superpeope.storage.services.impl.SuperPeopleService;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SuperPeopleServiceTest {

    @Mock
    ISuperPeopleRepository superPeopleRepository;

    @Mock
    ISuperPowerService superPowerService;

    @Mock
    Logger log;

    @InjectMocks
    SuperPeopleService superPeopleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveSuccess()
            throws InvalidNameException, ExcessiveTotalBattleAttributesException,
            BattleAttributeWithValueZeroException {
        var builder = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleRepository.save(any())).thenReturn(builder);
        SuperPeople result = superPeopleService.save(builder);
        Assertions.assertNotNull(result);
    }

    @Test
    void testSaveEmptyNameExceptionError()
            throws InvalidNameException, ExcessiveTotalBattleAttributesException,
            BattleAttributeWithValueZeroException {
        var builder = SuperPeopleBuilder.superPeopleInvalidNameExceptionErrorBuilder();
        InvalidNameException thrown = Assertions.assertThrows(InvalidNameException.class, () -> {
            superPeopleService.save(builder);
        });

        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("invalid name", thrown.getMessage());
    }


    @Test
    void testSaveNullNameError() throws InvalidNameException, ExcessiveTotalBattleAttributesException,
            BattleAttributeWithValueZeroException {
        var builder = SuperPeopleBuilder.superPeopleInvalidNameExceptionErrorBuilder();
        InvalidNameException thrown = Assertions.assertThrows(InvalidNameException.class, () -> {
            superPeopleService.save(builder);
        });
        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("invalid name", thrown.getMessage());
    }


    @Test
    void testSaveExcessiveTotalBattleAttributesExceptionError()
            throws InvalidNameException, ExcessiveTotalBattleAttributesException,
            BattleAttributeWithValueZeroException {
        var builder = SuperPeopleBuilder.
                superPeopleExcessiveTotalBattleAttributesExceptionErrorBuilder();
        ExcessiveTotalBattleAttributesException thrown = Assertions.assertThrows(
                ExcessiveTotalBattleAttributesException.class, () -> {
                    superPeopleService.save(builder);
                });

        Assertions.assertEquals("S02", thrown.getCode());
        Assertions.assertEquals("Total battle attributes must not exceed 30.", thrown.getMessage());
    }

    @Test
    void testBattleAttributeWithValueZeroExceptionError()
            throws InvalidNameException, ExcessiveTotalBattleAttributesException,
            BattleAttributeWithValueZeroException {
        var builder = SuperPeopleBuilder.
                superPeopleBattleAttributeWithValueZeroExceptionErrorBuilder();
        BattleAttributeWithValueZeroException thrown = Assertions.assertThrows(
                BattleAttributeWithValueZeroException.class, () -> {
                    superPeopleService.save(builder);
                });

        Assertions.assertEquals("S03", thrown.getCode());
        Assertions.assertEquals("All battle attributes must have a value of at least 1.",
                thrown.getMessage());
    }

    @Test
    void findSuperPeopleByNameSuccess() throws SuperPeopleNotFoundException {
        var builder = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleRepository.findSuperPeopleByName(any()))
                .thenReturn(List.of(builder
                        )
                );
        List<SuperPeople> superPeopleFound = superPeopleService.findSuperPeopleByName("Big Man");
        Assertions.assertNotNull(superPeopleFound);
    }

    @Test
    void findSuperPeopleByNameSuperPeopleNotFoundExceptionError()
            throws SuperPeopleNotFoundException {
        when(superPeopleRepository.findSuperPeopleByName(any())).thenReturn(List.of());
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(
                SuperPeopleNotFoundException.class, () -> {
                    superPeopleService.findSuperPeopleByName("Big Man");
                }
        );
        Assertions.assertEquals("S04", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }

    @Test
    void testListAllSuccess() {
        when(superPeopleRepository.findAll()).thenReturn(List.of());
        List<SuperPeople> superPeopleList = superPeopleService.listAll();
        Assertions.assertNotNull(superPeopleList);
    }

    @Test
    void testDeleteSuccess() throws SuperPeopleNotFoundException {
        var builder = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleRepository.findById(any())).thenReturn(Optional.of(builder));
        superPeopleService.delete(builder);
    }

    @Test
    void testDeleteSuperPeopleNotFoundExceptionError() throws SuperPeopleNotFoundException {
        when(superPeopleRepository.findById(any())).thenReturn(Optional.empty());
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(
                SuperPeopleNotFoundException.class, () -> {
                    superPeopleService.delete(SuperPeople.builder()
                            .name("Big Man").level(1L).currentExperience(1L)
                            .nextLevelExperience(1L).planet("Big Planet")
                            .superPowers(List.of(SuperPower.builder().id(1L).build()))
                            .type("hero").strength(5L).constitution(5L).dexterity(5L)
                            .intelligence(5L).wisdom(5L).charisma(5L)
                            .build());
                });
        Assertions.assertEquals("S04", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }


    @Test
    void testUpdateSuccess() throws SuperPeopleNotFoundException, InvalidNameException,
            BattleAttributeWithValueZeroException, ExcessiveTotalBattleAttributesException {
        when(superPeopleRepository.findById(any())).thenReturn(Optional.of(SuperPeople.builder()
                        .name("Big Man").level(1L).currentExperience(1L)
                        .nextLevelExperience(1L).planet("Big Planet")
                        .superPowers(List.of(SuperPower.builder().id(1L).build()))
                        .type("hero").strength(5L).constitution(5L).dexterity(5L)
                        .intelligence(5L).wisdom(5L).charisma(5L)
                        .build()
                )
        );
        when(superPeopleRepository.save(any(SuperPeople.class))).thenReturn(
                SuperPeople.builder()
                        .name("Big Man").planet("Big Planet").type("Hero")
                        .build()
        );
        SuperPeople result = superPeopleService.update(1L,
                SuperPeople.builder()
                        .name("Big Man").planet("Big Planet").type("Hero")
                        .build()
        );
        Assertions.assertNotNull(result);
    }


    @Test
    void testExperienceAndLevelApplierSuccess() throws InvalidNameException, SuperPeopleNotFoundException {
        var heroTested = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleRepository.findById(1L)).thenReturn(Optional.of(heroTested));

        SuperPeople result = superPeopleService.experienceAndLevelApplier(1L, true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testUpdateSuperPersonNotFoundExceptionError() throws SuperPeopleNotFoundException {
            when(superPeopleRepository.findById(any())).thenReturn(Optional.empty());
            SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                    () -> {
                        superPeopleService.update(1L, SuperPeople.builder()
                                .name("Big Man").planet("Big Planet").type("Hero").build());
                    });
            Assertions.assertEquals("S04", thrown.getCode());
            Assertions.assertEquals("not found", thrown.getMessage());
        }
    @Test
    void testExperienceAndLevelApplierSuperPeopleNotFoundExceptionError()
            throws SuperPeopleNotFoundException {
        var heroTested = SuperPeopleBuilder.superPeopleSuccessBuilder();
        when(superPeopleRepository.findById(any())).thenReturn(Optional.empty());
        SuperPeopleNotFoundException thrown = Assertions.assertThrows(SuperPeopleNotFoundException.class,
                () ->{superPeopleService.experienceAndLevelApplier(heroTested.getId(),true);
        });
        Assertions.assertEquals("S04", thrown.getCode());
        Assertions.assertEquals("not found", thrown.getMessage());
    }


}

