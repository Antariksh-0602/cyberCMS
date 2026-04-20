package com.cyberCMS.cyberCMS.controller;

import com.cyberCMS.cyberCMS.model.Policy;
import com.cyberCMS.cyberCMS.repository.PolicyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PolicyController {

    @Autowired
    private PolicyRepository policyRepository;

    // 👉 MAIN PAGE
    @GetMapping("/admin/policies")
    public String policies(Model model) {
        model.addAttribute("policy", new Policy());
        model.addAttribute("list", policyRepository.findAll());
        return "policies";
    }

    // 👉 SAVE (CREATE + UPDATE)
    @PostMapping("/admin/policies/save")
    public String save(@RequestParam(required = false) Long id,
                       @RequestParam String title,
                       @RequestParam String description,
                       @RequestParam(required = false) MultipartFile imageFile,
                       @RequestParam String link) {

        Policy p;

        // ✅ CREATE / UPDATE LOGIC
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
        p.setLink(link);
        p.setStatus("ACTIVE");

        // ✅ IMAGE UPLOAD (CLEAN + SAFE)
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
                p.setImage("/uploads/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save uploaded image", e);
            }
        }
        // ✅ KEEP OLD IMAGE IF NO NEW IMAGE
        else if (id != null) {
            Policy existing = policyRepository.findById(id).orElse(null);
            if (existing != null) {
                p.setImage(existing.getImage());
            }
        }

        policyRepository.save(p);

        return "redirect:/admin/policies";
    }

    // 👉 EDIT
    @GetMapping("/admin/policies/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Policy p = policyRepository.findById(id).orElse(new Policy());

        model.addAttribute("policy", p);
        model.addAttribute("list", policyRepository.findAll());

        return "policies";
    }

    // 👉 DELETE
    @GetMapping("/admin/policies/delete/{id}")
    public String delete(@PathVariable Long id) {
        policyRepository.deleteById(id);
        return "redirect:/admin/policies";
    }
}