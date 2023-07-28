package br.com.mrmaia.superpeope.storage.model;

import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

public class SuperPeopleBuilder {

    public static SuperPeople superPeopleSuccessBuilder() {
        return SuperPeople.builder()
                .id(1L)
                .name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }

    public static SuperPeople superPeopleSuccessBuilderNoId() {
        return SuperPeople.builder()
                .name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }

    public static SuperPeople superPeopleSuccessBuilder2() {
        return SuperPeople.builder()
                .id(1L)
                .name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }

    public static SuperPeople superPeopleUpdateBuilder(
            SuperPeople found, String name, String planet, String type) {
        return SuperPeople.builder()
                .id(found.getId())
                .name(name).planet(planet).type(type)
                .level(found.getLevel()).currentExperience(found.getCurrentExperience())
                .nextLevelExperience(found.getNextLevelExperience())
                .charisma(found.getCharisma()).intelligence(found.getIntelligence())
                .dexterity(found.getDexterity()).strength(found.getStrength())
                .wisdom(found.getWisdom()).constitution(found.getConstitution()).build();
    }

    public static SuperPeople superPeopleInvalidNameExceptionErrorBuilder() {
        return SuperPeople.builder()
                .id(1L)
                .name("").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }

    public static SuperPeople superPeopleExcessiveTotalBattleAttributesExceptionErrorBuilder() {
        return SuperPeople.builder()
                .name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(7L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }

    public static SuperPeople superPeopleBattleAttributeWithValueZeroExceptionErrorBuilder() {
        return SuperPeople.builder()
                .name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(0L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).build();
    }
}
