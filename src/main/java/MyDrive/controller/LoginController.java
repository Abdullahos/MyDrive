package MyDrive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    /*
        no need for passing model as spring provide like object for us when athentication happens
        if suucessful-->defualtsuccessUrl
        else add paramerter error to the url
     */
    public String getLoginPage(){
        return "login";
    }

    //no post as spring provide special post for login can be configured in configuraton class
}
