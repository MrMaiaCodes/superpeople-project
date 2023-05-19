package br.com.mrmaia.superpeope.storage.services;

import br.com.mrmaia.superpeope.storage.exceptions.SuperPowerNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPower;

public interface ISuperPowerService {

    SuperPower findSuperPowerByName(String name) throws SuperPowerNotFoundException;
}
