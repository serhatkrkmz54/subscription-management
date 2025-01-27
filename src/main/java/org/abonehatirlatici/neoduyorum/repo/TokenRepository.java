package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.Token;
import org.abonehatirlatici.neoduyorum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
    void deleteByUser(User user);
}
