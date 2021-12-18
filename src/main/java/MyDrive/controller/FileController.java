package MyDrive.controller;


import MyDrive.Messages.ErrorAndWarningMessages;
import MyDrive.Messages.SuccessMessages;
import MyDrive.models.File;
import MyDrive.models.User;
import MyDrive.service.FileService;
import MyDrive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public String save(@ModelAttribute MultipartFile fileToUpload, Authentication auth, RedirectAttributes redirectAttributes) throws IOException {
        //no file chosen
        if(fileToUpload.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.UPLOAD_NO_CHOSEN_FILE);
            return "redirect:/home";
        }
        //duplicate file name
        final File fileByName = fileService.findByNameAndUserName(fileToUpload.getOriginalFilename());
        if(fileByName!=null){
            redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.DUPLICATE_FILE_NAME);
            return "redirect:/home";
        }
        File file = new File(userService.getByUsername(auth.getName()), fileToUpload.getOriginalFilename(), fileToUpload.getBytes(), fileToUpload.getContentType());
        final File savedFile = fileService.save(file, auth.getName());
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("message", SuccessMessages.FILE_UPLOAD_SUCCESS_MESSAGE);
        return "redirect:/home";
    }
    @GetMapping("/view")
    public StreamingResponseBody renderFile(HttpServletResponse response, Authentication authentication, @RequestParam("id")Long id)throws Exception{

        File file = fileService.findById(id);
        response.setContentType(file.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\"" + file.getName());
        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[10000];
            InputStream inputStream = new ByteArrayInputStream(file.getData());
            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }
    @GetMapping("/download")
    public ResponseEntity downloadFile(Authentication authentication, @RequestParam("id")Long id){
        File files = fileService.findById(id);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(files.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + files.getName() + "\"")
                .body(new ByteArrayResource(files.getData()));
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Authentication auth){
        final User userByNoteId = userService.findByFileId(id);
        if(!userByNoteId.getUsername().equals(auth.getName()))   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        final boolean isDeleted = fileService.delete(id);
        if(isDeleted)   {
            redirectAttributes.addFlashAttribute("success",true);
            redirectAttributes.addFlashAttribute("message", SuccessMessages.FILE_DELETE_SUCCESS_MESSAGE);
        }
        else redirectAttributes.addFlashAttribute("message", ErrorAndWarningMessages.ERROR_ON_DELETING);
        return "redirect:/home";
    }

}
