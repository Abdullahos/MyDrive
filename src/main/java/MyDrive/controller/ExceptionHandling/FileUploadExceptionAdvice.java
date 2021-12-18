package com.Mydrive.MyDrive.ExceptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class to handle MaxUploadSizeExceededException
 * good resource : https://www.baeldung.com/spring-maxuploadsizeexceeded
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("fail",true);
        redirectAttributes.addFlashAttribute("message", "File too large!");
        return "redirect:/home";
    }
}
