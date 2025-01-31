package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.StaticSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaticSubscriptionRepo extends JpaRepository<StaticSubscription, Long> {

    @Query("SELECT s FROM StaticSubscription s ORDER BY s.sAbonelikAdi")
    List<StaticSubscription> findAllOrderedByName();

}
