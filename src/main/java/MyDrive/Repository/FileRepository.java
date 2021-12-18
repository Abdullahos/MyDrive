package MyDrive.Repository;

import MyDrive.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<Set<File>> findByUserUsername(String username);

    Optional<File> findByNameAndUserUsername(String originalFilename, String username);

    Optional<File> findByName(String originalFilename);
}
