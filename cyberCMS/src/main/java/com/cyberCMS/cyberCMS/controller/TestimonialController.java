package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.Testimonial;
import com.cyberCMS.cyberCMS.repository.TestimonialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String name,
                       @RequestParam String message) {

        Testimonial t;

        if (id != null) {
            t = repo.findById(id).orElse(new Testimonial());
        } else {
            t = new Testimonial();
            t.setCreatedAt(LocalDateTime.now());
        }

        t.setName(name);
        t.setMessage(message);
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