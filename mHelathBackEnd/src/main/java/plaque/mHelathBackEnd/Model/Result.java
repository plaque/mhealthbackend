package plaque.mHelathBackEnd.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Result {

    public Result(){

    }

    public Result(String title, ArrayList<DateResultPair> results){
        this.title = title;
        this.results = results;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long resultId;

    private String title;

    @OneToMany(targetEntity = DateResultPair.class, fetch = FetchType.EAGER)
    private List<DateResultPair> results;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public List<DateResultPair> getResults() {
        return results;
    }

    public void setResults(List<DateResultPair> results) {
        this.results = results;
    }
}
