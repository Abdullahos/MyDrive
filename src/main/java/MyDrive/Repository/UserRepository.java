package MyDrive.Repository;

import MyDrive.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNotesId(Long id);

    Optional<User> findByFilesId(Long id);

    Optional<User> findByCredentialsId(Long id);
}
