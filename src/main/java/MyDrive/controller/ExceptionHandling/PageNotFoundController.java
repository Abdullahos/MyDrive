package MyDrive.controller.ExceptionHandling;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageNotFoundController implements ErrorController {
    @RequestMapping("/404")
    @GetMapping
    public String requestPageNotFound(){
        return "404.html";
    }

    public String getErrorPath() {
        return "404";
    }
}
