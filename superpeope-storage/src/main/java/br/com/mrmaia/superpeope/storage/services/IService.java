package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public interface IService<T> {

    T save(T t) throws InvalidNameException,
            ExcessiveTotalBattleAttributesException, BattleAttributeWithValueZeroException;

    SuperPeople update(T t) throws SuperPeopleNotFoundException,
            InvalidNameException;

    void delete(T t) throws SuperPeopleNotFoundException;

    List<SuperPeople> listAll();
}
