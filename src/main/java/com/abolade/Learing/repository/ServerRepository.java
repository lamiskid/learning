package com.abolade.Learing.repository;

import com.abolade.Learing.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server,Long> {

    Server findByIpAddress(String ipAddress);
}
