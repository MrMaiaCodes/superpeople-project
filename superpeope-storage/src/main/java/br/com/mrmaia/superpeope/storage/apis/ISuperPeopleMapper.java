package br.com.mrmaia.superpeope.storage.apis;

import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ISuperPeopleMapper {

    ISuperPeopleMapper INSTANCE = Mappers.getMapper(ISuperPeopleMapper.class);

    SuperPeopleDTO convertToDto(SuperPeople superPeople);

    SuperPeople convertToEntity(SuperPeopleDTO superPeopleDTO);

    List<SuperPeopleDTO> convertToListDto(List<SuperPeople> superPeopleList);
}
