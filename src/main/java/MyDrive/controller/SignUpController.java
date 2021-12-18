package MyDrive.controller;

import MyDrive.Messages.ErrorAndWarningMessages;
import MyDrive.Messages.SuccessMessages;
import MyDrive.models.User;
import MyDrive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("signup")
public class SignUpController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String signup(){
        return "signup";
    }
    @PostMapping
    public String signup(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        //checking for duplicated username
        final User userByUsername = userService.getByUsername(user.getUsername());
        if(userByUsername!=null){
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.DUPLICATE_USER_NAME);
            return "redirect:/signup";
        }
        //check for duplicated email
        final User userByEmail = userService.getByEmail(user.getEmail());
        if(userByEmail!=null){
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.ACCOUNT_ALREADY_CREATED);
            return "redirect:/signup";
        }
        //check password format
        if(!userService.checkPassword(user.getPassword())){
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.PASSWORD_REGEX);
            return "redirect:/signup";
        }
        else {
            final User savedUser = userService.save(user);
            redirectAttributes.addFlashAttribute("message", SuccessMessages.Account_CREATED);
            redirectAttributes.addFlashAttribute("success", true);
        }
        return "redirect:/signup";
    }

}
