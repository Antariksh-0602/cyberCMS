package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.repository.BestPracticeRepository;
import com.cyberCMS.cyberCMS.repository.CategoryRepository;
import com.cyberCMS.cyberCMS.repository.PolicyRepository;
import com.cyberCMS.cyberCMS.repository.ITServiceRepository;
import com.cyberCMS.cyberCMS.repository.TestimonialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private BestPracticeRepository bestPracticeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ITServiceRepository itServiceRepository;

    @Autowired
    private TestimonialRepository testimonialRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("bestPractices", bestPracticeRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("policies", policyRepository.findAll());
        model.addAttribute("services", itServiceRepository.findAll());
        model.addAttribute("testimonials", testimonialRepository.findAll());

        return "dashboard";
    }
}