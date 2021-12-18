package com.Mydrive.MyDrive.Repository;

import com.Mydrive.MyDrive.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findByUserUsername(String username);

    Optional<Note> findByTitle(String title);

    Optional<Note> findByUserUsernameAndTitle(String name, String title);
}
