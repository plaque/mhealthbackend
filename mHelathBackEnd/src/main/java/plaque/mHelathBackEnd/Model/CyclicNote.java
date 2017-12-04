package plaque.mHelathBackEnd.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CyclicNote{

    public CyclicNote(){};

    public CyclicNote(List<Integer> days, ArrayList<HourMinutePair> hours, String content,
                      String title, Boolean active){
        this.content = content;
        this.title = title;
        this.days = days;
        this.hours = hours;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long noteId;

    private String content;

    private String title;

    private Boolean active;

    @ElementCollection
    private List<Integer> days;

    @OneToMany(targetEntity = HourMinutePair.class, fetch = FetchType.EAGER)
    private List<HourMinutePair> hours;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }


    public List<HourMinutePair> getHours() {
        return hours;
    }

    public void setHours(ArrayList<HourMinutePair> hours) {
        this.hours = hours;
    }
}
