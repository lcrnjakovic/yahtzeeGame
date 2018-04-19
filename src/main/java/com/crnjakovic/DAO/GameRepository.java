package com.crnjakovic.DAO;

import com.crnjakovic.model.Game;
import com.crnjakovic.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE firstPlayer=?1 OR secondPlayer=?1")
    Game findGameByUser(Player user);
}
