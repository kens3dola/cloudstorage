package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/{noteid}")
    public String viewNote(@PathVariable("noteid") int noteid, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        Note note = noteService.findById(noteid);
        int userid = userService.findByUsername(principal.getName()).getUserid();
        if (Objects.isNull(note) || note.getUserid() != userid) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized");
            return "redirect:/home";
        }
        model.addAttribute("note", note);
        return "view-note";
    }

    @PostMapping
    public String createNote(@ModelAttribute("note") Note note, Principal principal, RedirectAttributes model) {
        int userid = userService.findByUsername(principal.getName()).getUserid();
        note.setUserid(userid);
        if(noteService.save(note) <0){
            model.addFlashAttribute("error", "Error when saving note");
        }else{
            model.addFlashAttribute("message", "Added new note");
        }
        return "redirect:/home";
    }

    @GetMapping("/{noteid}/delete")
    public String deleteNote(@PathVariable("noteid") int noteid, Principal principal, RedirectAttributes redirectAttributes) {
        int userid = userService.findByUsername(principal.getName()).getUserid();
        Note note = noteService.findById(noteid);
        if (Objects.isNull(note) || note.getUserid() != userid) {
            redirectAttributes.addFlashAttribute("error", "Note is not found");
        }else {
            noteService.delete(noteid);
            redirectAttributes.addFlashAttribute("message", "Deleted note: "+ note.getNotetitle());
        }
        return "redirect:/home";
    }

    @PostMapping("/{noteid}/update")
    public String updateNote(@PathVariable("noteid") int noteid, @ModelAttribute("note") Note note, RedirectAttributes redirectAttributes){
        note.setNoteid(noteid);
        noteService.updateNote(note);
        redirectAttributes.addFlashAttribute("message", "Note updated");
        return "redirect:/home";
    }
}
