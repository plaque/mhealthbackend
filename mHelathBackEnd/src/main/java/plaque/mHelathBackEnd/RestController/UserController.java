package plaque.mHelathBackEnd.RestController;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import plaque.mHelathBackEnd.Model.*;
import plaque.mHelathBackEnd.Repository.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController {

    private UserRepository userRepository;
    private NoteRepository noteRepository;
    private HourMinutePairRepository hourMinutePairRepository;
    private DateResultPairRepository dateResultPairRepository;
    private ResultRepository resultRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender mailSender;

    @Inject
    UserController(UserRepository userRepository, HourMinutePairRepository hourMinutePairRepository,
                   NoteRepository noteRepository, PasswordEncoder passwordEncoder,
                   JavaMailSender mailSender, DateResultPairRepository dateResultPairRepository,
                   ResultRepository resultRepository){
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.resultRepository = resultRepository;
        this.hourMinutePairRepository = hourMinutePairRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.dateResultPairRepository = dateResultPairRepository;
    }

    @RequestMapping(value = "sign-up/", method = RequestMethod.POST)
    public void signUp(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public User getUserData(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        return userRepository.findByUsername(username);
    }

    @RequestMapping(value = "{id}/", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return userRepository.findByUserId(id);
    }

    @RequestMapping(value = "pupils/", method = RequestMethod.GET)
    public List<User> getPupils(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        List<Long> patientsId = userRepository.findByUsername(username).getPupilsId();
        List<User> patients = new ArrayList<>();

        for (Long patientId: patientsId) {
            patients.add(userRepository.findByUserId(patientId));
        }

        return patients;
    }


    @RequestMapping(value = "sitters/", method = RequestMethod.GET)
    public List<User> getSitters(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        List<Long> sittersId = userRepository.findByUsername(username).getSittersId();
        List<User> sitters = new ArrayList<>();

        for (Long sitterId: sittersId) {
            sitters.add(userRepository.findByUserId(sitterId));
        }

        return sitters;
    }

    @RequestMapping(value = "sitters/", method = RequestMethod.POST)
    public List<User> getSittersPost(@RequestBody Email email){

        List<Long> sittersId = userRepository.findByEmail(email.getEmail()).getSittersId();
        List<User> sitters = new ArrayList<>();

        for (Long sitterId: sittersId) {
            sitters.add(userRepository.findByUserId(sitterId));
        }

        return sitters;
    }

    @RequestMapping(value = "notes/", method = RequestMethod.GET)
    public List<CyclicNote> getNotes(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        return userRepository.findByUsername(username).getNotes();
    }


    @RequestMapping(value = "updateNotes/", method = RequestMethod.POST)
    public ResponseEntity addNote(@RequestBody ArrayList<CyclicNote> notes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        //search for user
        User searchedUser = userRepository.findByUsername(username);
        //get old notes
        PersistentBag dbNotes = (PersistentBag) searchedUser.getNotes();

        //get old notes hours
        ArrayList<HourMinutePair> hourMinutePairs = new ArrayList<>();
        for(Object note : dbNotes){
            hourMinutePairs.addAll(((CyclicNote) note).getHours());
        }

        //get new hours
        ArrayList<HourMinutePair> newHourMinutePairs;
        for(CyclicNote note : notes){
            newHourMinutePairs =(ArrayList<HourMinutePair>) note.getHours();
            hourMinutePairRepository.save(newHourMinutePairs);
            note.setHours(newHourMinutePairs);
        }

        noteRepository.save(notes);
        searchedUser.setNotes(notes);
        userRepository.save(searchedUser);
        noteRepository.delete(dbNotes);
        hourMinutePairRepository.delete(hourMinutePairs);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "updateResults/", method = RequestMethod.POST)
    public ResponseEntity addResult(@RequestBody ArrayList<Result> results) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        //search for user
        User searchedUser = userRepository.findByUsername(username);
        //get old notes
        PersistentBag dbResults = (PersistentBag) searchedUser.getResults();

        //get old notes hours
        ArrayList<DateResultPair> dateResultPairs = new ArrayList<>();
        for(Object result : dbResults){
            dateResultPairs.addAll(((Result) result).getResults());
        }

        //get new hours
        ArrayList<DateResultPair> newDateResultPairs;
        for(Result result : results){
            newDateResultPairs =(ArrayList<DateResultPair>) result.getResults();
            dateResultPairRepository.save(newDateResultPairs);
            result.setResults(newDateResultPairs);
        }

        resultRepository.save(results);
        searchedUser.setResults(results);
        userRepository.save(searchedUser);
        resultRepository.delete(dbResults);
        dateResultPairRepository.delete(dateResultPairs);

        return new ResponseEntity(HttpStatus.OK);
    }



    @RequestMapping(value = "addSitter/", method = RequestMethod.POST)
    public User addSitter(@RequestBody Email email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User sitter = userRepository.findByEmail(email.getEmail());

        if(sitter != null){
            User searchedUser = userRepository.findByUsername(username);
            searchedUser.getSittersId().add(sitter.getUserId());
            userRepository.save(searchedUser);
            sitter.getPupilsId().add(searchedUser.getUserId());
            userRepository.save(sitter);
        }else{
            User searchedUser = userRepository.findByUsername(username);
            SimpleMailMessage mail = new SimpleMailMessage();
            String subject = "Try out our application";
            mail.setFrom("sin1111@interia.eu");
            mail.setTo(email.getEmail());
            mail.setSubject(subject);
            mail.setText("One of our users " + searchedUser.getEmail() + " - "
                    + searchedUser.getName() + " " + searchedUser.getSurname() + " wants you "
                    + "to try our app. Get it from Google Play");
            mailSender.send(mail);
        }



        return sitter;
    }

    @RequestMapping(value = "addPupil/", method = RequestMethod.POST)
    public User addPupil(@RequestBody Email email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User pupil = userRepository.findByEmail(email.getEmail());

        if(pupil != null){
            User searchedUser = userRepository.findByUsername(username);
            searchedUser.getPupilsId().add(pupil.getUserId());
            userRepository.save(searchedUser);
            pupil.getSittersId().add(searchedUser.getUserId());
            userRepository.save(pupil);
        }else{
        User searchedUser = userRepository.findByUsername(username);
        SimpleMailMessage mail = new SimpleMailMessage();
        String subject = "Try out our application";
        mail.setFrom("sin1111@interia.eu");
        mail.setTo(email.getEmail());
        mail.setSubject(subject);
        mail.setText("One of our users " + searchedUser.getEmail() + " - "
                + searchedUser.getName() + " " + searchedUser.getSurname() + " wants you "
                + "to try our app. Get it from Google Play");
        mailSender.send(mail);
        }

        return pupil;
    }

    @RequestMapping(value = "fallDetected/", method = RequestMethod.GET)
    public ResponseEntity sendMails(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        User searchedUser = userRepository.findByUsername(username);
        PersistentBag sittersIds = (PersistentBag) searchedUser.getSittersId();


        for(Object id : sittersIds){
            SimpleMailMessage email = new SimpleMailMessage();
            String recipientEmail = userRepository.findByUserId((Long) id).getEmail();
            String subject = "Fall detected!";
            email.setFrom("sin1111@interia.eu");
            email.setTo(recipientEmail);
            email.setSubject(subject);
            email.setText("Our application detected fall from user: " + searchedUser.getEmail() + " - "
                    + searchedUser.getName() + " " + searchedUser.getSurname());
            //mailSender.send(email);
        }

        SimpleMailMessage email = new SimpleMailMessage();
        String subject = "Fall detected!";
        email.setFrom("sin1111@interia.eu");
        email.setTo("haikubee95@gmail.com");
        email.setSubject(subject);
        email.setText("Our application detected fall from user: " + searchedUser.getEmail() + " - "
                + searchedUser.getName() + " " + searchedUser.getSurname());
        mailSender.send(email);

        return ResponseEntity.ok().build();
    }
}
