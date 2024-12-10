package com.ticketingplatform.ticketing_platform.Repository;

import com.ticketingplatform.ticketing_platform.Models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findAllByEventName(String eventName);
    Configuration findFirstByEventName(String eventName);
}
