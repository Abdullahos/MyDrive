package MyDrive.service;



import MyDrive.Repository.NoteRepository;
import MyDrive.Repository.UserRepository;
import MyDrive.models.Note;
import MyDrive.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * create or edit note
     * @param note
     * @param username
     * @return note if that user exist. otherwise, null
     */
    public Note save(Note note, String username){
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            final User user = optionalUser.get();
            user.addNote(note);         //it's notes is a set in user, so it won't be added in case of update
            note.setUser(user);
            return noteRepository.save(note);
        }
        return null;
    }
    /**
     *
     * @param id
     * @return true if found and deleted. otherwise, false
     */
    public boolean delete(Long id){
        final Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isPresent()){
            noteRepository.delete(optionalNote.get());
            return true;
        }
        return false;
    }
    /**
     * find note by its id
     * @param id
     * @return note if found, null if not
     */
    public Note find(Long id){
        final Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isPresent())    return optionalNote.get();
        else return null;
    }

    /**
     *
     * @param username
     * @return set of all that user's notes(may be an empty if the user doesn't has any yet) or null if user doesn't exist
     */
    public List<Note> findByUserUsername(String username){
        final Optional<List<Note>> optionalNotes = noteRepository.findByUserUsername(username);
        if(optionalNotes.isPresent())   return optionalNotes.get();
        else return null;
    }

    public Note findByTitle(String title) {
        final Optional<Note> noteByTitle = noteRepository.findByTitle(title);
        if(noteByTitle.isPresent()){
            return noteByTitle.get();
        }
        else return null;
    }

    public Note findByUsernameAndTitle(String name, String title) {
        final Optional<Note> noteOptional =  noteRepository.findByUserUsernameAndTitle(name, title);
        if(noteOptional.isPresent())    return    noteOptional.get();
        else return null;
    }
}
