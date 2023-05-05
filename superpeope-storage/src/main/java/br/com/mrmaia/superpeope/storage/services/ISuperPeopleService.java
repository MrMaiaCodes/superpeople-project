package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public interface ISuperPeopleService extends IService<SuperPeople>{

    public List<SuperPeople> findSuperPeopleByName(String name) throws SuperPeopleNotFoundException;

    List<SuperPeople> listAll();

    SuperPeople update(SuperPeople superPeople) throws SuperPeopleNotFoundException,
            InvalidNameException, BattleAttributeWithValueZeroException,
            TotalBattleAttributesOverThirtyException;

    void delete(SuperPeople superPeople) throws SuperPeopleNotFoundException;
}
