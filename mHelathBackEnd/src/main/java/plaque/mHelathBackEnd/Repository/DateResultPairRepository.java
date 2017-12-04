package plaque.mHelathBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;
import plaque.mHelathBackEnd.Model.DateResultPair;

public interface DateResultPairRepository extends CrudRepository<DateResultPair, Long> {

    public DateResultPair findByDateResultPairId(Long id);
}
