package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.*;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public interface ISuperPeopleService extends IService<SuperPeople> {


    SuperPeople experienceAndLevelApplier(SuperPeople superPeople, Long xpGained)
            throws SuperPeopleNotFoundException;

    List<SuperPeople> findSuperPeopleByName(SuperPeople superPeople) throws SuperPeopleNotFoundException;

}



