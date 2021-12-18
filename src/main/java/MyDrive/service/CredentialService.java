package MyDrive.service;



import MyDrive.Repository.CredentialRepository;
import MyDrive.Repository.UserRepository;
import MyDrive.models.Credential;
import MyDrive.models.User;
import MyDrive.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CredentialService {
    @Autowired
    private
    CredentialRepository credentialRepository;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private UserRepository userRepository;

    /**
     * create or update credential
     * @param credential
     * @return the created or updated credential
     */
    public Credential save(Credential credential, String username){

        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            //get the current user
            final User user = optionalUser.get();
            //set the bi directional relationship (user & Credentials)
            user.addCredentials(credential);
            credential.setUser(user);

            //encoding the credential password to store it encoded.
            String password = credential.getPassword();
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

            //store the encoded password
            credential.setPassword(encryptedPassword);
            credential.setEncryptionKey(encodedKey);

            //set the date and time of that action
            credential.setLastModifiedDate(LocalDate.now());

            //save the credential
            return credentialRepository.save(credential);
        }
        return null;
    }

    /**
     *
     * @param id
     * @return true if credential found and deleted, otherwise return false.
     */
    public boolean delete(Long id){
        final Optional<Credential> optionalCred = credentialRepository.findById(id);
        if(optionalCred.isPresent()) {
            credentialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * find credential by its id
     * @param id
     * @return file if found, null if not
     */
    public Credential getById(Long id){
        final Optional<Credential> optionalCredential = credentialRepository.findById(id);
        if(optionalCredential.isPresent())  return optionalCredential.get();
        else return null;
    }

    /**
     * find set of credentials of specific user bu user username
     * @param username
     * @return set of all that user's credentials(may be an empty if the user doesn't has any yet) or null if user doesn't exist
     */
    public List<Credential> getAllByUsername(String username){
        final Optional<List<Credential>> optionalCredentialList = credentialRepository.findByUserUsername(username);
        if(optionalCredentialList.isPresent())  return optionalCredentialList.get();
        else return null;
    }

}
