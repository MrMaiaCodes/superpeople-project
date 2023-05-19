package br.com.mrmaia.superpeope.storage.model;

import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public class SuperPeopleBuilder {

    public static SuperPeople superPeopleSuccessBuilder() {
        return SuperPeople.builder()
                .id(1L).name("Big Dude").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).superPowers(List.of()).build();
    }

    public static SuperPeople superPeopleInvalidNameExceptionErrorBuilder() {
        return SuperPeople.builder()
                .id(1L).name("").planet("Zion").type("Hero").level(1L).currentExperience(1L)
                .nextLevelExperience(2L).charisma(5L).intelligence(5L).dexterity(5L)
                .strength(5L).wisdom(5L).constitution(5L).superPowers(List.of()).build();
    }
}
