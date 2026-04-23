package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.Category;
import com.cyberCMS.cyberCMS.repository.CategoryRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @PostMapping("/category/save")
    public String save(@ModelAttribute Category category, Model model) {

        // 🔥 DUPLICATE CHECK
        boolean exists;

        if (category.getId() == null) {
            exists = categoryRepository.existsByName(category.getName().trim());
        } else {
            exists = categoryRepository.existsByNameAndIdNot(
                    category.getName().trim(),
                    category.getId()
            );
        }

        if (exists) {
            model.addAttribute("error", "Category already exists!");
            model.addAttribute("category", new Category());
            model.addAttribute("categories", categoryRepository.findAll());
            return "categories"; // ❗ stay on same page
        }

        if (category.getId() == null) {
            category.setCreatedAt(LocalDateTime.now());
        }

        category.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(category);

        return "redirect:/admin/categories";
    }
    
    @GetMapping("/category/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(new Category());
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category-list";
    }
}