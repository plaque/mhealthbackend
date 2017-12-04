package plaque.mHelathBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import plaque.mHelathBackEnd.Model.*;
import plaque.mHelathBackEnd.Repository.*;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.util.*;

@Component
public class DataLoader {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private NoteRepository noteRepository;
    private HourMinutePairRepository hourMinutePairRepository;
    private DateResultPairRepository dateResultPairRepository;
    private ResultRepository resultRepository;

    @Autowired
    public DataLoader(UserRepository userRepository,
                      RoleRepository roleRepository,
                      NoteRepository noteRepository,
                      HourMinutePairRepository hourMinutePairRepository,
                      DateResultPairRepository dateResultPairRepository,
                      ResultRepository resultRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.noteRepository = noteRepository;
        this.hourMinutePairRepository = hourMinutePairRepository;
        this.dateResultPairRepository = dateResultPairRepository;
        this.resultRepository = resultRepository;
    }

    @PostConstruct
    public void loadData(){

        userRepository.deleteAll();
        roleRepository.deleteAll();
        noteRepository.deleteAll();
        hourMinutePairRepository.deleteAll();
        resultRepository.deleteAll();
        dateResultPairRepository.deleteAll();


        //User1

        User user1 = new User("sin1111", "Szymon", "Flagus", "sin1111@interia.eu", "$2a$10$.wA.0r2aRn41lsPPiGvkBuPZQH0eWI02bIIDQIpZKd5MAv98xzmfK", "531535908");


        //patients&pupils of user1

        ArrayList<Long> patientsIds = new ArrayList(){};
        ArrayList<Long> patronsIds = new ArrayList(){};

        patientsIds.add(2L);
        patientsIds.add(3L);
        patronsIds.add(4L);
        patronsIds.add(2L);


        //hours for user1
        ArrayList<Integer> days = new ArrayList<>();
        ArrayList<HourMinutePair> hours = new ArrayList<>();
        days.add(0);
        days.add(1);
        hours.add(new HourMinutePair(11, 22));
        hours.add(new HourMinutePair(22, 3));


        ArrayList<HourMinutePair> hours1 = new ArrayList<>();
        hours1.add(new HourMinutePair(10, 22));
        hours1.add(new HourMinutePair(20, 3));


        hourMinutePairRepository.save(hours);
        hourMinutePairRepository.save(hours1);

        //results for user1


        ArrayList<DateResultPair> results = new ArrayList<>();
        results.add(new DateResultPair("23-12-2017", 120f));
        results.add(new DateResultPair("24-12-2017", 130f));
        results.add(new DateResultPair("25-12-2017", 110f));
        results.add(new DateResultPair("26-12-2017", 150f));
        dateResultPairRepository.save(results);

        ArrayList<Result> newResults = new ArrayList<>();
        newResults.add(new Result("Cisnienie", results));



        ArrayList<DateResultPair> resultss = new ArrayList<>();
        resultss.add(new DateResultPair("23-12-2017", 80f));
        resultss.add(new DateResultPair("24-12-2017", 90f));
        resultss.add(new DateResultPair("25-12-2017", 120f));
        resultss.add(new DateResultPair("26-12-2017", 77f));
        dateResultPairRepository.save(resultss);

        newResults.add(new Result("Cukier", resultss));
        resultRepository.save(newResults);


        //notes for user1

        ArrayList<CyclicNote> notes = new ArrayList(){};

        CyclicNote note1 = new CyclicNote(days, hours1 , "Przed jedzeniem, zjesc obfity posilek", "Hepatil", true);
        CyclicNote note = new CyclicNote(days,hours , "Popic szklanka wody", "Rutinoscorbin", true);

        notes.add(note);
        notes.add(note1);

        noteRepository.save(notes);


        //save user1

        user1.setNotes(notes);
        user1.setResults(newResults);
        user1.setPupilsId(patientsIds);
        user1.setSittersId(patronsIds);

        userRepository.save(user1);


        //User2

        User user2 = new User("krzysztof", "Krzysztof", "Wieszak", "krzysztof@gmail.com", "$2a$10$.wA.0r2aRn41lsPPiGvkBuPZQH0eWI02bIIDQIpZKd5MAv98xzmfK", "531535908");


        //patients&pupils of user2

        patientsIds = new ArrayList(){};
        patronsIds = new ArrayList(){};

        patronsIds.add(1L);


        //hours for user2
        days = new ArrayList<>();
        hours = new ArrayList<>();
        days.add(2);
        days.add(3);
        hours.add(new HourMinutePair(9, 1));
        hours.add(new HourMinutePair(11, 54));


        hours1 = new ArrayList<>();
        hours1.add(new HourMinutePair(15, 24));
        hours1.add(new HourMinutePair(13, 34));


        hourMinutePairRepository.save(hours);
        hourMinutePairRepository.save(hours1);

        //results for user2


        results = new ArrayList<>();
        results.add(new DateResultPair("23-12-2017", 120f));
        results.add(new DateResultPair("24-12-2017", 130f));
        results.add(new DateResultPair("25-12-2017", 110f));
        results.add(new DateResultPair("26-12-2017", 150f));
        dateResultPairRepository.save(results);

        newResults = new ArrayList<>();
        newResults.add(new Result("Przeciwciala omega", results));



        resultss = new ArrayList<>();
        resultss.add(new DateResultPair("23-12-2017", 80f));
        resultss.add(new DateResultPair("24-12-2017", 90f));
        resultss.add(new DateResultPair("25-12-2017", 120f));
        resultss.add(new DateResultPair("26-12-2017", 77f));
        dateResultPairRepository.save(resultss);

        newResults.add(new Result("Przeciwciala alfa", resultss));
        resultRepository.save(newResults);


        //notes for user2

        notes = new ArrayList(){};

        note1 = new CyclicNote(days, hours1 , "Nie popijac mlekiem", "Strepsils", true);
        note = new CyclicNote(days,hours , "Popic szklanka wody", "Klindamycin", true);

        notes.add(note);
        notes.add(note1);

        noteRepository.save(notes);


        //save user2

        user2.setNotes(notes);
        user2.setResults(newResults);
        user2.setPupilsId(patientsIds);
        user2.setSittersId(patronsIds);

        userRepository.save(user2);

        //User3

        User user3 = new User("mareg", "Marek", "Kret", "marek@gmail.com", "$2a$10$.wA.0r2aRn41lsPPiGvkBuPZQH0eWI02bIIDQIpZKd5MAv98xzmfK", "531535908");


        //hours for user3
        days = new ArrayList<>();
        hours = new ArrayList<>();
        days.add(2);
        days.add(3);
        hours.add(new HourMinutePair(9, 1));
        hours.add(new HourMinutePair(11, 54));


        hours1 = new ArrayList<>();
        hours1.add(new HourMinutePair(15, 24));
        hours1.add(new HourMinutePair(13, 34));


        hourMinutePairRepository.save(hours);
        hourMinutePairRepository.save(hours1);

        //results for user3


        results = new ArrayList<>();
        results.add(new DateResultPair("23-12-2017", 120f));
        results.add(new DateResultPair("24-12-2017", 130f));
        results.add(new DateResultPair("25-12-2017", 110f));
        results.add(new DateResultPair("26-12-2017", 150f));
        dateResultPairRepository.save(results);

        newResults = new ArrayList<>();
        newResults.add(new Result("Przeciwciala omega", results));



        resultss = new ArrayList<>();
        resultss.add(new DateResultPair("23-12-2017", 80f));
        resultss.add(new DateResultPair("24-12-2017", 90f));
        resultss.add(new DateResultPair("25-12-2017", 120f));
        resultss.add(new DateResultPair("26-12-2017", 77f));
        dateResultPairRepository.save(resultss);

        newResults.add(new Result("Przeciwciala alfa", resultss));
        resultRepository.save(newResults);


        //notes for user3

        notes = new ArrayList(){};

        note1 = new CyclicNote(days, hours1 , "Jedna lyzka stol owa", "Glimbax", true);
        note = new CyclicNote(days,hours , "Nie klasc sie przez pol godziny", "Fixsin", true);

        notes.add(note);
        notes.add(note1);

        noteRepository.save(notes);


        //save user3

        user3.setNotes(notes);
        user3.setResults(newResults);

        userRepository.save(user3);


        //User4

        User user4 = new User("zosia", "Zosia", "Ynka", "zosia@gmail.com", "$2a$10$.wA.0r2aRn41lsPPiGvkBuPZQH0eWI02bIIDQIpZKd5MAv98xzmfK", "531535908");


        //hours for user4
        days = new ArrayList<>();
        hours = new ArrayList<>();
        days.add(2);
        days.add(3);
        hours.add(new HourMinutePair(9, 1));
        hours.add(new HourMinutePair(11, 54));


        hours1 = new ArrayList<>();
        hours1.add(new HourMinutePair(15, 24));
        hours1.add(new HourMinutePair(13, 34));


        hourMinutePairRepository.save(hours);
        hourMinutePairRepository.save(hours1);

        //results for user4


        results = new ArrayList<>();
        results.add(new DateResultPair("23-12-2017", 120f));
        results.add(new DateResultPair("24-12-2017", 130f));
        results.add(new DateResultPair("25-12-2017", 110f));
        results.add(new DateResultPair("26-12-2017", 150f));
        dateResultPairRepository.save(results);

        newResults = new ArrayList<>();
        newResults.add(new Result("Przeciwciala omega", results));



        resultss = new ArrayList<>();
        resultss.add(new DateResultPair("23-12-2017", 80f));
        resultss.add(new DateResultPair("24-12-2017", 90f));
        resultss.add(new DateResultPair("25-12-2017", 120f));
        resultss.add(new DateResultPair("26-12-2017", 77f));
        dateResultPairRepository.save(resultss);

        newResults.add(new Result("Przeciwciala alfa", resultss));
        resultRepository.save(newResults);


        //notes for user4

        notes = new ArrayList(){};

        note1 = new CyclicNote(days, hours1 , "Przed jedzeniem", "Metypred", true);
        note = new CyclicNote(days,hours , "Starannie wetrzec", "Lioton 1000", true);

        notes.add(note);
        notes.add(note1);

        noteRepository.save(notes);


        //save user4

        user4.setNotes(notes);
        user4.setResults(newResults);

        userRepository.save(user4);

    }
}
