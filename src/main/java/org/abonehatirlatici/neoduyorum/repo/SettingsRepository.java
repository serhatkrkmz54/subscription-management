package org.abonehatirlatici.neoduyorum.repo;

import org.abonehatirlatici.neoduyorum.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Optional<Settings> findByUserId(Long userId);


}
