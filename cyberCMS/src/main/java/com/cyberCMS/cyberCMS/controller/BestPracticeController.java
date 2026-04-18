package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.*;
import com.cyberCMS.cyberCMS.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class BestPracticeController {

    @Autowired
    private BestPracticeRepository bestPracticeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Show form
    @GetMapping("/best-practice/add")
    public String showForm(Model model) {
        model.addAttribute("bestPractice", new BestPractice());
        model.addAttribute("categories", categoryRepository.findAll());
        return "best-practice-form";
    }

    // Save data
    @PostMapping("/best-practice/save")
    public String saveBestPractice(@ModelAttribute BestPractice bestPractice) {
        bestPractice.setCreatedAt(LocalDateTime.now());
        bestPractice.setUpdatedAt(LocalDateTime.now());
        bestPractice.setStatus("ACTIVE");

        bestPracticeRepository.save(bestPractice);
        return "redirect:/best-practice/list";
    }

    // List page
    @GetMapping("/best-practice/list")
    public String list(Model model) {
        model.addAttribute("list", bestPracticeRepository.findAll());
        return "best-practice-list";
    }
}