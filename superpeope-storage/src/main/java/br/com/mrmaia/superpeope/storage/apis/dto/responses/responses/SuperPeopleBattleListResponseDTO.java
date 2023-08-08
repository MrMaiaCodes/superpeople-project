package br.com.mrmaia.superpeope.storage.apis.dto.responses.responses;

import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleBattleDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SuperPeopleBattleListResponseDTO {

    public List<SuperPeopleBattleDTO> data;
}
