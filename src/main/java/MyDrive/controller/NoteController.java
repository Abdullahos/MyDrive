package MyDrive.controller;

import MyDrive.Messages.ErrorAndWarningMessages;
import MyDrive.Messages.SuccessMessages;
import MyDrive.models.Note;
import MyDrive.models.User;
import MyDrive.service.NoteService;
import MyDrive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String addNote(@ModelAttribute Note note, Authentication auth, RedirectAttributes redirectAttributes){
        final Note noteByUsernameAndTitle = noteService.findByUsernameAndTitle(auth.getName(), note.getTitle());
        //duplicate Note title
        if(noteByUsernameAndTitle!=null){
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.DUPLICATE_NOTE_TITLE);
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("success", true);
        //create note
        if(note.getId()==null)         redirectAttributes.addFlashAttribute("message", SuccessMessages.NOTE_CREATE_SUCCESS_MESSAGE);
        //edit note
        else         redirectAttributes.addFlashAttribute("message", SuccessMessages.NOTE_EDIT_SUCCESS_MESSAGE);
        final Note savedNote = noteService.save(note, auth.getName());
        return "redirect:/home";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes,Authentication auth){
        final User userByNoteId = userService.findByNoteId(id);
        if(!userByNoteId.getUsername().equals(auth.getName()))   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        final boolean isDeleted = noteService.delete(id);
        if(isDeleted)   {
            redirectAttributes.addFlashAttribute("success",true);
            redirectAttributes.addFlashAttribute("message", SuccessMessages.NOTE_DELETE_SUCCESS_MESSAGE);
        }
        else redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.ERROR_ON_DELETING);
        return "redirect:/home";
    }
}
