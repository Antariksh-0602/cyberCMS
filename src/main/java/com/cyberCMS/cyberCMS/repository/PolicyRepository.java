package com.cyberCMS.cyberCMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberCMS.cyberCMS.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}