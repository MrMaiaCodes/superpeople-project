package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.BattleNotFoundException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.Battle;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

import java.util.List;

public interface IBattleService {

    List<Battle> findBattleByName(String battleName) throws BattleNotFoundException;

    Battle battleOrganizer(SuperPeople combatantOne) throws SuperPeopleNotFoundException;
}
