package br.com.mrmaia.superpeope.storage.services.impl;

import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import br.com.mrmaia.superpeope.storage.services.ISuperPowerService;
import br.com.mrmaia.superpeope.storage.services.util.SuperPeopleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SuperPeopleService implements ISuperPeopleService {

    public static final long TOTAL_BATTLE_ATTRIBUTES = 30;

    @Autowired
    private ISuperPeopleRepository superPeopleRepository;

    @Autowired
    private ISuperPowerService superPowerService;

    @Override
    public SuperPeople save(SuperPeople superPeople) throws InvalidNameException,
            TotalBattleAttributesOverThirtyException, BattleAttributeWithValueZeroException {
        log.info("initialized SuperPeopleService.save");
        newSuperPeopleValidator(superPeople);
        return superPeopleRepository.save(superPeople);
    }

    private void newSuperPeopleValidator(SuperPeople superPeople) throws InvalidNameException,
            TotalBattleAttributesOverThirtyException, BattleAttributeWithValueZeroException {
        SuperPeopleUtil.superPeopleNameNullVerifier(superPeople.getName());
        SuperPeopleUtil.superPeopleTotalAttributeValueVerifier(
                SuperPeopleUtil.attributeSum(
                        superPeople.getStrength(), superPeople.getConstitution(),
                        superPeople.getDexterity(), superPeople.getIntelligence(),
                        superPeople.getWisdom(), superPeople.getCharisma()
                ), TOTAL_BATTLE_ATTRIBUTES
        );
        SuperPeopleUtil.superPeopleNoZeroValuesVerifier(
                superPeople.getStrength(), superPeople.getConstitution(),
                superPeople.getDexterity(), superPeople.getIntelligence(),
                superPeople.getWisdom(), superPeople.getCharisma()
        );
    }

    public List<SuperPeople> findSuperPeopleByName(String name) throws SuperPeopleNotFoundException {
        log.info("initialized SuperPeopleService.findSuperPeopleByName");
        var heroFind = superPeopleRepository.findSuperPeopleByName(name);
        SuperPeopleUtil.superPeopleFoundVerifier(name);
        return heroFind;
    }




}
