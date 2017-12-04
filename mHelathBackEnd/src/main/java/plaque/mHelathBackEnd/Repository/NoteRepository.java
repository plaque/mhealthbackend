package plaque.mHelathBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plaque.mHelathBackEnd.Model.CyclicNote;

@Repository
public interface NoteRepository extends CrudRepository<CyclicNote, Long> {
    public CyclicNote findByNoteId(Long id);

}
