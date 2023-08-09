package br.com.mrmaia.superpeope.storage.services.impl;

import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.IBattleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class BattleService implements IBattleService {

    @Autowired
    private ISuperPeopleRepository superPeopleRepository;

    @Override
    public SuperPeople superHeroFinder(Long hero) throws SuperPeopleNotFoundException {
        return superPeopleRepository.findById(hero)
                .orElseThrow(() -> new SuperPeopleNotFoundException("S04", "not found"));
    }

    private List<SuperPeople> opponentFinder(List<SuperPeople> opponentList, Long level,
                                             Long desiredOpponentAmount) {
        List<SuperPeople> foundOpponents = new ArrayList<>();
        long opponentAmount = 0;
        for (SuperPeople opponent : opponentList) {
            if (opponent.getLevel() == level && opponentAmount <= desiredOpponentAmount) {
                foundOpponents.add(opponent);
                opponentAmount++;
            }
        }
        return foundOpponents;
    }

    private List<SuperPeople> opponentGenerator(List<SuperPeople> foundOpponents, SuperPeople superPerson,
                                                Long foundOpponentAmount, Long desiredOpponentAmount) {
        if (foundOpponentAmount == null) {
            List<SuperPeople> generatedOpponents = new ArrayList<>();
            int instance = 0;

            do {
                Random random = new Random();
                int range = 1;

                SuperPeople generatedOpponent = SuperPeople.builder()
                        .strength(superPerson.getStrength() - range + random.nextInt(2 * range + 1))
                        .dexterity(superPerson.getDexterity()-range+random.nextInt(2*range+1))
                        .level(superPerson.getLevel()-range+random.nextInt(2*range+1))
                        .constitution(superPerson.getConstitution()-range+ random.nextInt(2*range+1))
                        .build();

                generatedOpponents.add(generatedOpponent);

                instance++;
            } while (instance <= desiredOpponentAmount);

            return generatedOpponents;
        }
    }
}
