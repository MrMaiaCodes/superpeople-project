package br.com.mrmaia.superpeope.storage.apis.api;

import br.com.mrmaia.superpeope.storage.adapters.SuperPeopleAdapter;
import br.com.mrmaia.superpeope.storage.adapters.SuperPeopleDTOAdapter;
import br.com.mrmaia.superpeope.storage.apis.ISuperPeopleAPI;
import br.com.mrmaia.superpeope.storage.apis.ISuperPeopleMapper;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.DeleteResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleListResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleResponseDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("V1/super-people")
public class SuperPeopleAPI implements ISuperPeopleAPI {

    @Autowired
    private ISuperPeopleService superPeopleService;

    @PostMapping("/new")
    public SuperPeopleResponseDTO add(@RequestBody SuperPeopleDTO superPeopleDTO)
            throws InvalidNameException, BattleAttributeWithValueZeroException,
            ExcessiveTotalBattleAttributesException {

        SuperPeople superPeople = ISuperPeopleMapper.INSTANCE.convertToEntity(superPeopleDTO);
        SuperPeople savedSuperPeople = superPeopleService.save(superPeople);
        SuperPeopleDTO savedSuperPeopleDTO = ISuperPeopleMapper.INSTANCE.convertToDto(savedSuperPeople);

        return SuperPeopleResponseDTO.builder()
                .data(
                        savedSuperPeopleDTO
                       /* SuperPeopleDTOAdapter.convertTo(
                                superPeopleService.save(SuperPeopleAdapter.convertTo(superPeopleDTO))

                        */
                        )
                .build();
    }

    @GetMapping("/find/{super-people}")
    public SuperPeopleListResponseDTO find(@PathVariable("super-people") String heroName)
        throws SuperPeopleNotFoundException {

        List<SuperPeople> superPeopleList = superPeopleService.findSuperPeopleByName(heroName);
        List<SuperPeopleDTO> superPeopleDTOlist = ISuperPeopleMapper.INSTANCE.convertToListDto(superPeopleList);
        return SuperPeopleListResponseDTO.builder()
                .data(superPeopleDTOlist
                        /*SuperPeopleDTOAdapter.convertToList(
                                superPeopleService.findSuperPeopleByName(
                                        heroName)
                        )

                         */
                ).build();
    }

    @GetMapping("/list")
    public SuperPeopleListResponseDTO listAll() {

        List<SuperPeople> superPeopleList = superPeopleService.listAll();
        List<SuperPeopleDTO> superPeopleDTOlist = ISuperPeopleMapper.INSTANCE.convertToListDto(
                superPeopleList);
        return SuperPeopleListResponseDTO.builder()
                .data(superPeopleDTOlist
                        /*SuperPeopleDTOAdapter.convertToList(
                                superPeopleService.listAll()
                        )

                         */
                ).build();
    }

    @PutMapping("/change/super-people")
    public SuperPeopleResponseDTO update(@RequestBody SuperPeopleDTO superPeopleDTO)
        throws SuperPeopleNotFoundException, InvalidNameException {

        SuperPeople superPeople = ISuperPeopleMapper.INSTANCE.convertToEntity(superPeopleDTO);
        SuperPeople updatedSuperPeople = superPeopleService.update(superPeople);
        SuperPeopleDTO updatedSuperPeopleDTO = ISuperPeopleMapper.INSTANCE.convertToDto(updatedSuperPeople);
        return SuperPeopleResponseDTO.builder()
                .data(updatedSuperPeopleDTO
                        /*SuperPeopleDTOAdapter.convertTo(
                                superPeopleService.update(
                                        SuperPeopleAdapter.convertTo(superPeopleDTO)
                                )
                        )

                         */
                ).build();
    }
/*
    @PutMapping("/battle-result/super-people")
    public BattleResultResponseDTO battleExperienceAndLevelApplier(@RequestBody SuperPeopleDTO battleResultDTO)
        throws SuperPeopleNotFoundException {

        return BattleResultResponseDTO.builder()
                .data(
                        SuperPeopleDTOAdapter.convertTo(
                                superPeopleService.experienceAndLevelApplier(battleResultDTO)
                        )
                ).build();
    }

 */
    @DeleteMapping("/{superPeopleId}")
    public ResponseEntity<DeleteResponseDTO> delete(@PathVariable("superPeopleId") Long superPeopleId)
        throws SuperPeopleNotFoundException {
        superPeopleService.delete(SuperPeople.builder().id(superPeopleId).build());

        return ResponseEntity.ok(DeleteResponseDTO.builder()
                .deleteSuccessMessage("SuperPerson successfully deleted")
                .build());
    }
}
