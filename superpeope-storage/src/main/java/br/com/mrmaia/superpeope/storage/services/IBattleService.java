package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public interface IBattleService {
    SuperPeople superHeroFinder(Long hero) throws SuperPeopleNotFoundException;

}
