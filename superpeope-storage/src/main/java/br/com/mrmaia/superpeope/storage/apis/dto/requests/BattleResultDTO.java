package br.com.mrmaia.superpeope.storage.apis.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BattleResultDTO {

    private Long winnerId;

    private Long loserId;


}
