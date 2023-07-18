package br.com.mrmaia.superpeope.storage.model;

import br.com.mrmaia.superpeope.storage.apis.dto.requests.BattleResultDTO;

public class BattleResultDTOBuilder {

    public static BattleResultDTO battleResultDTOBuilderSuccess() {
        return BattleResultDTO.builder().winnerId(1L).loserId(2L).build();
    }
}
