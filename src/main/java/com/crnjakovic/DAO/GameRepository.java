package com.crnjakovic.DAO;

import com.crnjakovic.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lukacrnjakovic on 4/18/18.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
