package br.com.mrmaia.superpeope.storage.service;

import br.com.mrmaia.superpeope.storage.services.ISuperPowerService;
import br.com.mrmaia.superpeope.storage.services.impl.SuperPeopleService;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;
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
            throws InvalidNameException, TotalBattleAttributesOverThirtyException,
            BattleAttributeWithValueZeroException {
        when(superPeopleRepository.save(any())).thenReturn(SuperPeople.builder().id(1L).build());
        SuperPeople result = superPeopleService.save(
                SuperPeople.builder()
                        .name("Big Man").level(1L).currentExperience(1L)
                        .nextLevelExperience(1L).planet("Big Planet")
                        .superPowers(List.of(SuperPower.builder().id(1L).build()))
                        .type("hero").strength(5L).constitution(5L).dexterity(5L)
                        .intelligence(5L).wisdom(5L).charisma(5L)
                        .build());
        Assertions.assertNotNull(result);
    }

    @Test
    void testSaveInvalidNameExceptionError()
            throws InvalidNameException, TotalBattleAttributesOverThirtyException,
            BattleAttributeWithValueZeroException {
        InvalidNameException thrown = Assertions.assertThrows(InvalidNameException.class, () -> {
            superPeopleService.save(
                    SuperPeople.builder()
                            .name("").level(1L).currentExperience(1L)
                            .nextLevelExperience(1L).planet("Big Planet")
                            .superPowers(List.of(SuperPower.builder().id(1L).build()))
                            .type("hero").strength(5L).constitution(5L).dexterity(5L)
                            .intelligence(5L).wisdom(5L).charisma(5L)
                            .build());
        });

        Assertions.assertEquals("S01", thrown.getCode());
        Assertions.assertEquals("invalid name", thrown.getMessage());
    }

    @Test
    void testSaveTotalBattleAttributesOverThirtyExceptionError()
            throws InvalidNameException, TotalBattleAttributesOverThirtyException,
            BattleAttributeWithValueZeroException {
        TotalBattleAttributesOverThirtyException thrown = Assertions.assertThrows(
                TotalBattleAttributesOverThirtyException.class, () -> {
                    superPeopleService.save(SuperPeople.builder()
                            .name("Big Man").level(1L).currentExperience(1L)
                            .nextLevelExperience(1L).planet("Big Planet")
                            .superPowers(List.of(SuperPower.builder().id(1L).build()))
                            .type("hero").strength(5L).constitution(5L).dexterity(5L)
                            .intelligence(5L).wisdom(6L).charisma(5L)
                            .build());
                });

        Assertions.assertEquals("S02", thrown.getCode());
        Assertions.assertEquals("Total battle attributes must not exceed 30.", thrown.getMessage());
    }

    @Test
    void testBattleAttributeWithValueZeroExceptionError()
            throws InvalidNameException, TotalBattleAttributesOverThirtyException,
            BattleAttributeWithValueZeroException {
        BattleAttributeWithValueZeroException thrown = Assertions.assertThrows(
                BattleAttributeWithValueZeroException.class, () -> {
                    superPeopleService.save(SuperPeople.builder()
                            .name("Big Man").level(1L).currentExperience(1L)
                            .nextLevelExperience(1L).planet("Big Planet")
                            .superPowers(List.of(SuperPower.builder().id(1L).build()))
                            .type("hero").strength(5L).constitution(5L).dexterity(5L)
                            .intelligence(5L).wisdom(0L).charisma(5L)
                            .build());
                });

        Assertions.assertEquals("S03", thrown.getCode());
        Assertions.assertEquals("All battle attributes must have a value of at least 1.",
                thrown.getMessage());
    }
}