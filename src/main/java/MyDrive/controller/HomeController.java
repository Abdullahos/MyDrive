package MyDrive.controller;


import MyDrive.security.EncryptionService;
import MyDrive.service.CredentialService;
import MyDrive.service.FileService;
import MyDrive.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    public HomeController() {
    }


    @GetMapping
    public String getHome(Authentication auth, Model model){
        final String username = auth.getName();
        model.addAttribute("files",fileService.findByUsername(username));
        model.addAttribute("notes", noteService.findByUserUsername(username));
        model.addAttribute("credentials", credentialService.getAllByUsername(username));
        //for decryption
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}

