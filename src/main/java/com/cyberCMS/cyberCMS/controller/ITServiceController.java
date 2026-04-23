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
                       @RequestParam String description,
                       @RequestParam String securityProvisions,
                       @RequestParam String dos,
                       @RequestParam String donts,
                       Model model) {

        // 🔥 DUPLICATE CHECK
        boolean exists;

        if (id == null) {
            exists = repo.existsByTitle(title.trim());
        } else {
            exists = repo.existsByTitleAndIdNot(title.trim(), id);
        }

        if (exists) {
            model.addAttribute("error", "IT Service with this title already exists!");
            model.addAttribute("service", new ITService());
            model.addAttribute("list", repo.findAll());
            return "it-services"; // ❗ IMPORTANT: not redirect
        }

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
        s.setSecurityProvisions(securityProvisions);
        s.setDos(dos);
        s.setDonts(donts);
        s.setStatus("ACTIVE");

        repo.save(s);

        return "redirect:/admin/it-services";
    }

    // EDIT
    @GetMapping("/admin/it-services/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        ITService s = repo.findById(id).orElse(new ITService());

        model.addAttribute("service", s);
        model.addAttribute("list", repo.findAll());

        return "it-services";
    }

    // DELETE
    @GetMapping("/admin/it-services/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/it-services";
    }
}