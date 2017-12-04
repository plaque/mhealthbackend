package plaque.mHelathBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plaque.mHelathBackEnd.Model.HourMinutePair;

@Repository
public interface HourMinutePairRepository extends CrudRepository<HourMinutePair, Long> {
    public HourMinutePair findByHourMinutePairId(Long id);
}
