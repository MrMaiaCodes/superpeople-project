package br.com.mrmaia.superpeope.storage.services.impl;

import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
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


    @Override
    public SuperPeople save(SuperPeople superPeople) throws InvalidNameException,
            ExcessiveTotalBattleAttributesException, BattleAttributeWithValueZeroException {
        log.info("initialized SuperPeopleService.save");
        newSuperPeopleValidator(superPeople);
        log.info("save successful");
        return superPeopleRepository.save(superPeople);
    }

    private void newSuperPeopleValidator(SuperPeople superPeople) throws InvalidNameException,
            ExcessiveTotalBattleAttributesException, BattleAttributeWithValueZeroException {
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

    private void levelUpValidator(SuperPeople superPeople, Long newLevel)
            throws ExcessiveTotalBattleAttributesException, BattleAttributeWithValueZeroException {
        SuperPeopleUtil.superPeopleTotalAttributeValueVerifier(
                SuperPeopleUtil.attributeSum(
                        superPeople.getStrength(), superPeople.getConstitution(),
                        superPeople.getDexterity(), superPeople.getIntelligence(),
                        superPeople.getWisdom(), superPeople.getCharisma()
                ), newLevel
        );
        SuperPeopleUtil.superPeopleNoZeroValuesVerifier(
                superPeople.getStrength(), superPeople.getConstitution(),
                superPeople.getDexterity(), superPeople.getIntelligence(),
                superPeople.getWisdom(), superPeople.getCharisma()
        );
    }

    private Long pointAdder(Long number) {
        return TOTAL_BATTLE_ATTRIBUTES + ((number * 5) - 5);
    }

    @Override
    public SuperPeople experienceAndLevelApplier(SuperPeople superPeople, Long xpGained)
            throws SuperPeopleNotFoundException {
        log.info("initialized xpAndLevelApplier");
        experienceAdder(superPeople, xpGained);
        nextLevelCalculator(superPeople);
        log.info("successfully concluded xpAndLevelApplier");
        return superPeople;
    }

    private SuperPeople experienceAdder(SuperPeople superPeople, Long xpGained)
            throws SuperPeopleNotFoundException {
        log.info("initialized superPeopleService.xpAdder");
        var heroToEvolve = superPeopleRepository.findById(superPeople.getId())
                .orElseThrow(() -> new SuperPeopleNotFoundException("S04", "not found"));
        log.info("executing xpAdder");
        heroToEvolve.setCurrentExperience(superPeople.getCurrentExperience() + xpGained);
        log.info("successfully finished xpAdder");
        return heroToEvolve;

    }
    private void nextLevelCalculator(SuperPeople superPeople) {
        log.info("initialized superPeopleService.nextLevelCalculator");
        if (superPeople.getCurrentExperience() >= superPeople.getNextLevelExperience()) {
            superPeople.incrementLevel(superPeople);
            Long extraXp = superPeople.getCurrentExperience() - superPeople.getNextLevelExperience();
            superPeople.setNextLevelExperience(superPeople.getNextLevelExperience()
                    +(superPeople.getLevel() * 100));
            superPeople.setCurrentExperience(extraXp);
        }
        log.info("successfully completed nextLevelCalculator");
    }



    @Override
    public List<SuperPeople> findSuperPeopleByName(SuperPeople superPeople)
            throws SuperPeopleNotFoundException {
        log.info("initialized SuperPeopleService.findSuperPeopleByName");
        var heroFind = superPeopleRepository.findSuperPeopleByName(superPeople.getName());
        SuperPeopleUtil.superPeopleFoundVerifier(superPeople.getName());
        log.info("find successful");
        return heroFind;
    }

    @Override
    public List<SuperPeople> listAll() {
        log.info("initialized SuperPeopleService.listAll");
        log.info("listAll complete");
        return superPeopleRepository.findAll();
    }

    //change this so every time the person levels up they get an extra 5 points to place as they choose
    @Override
    public SuperPeople update(SuperPeople superPeople) throws SuperPeopleNotFoundException {
        log.info("initialized SuperPeopleService.update");
        var heroFind = superPeopleRepository.findById(superPeople.getId())
                .orElseThrow(() -> new SuperPeopleNotFoundException("S04", "not found")
                );
        log.info("processing update");
        heroFind.setName(superPeople.getName());
        heroFind.setPlanet(superPeople.getPlanet());
        heroFind.setType(superPeople.getType());

        log.info("update complete");
        return superPeopleRepository.save(heroFind);
    }

    ///private Long pointAdder(Long number) {
//        return TOTAL_BATTLE_ATTRIBUTES + ((number*5)-5);
//    }


    @Override
    public void delete(SuperPeople superPeople) throws SuperPeopleNotFoundException {
        log.info("initialized superPeopleService.delete");
        var heroDelete = superPeopleRepository.findById(
                superPeople.getId()).orElseThrow(() -> new SuperPeopleNotFoundException(
                "S04", "not found"));
        log.info("processing delete");
        superPeopleRepository.delete(heroDelete);
        log.info("delete completed");
    }




}
