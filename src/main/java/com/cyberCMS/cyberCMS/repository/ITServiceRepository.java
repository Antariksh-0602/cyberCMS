package com.cyberCMS.cyberCMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberCMS.cyberCMS.model.ITService;

public interface ITServiceRepository extends JpaRepository<ITService, Long> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}