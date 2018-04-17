package com.crnjakovic.DAO;

import com.crnjakovic.model.Player;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
@Repository
@Transactional
public class PlayerInfoDAO implements IPlayerInfoDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public Player getActiveUser(String userName) {
        Player activeUserInfo = new Player();
        short enabled = 1;
        List<?> list = entityManager.createQuery("SELECT u FROM Player u WHERE userName=? and enabled=?")
                .setParameter(0, userName).setParameter(1, enabled).getResultList();
        if(!list.isEmpty()) {
            activeUserInfo = (Player) list.get(0);
        }
        return activeUserInfo;
    }
}
