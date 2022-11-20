package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String upload(@RequestParam MultipartFile fileUpload, Principal principal, RedirectAttributes redirectAttributes) {
        if(fileService.existingFile(fileUpload.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("error", "Filename already existed");
        }else {
            String username = principal.getName();
            fileService.save(userService.findByUsername(username).getUserid(), fileUpload);
            redirectAttributes.addFlashAttribute("message", "Added new file");
        }
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String viewFile(@PathVariable("id") int id, Model model, Principal principal){
        File file = fileService.findById(id);
        if(file.getUserid() != userService.findByUsername(principal.getName()).getUserid()){
            return "redirect:/home";
        }
        model.addAttribute("file", file);
        return "view-file";
    }

    @GetMapping("/{id}/delete")
    public String deleteFile(@PathVariable("id") int id, Model model, Principal principal, RedirectAttributes redirectAttributes){
        File file = fileService.findById(id);
        if(file.getUserid() == userService.findByUsername(principal.getName()).getUserid()){
            fileService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Deleted file: "+ file.getFilename());
        }
        return "redirect:/home";
    }
}
