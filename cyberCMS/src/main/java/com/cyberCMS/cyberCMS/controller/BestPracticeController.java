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
    // @PostMapping("/best-practice/save")
    // public String saveBestPractice(@ModelAttribute BestPractice bestPractice) {
    //     bestPractice.setCreatedAt(LocalDateTime.now());
    //     bestPractice.setUpdatedAt(LocalDateTime.now());
    //     bestPractice.setStatus("ACTIVE");

    //     bestPracticeRepository.save(bestPractice);
    //     return "redirect:/best-practice/list";
    // }
    // DELETE
    @GetMapping("/best-practice/delete/{id}")
    public String delete(@PathVariable Long id) {
        bestPracticeRepository.deleteById(id);
        return "redirect:/best-practice/list";
    }
    // EDIT FORM
    @GetMapping("/best-practice/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    BestPractice bp = bestPracticeRepository.findById(id).orElse(null);

        model.addAttribute("bestPractice", bp);
        model.addAttribute("categories", categoryRepository.findAll());

        return "best-practice-form";    
    }

    // UPDATE (reuse same save method but improve it)
    @PostMapping("/best-practice/save")
public String saveBestPractice(
        @RequestParam(required = false) Long id,
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam Long categoryId
) {

    BestPractice bp;

    if (id != null) {
        // EXISTING RECORD
        bp = bestPracticeRepository.findById(id).orElse(new BestPractice());
        bp.setUpdatedAt(LocalDateTime.now());
    } else {
        // NEW RECORD
        bp = new BestPractice();
        bp.setCreatedAt(LocalDateTime.now());
        bp.setUpdatedAt(LocalDateTime.now());
    }

    // SET VALUES
    bp.setTitle(title);
    bp.setDescription(description);

    // VERY IMPORTANT: fetch category from DB
    Category category = categoryRepository.findById(categoryId).orElse(null);
    bp.setCategory(category);

    bp.setStatus("ACTIVE");

    bestPracticeRepository.save(bp);

    return "redirect:/best-practice/list";
}

    // List page
    @GetMapping("/best-practice/list")
    public String list(Model model) {
        model.addAttribute("list", bestPracticeRepository.findAll());
        return "best-practice-list";
    }
}