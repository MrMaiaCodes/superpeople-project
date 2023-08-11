package br.com.mrmaia.superpeope.storage.services.util;

import br.com.mrmaia.superpeope.storage.exceptions.BattleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.Battle;

import java.util.List;

public class BattleUtil {

    public static void battleFoundVerifier(List<Battle> battleList, String battleName)
        throws BattleNotFoundException {
        if (!StringUtil.validateStringIsNotNullOrBlank(battleName)) {
            throw new BattleNotFoundException("S05", "not found");
        }
        boolean found = battleList.stream().anyMatch(battle -> battle.getName().equals(battleName));
        if (!found) {
            throw new BattleNotFoundException("S05", "not found");
        }
    }
}
