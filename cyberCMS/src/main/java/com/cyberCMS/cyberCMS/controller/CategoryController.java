package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.Category;
import com.cyberCMS.cyberCMS.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/add")
    public String showForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @PostMapping("/category/save")
    public String save(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/category/list";
    }

    @GetMapping("/category/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category-list";
    }
}