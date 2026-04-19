package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.Policy;
import com.cyberCMS.cyberCMS.repository.PolicyRepository;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class PolicyController {

    @Autowired
    private PolicyRepository policyRepository;


    @GetMapping("/admin/policies")
    public String policies(Model model) {
        model.addAttribute("policy", new Policy());
        model.addAttribute("list", policyRepository.findAll());
        return "policies";
    }

    @PostMapping("/admin/policies/save")
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String title,
                       @RequestParam String description) {

        Policy p;

        if (id != null) {
            p = policyRepository.findById(id).orElse(new Policy());
            p.setUpdatedAt(LocalDateTime.now());
        } else {
            p = new Policy();
            p.setCreatedAt(LocalDateTime.now());
            p.setUpdatedAt(LocalDateTime.now());
        }

        p.setTitle(title);
        p.setDescription(description);
        p.setStatus("ACTIVE");

        policyRepository.save(p);

        return "redirect:/admin/policies";
    }

    @GetMapping("/admin/policies/delete/{id}")
    public String delete(@PathVariable Long id) {
        policyRepository.deleteById(id);
        return "redirect:/admin/policies";
    }
}