package com.cyberCMS.cyberCMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cyberCMS.cyberCMS.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
}