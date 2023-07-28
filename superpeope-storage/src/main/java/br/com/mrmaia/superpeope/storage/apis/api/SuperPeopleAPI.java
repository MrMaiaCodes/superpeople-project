package br.com.mrmaia.superpeope.storage.apis.api;

import br.com.mrmaia.superpeope.storage.apis.ISuperPeopleAPI;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.BattleResultDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.DeleteResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleListResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleResponseDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.mappers.ISuperPeopleMapper;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("V1/super-people")
public class SuperPeopleAPI implements ISuperPeopleAPI {


    private ISuperPeopleService superPeopleService;

    private ISuperPeopleMapper superPeopleMapper;

    @Autowired
    public SuperPeopleAPI(ISuperPeopleService superPeopleService, ISuperPeopleMapper superPeopleMapper) {
        this.superPeopleService = superPeopleService;
        this.superPeopleMapper = superPeopleMapper;
    }

    @PostMapping("/new")
    public SuperPeopleResponseDTO add(@RequestBody SuperPeopleDTO superPeopleDTO)
            throws InvalidNameException, BattleAttributeWithValueZeroException,
            ExcessiveTotalBattleAttributesException {

        return SuperPeopleResponseDTO.builder()
                .data(
                        superPeopleMapper.convertToDto(
                                superPeopleService.save(
                                        superPeopleMapper.convertToEntity(superPeopleDTO)
                                )
                        )
                )
                .build();
    }

    @GetMapping("/find/{super-people}")
    public SuperPeopleListResponseDTO find(@PathVariable("super-people") String heroName)
            throws SuperPeopleNotFoundException {

        List<SuperPeople> superPeopleList = superPeopleService.findSuperPeopleByName(heroName);
        List<SuperPeopleDTO> superPeopleDTOList = superPeopleMapper.convertToListDto(superPeopleList);
        return SuperPeopleListResponseDTO.builder()
                .data(superPeopleDTOList
                ).build();
    }

    @GetMapping("/list")
    public SuperPeopleListResponseDTO listAll() {

        List<SuperPeople> superPeopleList = superPeopleService.listAll();
        List<SuperPeopleDTO> superPeopleDTOList = superPeopleMapper.convertToListDto(
                superPeopleList);
        return SuperPeopleListResponseDTO.builder()
                .data(superPeopleDTOList
                ).build();
    }

    @PutMapping("/change/super-people/{superPeopleId}")
    public SuperPeopleResponseDTO update(@PathVariable("superPeopleId") Long superPeopleId,
                                         @RequestBody SuperPeopleDTO superPeopleDTO)
            throws SuperPeopleNotFoundException, InvalidNameException {

        return SuperPeopleResponseDTO.builder()
                .data(
                        superPeopleMapper.convertToDto(
                                superPeopleService.update(
                                        superPeopleId, superPeopleMapper.convertToEntity(
                                                superPeopleDTO)
                                )
                        )
                ).build();
    }

    @PutMapping("/battle-result/super-people")
    public ResponseEntity battleExperienceAndLevelApplier(@RequestBody BattleResultDTO battleResultDTO)
            throws SuperPeopleNotFoundException {

        superPeopleService.experienceAndLevelApplier(battleResultDTO.getWinnerId(), true);
        superPeopleService.experienceAndLevelApplier(battleResultDTO.getLoserId(), false);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{superPeopleId}")
    public ResponseEntity<DeleteResponseDTO> delete(@PathVariable("superPeopleId") Long superPeopleId)
            throws SuperPeopleNotFoundException {
        superPeopleService.delete(SuperPeople.builder().id(superPeopleId).build());

        return ResponseEntity.ok(DeleteResponseDTO.builder()
                .deleteSuccessMessage("SuperPerson successfully deleted")
                .build());
    }
}
