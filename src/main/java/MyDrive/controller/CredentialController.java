package MyDrive.controller;


import MyDrive.Messages.ErrorAndWarningMessages;
import MyDrive.Messages.SuccessMessages;
import MyDrive.models.Credential;
import MyDrive.models.User;
import MyDrive.service.CredentialService;
import MyDrive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("credentials")
public class CredentialController {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public String save(@ModelAttribute Credential credential, Authentication auth, RedirectAttributes redirectAttributes){
        //create
        if(credential.getId()==null)    redirectAttributes.addFlashAttribute("message", SuccessMessages.CREDENTIAL_CREATE_SUCCESS_MESSAGE);
        //edit exiting credential
        else redirectAttributes.addFlashAttribute("message", SuccessMessages.CREDENTIAL_EDIT_SUCCESS_MESSAGE);
        final Credential savedCredential = credentialService.save(credential, auth.getName());
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Authentication auth){
        final User userByNoteId = userService.findByCredentialId(id);
        if(!userByNoteId.getUsername().equals(auth.getName()))   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        final boolean isDeleted = credentialService.delete(id);
        if(isDeleted)   {
            redirectAttributes.addFlashAttribute("success",true);
            redirectAttributes.addFlashAttribute("message", SuccessMessages.CREDENTIAL_DELETE_SUCCESS_MESSAGE);
        }
        else redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.ERROR_ON_DELETING);
        return "redirect:/home";
    }

}
