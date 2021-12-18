package MyDrive.service;


import MyDrive.Repository.UserRepository;
import MyDrive.models.User;
import MyDrive.security.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashService hashService;
    /**
     * create or update user
     * @param user
     * @return
     */
    public User save(User user){
        //new user(create)
        if(user.getId() == null) {
            final String[] hashAndSalt = hashPassword(user.getPassword());
            user.setPassword(hashAndSalt[0]);
            user.setSalt(hashAndSalt[1]);
        }
        //save user
        final User savedUser = userRepository.save(user);
        return savedUser;
    }

    /**
     * @param id
     * @return true if found and deleted, otherwise return false
     */
    public boolean delete(Long id){
        final Optional<User> useById = userRepository.findById(id);
        if(useById.isPresent()) {
            userRepository.delete(useById.get());
            return true;
        }
        return false;
    }

    /**
     * return user by username. user name is unique
     * @return user or null if not found
     */
    public User getByUsername(String username){
        final Optional<User> userByUsername = userRepository.findByUsername(username);
        if(userByUsername.isPresent())  return userByUsername.get();
        return null;
    }
    public User getByEmail(String email){
        final Optional<User> userByEmail = userRepository.findByEmail(email);
        if(userByEmail.isPresent()){
            return userByEmail.get();
        }
        return null;
    }
    public String[] hashPassword(String password){
        String[] hashAndSalt = new String[2];
        //hashing password
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String hashedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(password,hashedSalt);
        hashAndSalt[0] = hashedPassword;
        hashAndSalt[1] = hashedSalt;
        return hashAndSalt;
    }

    public User findByNoteId(Long id) {
        final Optional<User> userByNoteId = userRepository.findByNotesId(id);
        if(userByNoteId.isPresent())    return userByNoteId.get();
        return null;
    }

    public User findByFileId(Long id) {
        final Optional<User> userByNoteId = userRepository.findByFilesId(id);
        if(userByNoteId.isPresent())    return userByNoteId.get();
        return null;
    }

    public User findByCredentialId(Long id) {
        final Optional<User> userByNoteId = userRepository.findByCredentialsId(id);
        if(userByNoteId.isPresent())    return userByNoteId.get();
        return null;
    }

    public boolean checkPassword(String password) {
        String regex = "^(?=\\S*\\d)(?=\\S*[a-zA-Z])(?=\\S*[.!@#$%^&*()_+?]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return  matcher.find();
    }
}
