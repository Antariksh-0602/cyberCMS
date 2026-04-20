package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.*;
import com.cyberCMS.cyberCMS.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class BestPracticeController {

    @Autowired
    private BestPracticeRepository bestPracticeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // MAIN PAGE (ADD + LIST)
    @GetMapping("/admin/best-practices")
    public String bestPractices(Model model) {

        model.addAttribute("bestPractice", new BestPractice());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("list", bestPracticeRepository.findAll());

        return "best-practices";
    }

    // SAVE (CREATE + UPDATE)
    @PostMapping("/admin/best-practices/save")
    public String save(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String link,
            @RequestParam Long categoryId
    ) {

        BestPractice bp;

        if (id != null) {
            bp = bestPracticeRepository.findById(id).orElse(new BestPractice());
            bp.setUpdatedAt(LocalDateTime.now());
        } else {
            bp = new BestPractice();
            bp.setCreatedAt(LocalDateTime.now());
            bp.setUpdatedAt(LocalDateTime.now());
        }

        bp.setTitle(title);
        bp.setDescription(description);
        bp.setLink(link);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String originalFilename = imageFile.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                }
                String filename = UUID.randomUUID().toString() + extension;
                Path targetPath = uploadDir.resolve(filename);
                imageFile.transferTo(targetPath.toFile());
                bp.setImage("/uploads/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save uploaded image", e);
            }
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        bp.setCategory(category);

        bp.setStatus("ACTIVE");

        bestPracticeRepository.save(bp);

        return "redirect:/admin/best-practices";
    }

    // DELETE
    @GetMapping("/admin/best-practices/delete/{id}")
    public String delete(@PathVariable Long id) {
        bestPracticeRepository.deleteById(id);
        return "redirect:/admin/best-practices";
    }

    // EDIT (LOAD DATA IN SAME PAGE)
    @GetMapping("/admin/best-practices/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        BestPractice bp = bestPracticeRepository.findById(id).orElse(new BestPractice());

        model.addAttribute("bestPractice", bp);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("list", bestPracticeRepository.findAll());

        return "best-practices";
    }
}