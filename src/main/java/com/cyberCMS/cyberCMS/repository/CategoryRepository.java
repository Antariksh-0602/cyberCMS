package com.cyberCMS.cyberCMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberCMS.cyberCMS.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 🔥 for new category
    boolean existsByName(String name);

    // 🔥 for edit (exclude same record)
    boolean existsByNameAndIdNot(String name, Long id);
}