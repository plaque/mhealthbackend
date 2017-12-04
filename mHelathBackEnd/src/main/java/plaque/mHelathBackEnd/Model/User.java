package plaque.mHelathBackEnd.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2016-10-14.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long userId;

    @NotNull
    private String username;

    private String name;

    private String surname;

    private String phoneNumber;

    @NotNull
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ElementCollection
    private List<Long> sittersId;

    @ElementCollection
    private List<Long> pupilsId;

    @OneToMany(targetEntity = CyclicNote.class, fetch = FetchType.LAZY)
    private List<CyclicNote> notes;

    @OneToMany(targetEntity = Result.class, fetch = FetchType.LAZY)
    private List<Result> results;

    @JsonIgnore
    private boolean enabled;

    public User(){
    }


    public User(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.password = user.getPassword();
        this.enabled = true;
        this.notes = user.getNotes();
        this.results = user.getResults();
        this.sittersId = user.getSittersId();
        this.pupilsId = user.getPupilsId();
        this.phoneNumber = user.phoneNumber;
    }

    public User(String userName, String name, String surname,
                String email, String password, String phoneNumber) {
        this.username = userName;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Long> getPupilsId() {
        return pupilsId;
    }

    public void setPupilsId(ArrayList<Long> pupilsId) {
        this.pupilsId = pupilsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Long> getSittersId() {
        return sittersId;
    }

    public void setSittersId(List<Long> sittersId) {
        this.sittersId = sittersId;
    }

    public List<CyclicNote> getNotes() {
        return notes;
    }

    public void setNotes(List<CyclicNote> notes) {
        this.notes = notes;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

