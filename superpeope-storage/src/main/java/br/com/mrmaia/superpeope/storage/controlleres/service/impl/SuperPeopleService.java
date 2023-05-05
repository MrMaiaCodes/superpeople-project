package br.com.mrmaia.superpeope.storage.controlleres.service.impl;

import br.com.mrmaia.superpeope.storage.controlleres.service.AbstractValidateService;
import br.com.mrmaia.superpeope.storage.controlleres.service.ISuperPeopleService;
import br.com.mrmaia.superpeope.storage.controlleres.service.ISuperPowerService;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;
import br.com.mrmaia.superpeope.storage.repositories.ISuperPeopleRepository;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SuperPeopleService extends AbstractValidateService<SuperPeople> implements ISuperPeopleService {

    @Autowired
    private ISuperPeopleRepository superPeopleRepository;

    @Autowired
    private ISuperPowerService superPowerService;

    @Override
    public SuperPeople save(SuperPeople superPeople) throws InvalidNameException,
            TotalBattleAttributesOverThirtyException, BattleAttributeWithValueZeroException {
        log.info("initialized SuperPeopleService.save");
        Long sum = superPeople.getStrength() + superPeople.getConstitution() + superPeople.getDexterity()
                + superPeople.getIntelligence() + superPeople.getWisdom() + superPeople.getCharisma();
        long[] superAttributes = {superPeople.getStrength(), superPeople.getConstitution(),
                superPeople.getDexterity(), superPeople.getIntelligence(), superPeople.getWisdom(),
                superPeople.getCharisma()};
        boolean noneMatch = Arrays.stream(superAttributes).noneMatch(value -> value == 0);
        if (validate(superPeople)) {
            if (sum <= 30) {
                if (noneMatch) {
                    log.info("Processing save");
                    superPeopleRepository.save(superPeople);
                    log.info("save complete");
                    return superPeople;
                } else {
                    log.error("validation failed, all battle attributes must have a value of at least 1.");
                    throw new BattleAttributeWithValueZeroException(
                            "S03", "All battle attributes must have a value of at least 1.");
                }
            } else {
                log.error("validation failed, Total battle attributes must not exceed 30");
                throw new TotalBattleAttributesOverThirtyException(
                        "S02", "Total battle attributes must not exceed 30.");
            }
        } else {
            log.error("Invalid name, validation failed");
            throw new InvalidNameException("S01", "invalid name");
        }
    }

    @Override
    protected boolean validate(SuperPeople superPeople) {
        return !validateStringIsNullOrBlank(superPeople.getName());
    }

}
