package MyDrive.service;


import MyDrive.Repository.FileRepository;
import MyDrive.Repository.UserRepository;
import MyDrive.models.File;
import MyDrive.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * create or update user
     * @param file
     * @param username
     * @return file or null in case of there's no user with that username
     */
    public File save(File file, String username){
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            final User user = optionalUser.get();
            user.addFile(file);
            file.setUser(user);
            return fileRepository.save(file);
        }
        return null;
    }

    /**
     *
     * @param id
     * @return true if found and deleted. otherwise, false
     */
    public boolean delete(Long id){
        final Optional<File> optionalFile = fileRepository.findById(id);
        if(optionalFile.isPresent()){
            fileRepository.delete(optionalFile.get());
            return true;
        }
        return false;
    }

    /**
     * find file by its id
     * @param id
     * @return file if found, null if not
     */
    public File findById(Long id){
        final Optional<File> optionalFile = fileRepository.findById(id);
        if(optionalFile.isPresent())    return optionalFile.get();
        return null;
    }

    /**
     * find set of files of specific user bu user username
     * @param username
     * @return set of all that user's file(may be an empty if the user doesn't has any yet) or null if user doesn't exist
     */
    public Set<File> findByUsername(String username){
        final Optional<Set<File>> optionalFiles = fileRepository.findByUserUsername(username);
        if(optionalFiles.isPresent())   return optionalFiles.get();
        return null;
    }

    public File findByNameAndUserName(String originalFilename, String username) {
        final Optional<File> optionalFile = fileRepository.findByNameAndUserUsername(originalFilename, username);
        if(optionalFile.isPresent())    return optionalFile.get();
        return null;
    }

    public File findByNameAndUserName(String originalFilename) {
        final Optional<File> fileByName = fileRepository.findByName(originalFilename);
        if(fileByName.isPresent())  return fileByName.get();
        else return null;
    }
}
