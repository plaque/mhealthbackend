package plaque.mHelathBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plaque.mHelathBackEnd.Model.Result;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {

    public Result findByResultId(Long id);
}
