package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.*;
import com.cyberCMS.cyberCMS.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("unused")
@Controller
public class TestimonialController {

    @Autowired
    private TestimonialRepository repo;

    @GetMapping("/admin/testimonials")
    public String testimonials(Model model) {
        model.addAttribute("testimonial", new Testimonial());
        model.addAttribute("list", repo.findAll());
        return "testimonial";
    }

    @PostMapping("/admin/testimonials/save")
    public String save(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String jobPost,
            @RequestParam String message,
            @RequestParam(required = false) MultipartFile imageFile,
            Model model
    ) {

        // 🚨 DUPLICATE CHECK (only for NEW entries)
    	boolean exists = repo.existsByName(name.trim());

    	if (id == null && exists) {
    	    model.addAttribute("error", "This person already has a testimonial!");
    	    model.addAttribute("testimonial", new Testimonial());
    	    model.addAttribute("list", repo.findAll());
    	    return "testimonial";
        }

        Testimonial t;

        if (id != null) {
            t = repo.findById(id).orElse(new Testimonial());
        } else {
            t = new Testimonial();
            t.setCreatedAt(LocalDateTime.now());
        }

        t.setName(name);
        t.setJobPost(jobPost);
        t.setMessage(message);

        // ✅ IMAGE UPLOAD (your code untouched)
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String original = imageFile.getOriginalFilename();
                String ext = (original != null && original.contains("."))
                        ? original.substring(original.lastIndexOf("."))
                        : "";

                String filename = UUID.randomUUID().toString() + ext;

                Path target = uploadDir.resolve(filename);

                imageFile.transferTo(target.toFile());

                t.setPhoto("/uploads/" + filename);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        t.setStatus("ACTIVE");

        repo.save(t);

        return "redirect:/admin/testimonials";
    }

    @GetMapping("/admin/testimonials/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Testimonial t = repo.findById(id).orElse(new Testimonial());
        model.addAttribute("testimonial", t);
        model.addAttribute("list", repo.findAll());
        return "testimonial";
    }

    @GetMapping("/admin/testimonials/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/testimonials";
    }
}