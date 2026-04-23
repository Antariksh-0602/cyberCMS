package com.cyberCMS.cyberCMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberCMS.cyberCMS.model.BestPractice;
import com.cyberCMS.cyberCMS.model.Category;

public interface BestPracticeRepository extends JpaRepository<BestPractice, Long> {

	boolean existsByTitleAndCategory(String title, Category category);

	boolean existsByTitleAndCategoryAndIdNot(String title, Category category, Long id);
}