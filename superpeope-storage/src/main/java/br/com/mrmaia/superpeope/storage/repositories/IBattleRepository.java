package br.com.mrmaia.superpeope.storage.repositories;

import br.com.mrmaia.superpeope.storage.repositories.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBattleRepository extends JpaRepository<Battle, Long> {

    @Query(value = "select d from Battle d where d.name")
    List<Battle> findBattleByName(String name);
}
