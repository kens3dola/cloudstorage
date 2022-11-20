package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public String viewCredential(@PathVariable("id")int id, Model model, Principal principal, RedirectAttributes redirectAttributes){
        Credential cre = credentialService.findById(id);
        int userid = userService.findByUsername(principal.getName()).getUserid();
        if (Objects.isNull(cre) || cre.getUserid() != userid) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized");
            return "redirect:/home";
        }
        model.addAttribute("credential", cre);
        return "view-credential";
    }

    @PostMapping
    public String createCredential(@ModelAttribute("credential") Credential credential, Principal principal, RedirectAttributes model) {
        int userid = userService.findByUsername(principal.getName()).getUserid();
        credential.setUserid(userid);
        if(credentialService.save(credential) <0){
            model.addFlashAttribute("error", "Error when saving credential");
        }else{
            model.addFlashAttribute("message", "Added new credential");
        }
        return "redirect:/home";
    }

    @GetMapping("/{credentialid}/delete")
    public String deleteCredential(@PathVariable("credentialid") int credentialid, Principal principal, RedirectAttributes redirectAttributes) {
        int userid = userService.findByUsername(principal.getName()).getUserid();
        Credential credential = credentialService.findById(credentialid);
        if (Objects.isNull(credential) || credential.getUserid() != userid) {
            redirectAttributes.addFlashAttribute("error", "Credential is not found");
        }else {
            credentialService.delete(credentialid);
            redirectAttributes.addFlashAttribute("message", "Deleted credential: "+ credential.getUrl());
        }
        return "redirect:/home";
    }

    @PostMapping("/{credentialid}/update")
    public String updateCredential(@PathVariable("credentialid") int credentialid, @ModelAttribute("credential") Credential credential, RedirectAttributes redirectAttributes){
        credential.setCredentialid(credentialid);
        credentialService.updateCredential(credential);
        redirectAttributes.addFlashAttribute("message", "Credential updated");
        return "redirect:/home";
    }
}
