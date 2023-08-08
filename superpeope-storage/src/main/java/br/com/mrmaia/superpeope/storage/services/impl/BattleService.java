package br.com.mrmaia.superpeope.storage.services.impl;

import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.IBattleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<SuperPeople> lowerLevelOpponentFinder(List<SuperPeople> opponentList, Long level) {
        return opponentList;

    }
}
