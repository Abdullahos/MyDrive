package MyDrive.security;


import MyDrive.Repository.UserRepository;
import MyDrive.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AthenticationService implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashService hashService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        //check if the there's user attached with that username
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String password = authentication.getCredentials().toString();
            String salt = user.getSalt();
            //compare hashes
            //authenticated
            if(hashService.getHashedValue(password,salt).equals(user.getPassword()))
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }
        //not authenticated
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
