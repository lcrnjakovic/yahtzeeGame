package com.crnjakovic.DAO;

import com.crnjakovic.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT u FROM Player u WHERE userName= ?1")
    Player findByUsername(String userName);
}
