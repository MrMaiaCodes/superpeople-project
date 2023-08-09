package br.com.mrmaia.superpeope.storage.services.impl;

import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.Battle;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.IBattleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.addAll;

@Service
@Slf4j
public class BattleService implements IBattleService {

    @Autowired
    private ISuperPeopleRepository superPeopleRepository;

    public Battle battleOrganizer(SuperPeople combatantOne) throws SuperPeopleNotFoundException {

        combatantOneFinder(combatantOne.getId());
        List<SuperPeople> opponentList = opponentListCreator(combatantOne, 5L);
        Battle battle = Battle.builder().superHeroOne(combatantOne).opponentList(opponentList).build();

        return battle;
    }

    private List<SuperPeople> opponentListCreator(SuperPeople combatantOne,
                                                  Long desiredOpponentAmount) {
        var lowerLevel = combatantOne.getLevel() - 2;
        var upperLevel = combatantOne.getLevel() + 2;
        List<SuperPeople> allCandidates = new ArrayList<>();
        var lowerLevelCandidates = opponentFinder(lowerLevel, 2L);
        var equalLevelCandidates = opponentFinder(
                combatantOne.getLevel(), 5L);
        var higherLevelCandidates = opponentFinder(upperLevel, 2L);
        listAdder(allCandidates, lowerLevelCandidates);
        listAdder(allCandidates, equalLevelCandidates);
        listAdder(allCandidates, higherLevelCandidates);
        List<SuperPeople> opponentList = opponentGenerator(allCandidates, combatantOne, 9L);
        return opponentList;
    }


    private SuperPeople combatantOneFinder(Long combatantOneId) throws SuperPeopleNotFoundException {
        return superPeopleRepository.findById(combatantOneId)
                .orElseThrow(() -> new SuperPeopleNotFoundException("S04", "not found"));
    }

    private List<SuperPeople> opponentFinder(Long level, Long desiredOpponentAmount) {
        List<SuperPeople> foundOpponents = new ArrayList<>();
        List<SuperPeople> opponentList = new ArrayList<>();
        long opponentAmount = 0;
        for (SuperPeople opponent : opponentList) {
            if (opponent.getLevel().equals(level) && opponentAmount <= desiredOpponentAmount) {
                foundOpponents.add(opponent);
                opponentAmount++;
            }
        }
        return foundOpponents;
    }

    private List<SuperPeople> opponentGenerator(List<SuperPeople> foundOpponents, SuperPeople combatantOne,
                                                Long desiredOpponentAmount) {
        int instance = 0;
        if (foundOpponents == null) {
            List<SuperPeople> generatedOpponents = new ArrayList<>();
            do {
                Random random = new Random();
                int range = 1;
                SuperPeople generatedOpponent = SuperPeople.builder()
                        .strength(combatantOne.getStrength() - range + random.nextInt(2 * range + 1))
                        .dexterity(combatantOne.getDexterity() - range + random.nextInt(2 * range + 1))
                        .level(combatantOne.getLevel() - range + random.nextInt(2 * range + 1))
                        .constitution(combatantOne.getConstitution() - range + random.nextInt(2 * range + 1))
                        .build();
                generatedOpponents.add(generatedOpponent);
                instance++;
            } while (instance <= desiredOpponentAmount);
            foundOpponents.addAll(generatedOpponents);
            return foundOpponents;

        } else if (foundOpponents.size() < desiredOpponentAmount) {
            List<SuperPeople> generatedOpponents = new ArrayList<>();
            do {
                Random random = new Random();
                int range = 1;
                SuperPeople generatedOpponent = SuperPeople.builder()
                        .strength(combatantOne.getStrength() - range + random.nextInt(2 * range + 1))
                        .dexterity(combatantOne.getDexterity() - range + random.nextInt(2 * range + 1))
                        .level(combatantOne.getLevel() - range + random.nextInt(2 * range + 1))
                        .constitution(combatantOne.getConstitution() - range + random.nextInt(2 * range + 1))
                        .build();
                generatedOpponents.add(generatedOpponent);
                instance++;
            } while (instance <= (desiredOpponentAmount - foundOpponents.size()));
            foundOpponents.addAll(generatedOpponents);
            return foundOpponents;

        }
        return foundOpponents;
    }

    private List<SuperPeople> listAdder(List<SuperPeople> finalList, List<SuperPeople> listToAdd) {
        finalList.addAll(listToAdd);
        return finalList;
    }
}
