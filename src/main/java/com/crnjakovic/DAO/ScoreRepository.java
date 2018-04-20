package com.crnjakovic.DAO;

import com.crnjakovic.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lukacrnjakovic on 4/20/18.
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
