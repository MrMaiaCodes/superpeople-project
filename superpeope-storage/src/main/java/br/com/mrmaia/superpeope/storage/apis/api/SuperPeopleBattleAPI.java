package br.com.mrmaia.superpeope.storage.apis.api;

import br.com.mrmaia.superpeope.storage.mappers.ISuperPeopleMapper;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("V1/super-people-battle")
public class SuperPeopleBattleAPI {

    private ISuperPeopleService superPeopleService;

    private ISuperPeopleMapper superPeopleMapper;

    @Autowired
    public SuperPeopleBattleAPI(ISuperPeopleService superPeopleService, ISuperPeopleMapper superPeopleMapper) {
        this.superPeopleService = superPeopleService;
        this.superPeopleMapper = superPeopleMapper;
    }


}
