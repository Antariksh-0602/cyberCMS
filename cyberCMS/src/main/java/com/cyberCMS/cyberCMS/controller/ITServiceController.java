package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.ITService;
import com.cyberCMS.cyberCMS.repository.ITServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ITServiceController {

    @Autowired
    private ITServiceRepository repo;


    @GetMapping("/admin/it-services")
    public String services(Model model) {
        model.addAttribute("service", new ITService());
        model.addAttribute("list", repo.findAll());
        return "it-services";
    }
    
    @PostMapping("/admin/it-services/save")
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String title,
                       @RequestParam String description) {

        ITService s;

        if (id != null) {
            s = repo.findById(id).orElse(new ITService());
            s.setUpdatedAt(LocalDateTime.now());
        } else {
            s = new ITService();
            s.setCreatedAt(LocalDateTime.now());
            s.setUpdatedAt(LocalDateTime.now());
        }

        s.setTitle(title);
        s.setDescription(description);
        s.setStatus("ACTIVE");

        repo.save(s);

        return "redirect:/admin/it-services";
    }

    @GetMapping("/admin/it-services/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/it-services";
    }
}