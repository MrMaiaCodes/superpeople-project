package br.com.mrmaia.superpeope.storage.controlleres.service;

import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.TotalBattleAttributesOverThirtyException;

public interface IService<T> {

    T save(T t) throws InvalidNameException,
            TotalBattleAttributesOverThirtyException, BattleAttributeWithValueZeroException;


}
