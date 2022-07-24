package ru.kuzmin.homework.rest.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuzmin.homework.rest.models.Authority;
import ru.kuzmin.homework.rest.models.Person;
import ru.kuzmin.homework.rest.repositories.AuthoritiesRepository;
import ru.kuzmin.homework.rest.repositories.PeopleRepository;
import ru.kuzmin.homework.rest.util.PersonNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AuthoritiesRepository authoritiesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, AuthoritiesRepository authoritiesRepository) {
        this.peopleRepository = peopleRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }


    //Делаем выборку по статусу и времени его последнего изменения
    public List<Person> findAll(String status, Date timestamp) {
        Session session = entityManager.unwrap(Session.class);

        List<Person> filterList = session.createQuery("select p FROM Person p where p.status=:status" +
                        " and p.statusChangeAt>:timestamp",
                Person.class).setParameter("status", status).setParameter("timestamp", timestamp).getResultList();

        return filterList;
    }


    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public int saveAndReturnId(Person person) {
        person.setStatus("offline");
        person.setStatusChangeAt(new Date());
        Optional<Authority> authority = authoritiesRepository.findById(1);
        System.out.println(authority.get());
        person.setRoles(new ArrayList<>(Collections.singleton(authority.get())));
        peopleRepository.save(person);

        return person.getId();
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }
        updatePerson.setId(id);
        updatePerson.setRoles(person.get().getRoles());
        updatePerson.setStatus(person.get().getStatus());

        peopleRepository.save(updatePerson);
    }

    @Transactional
    public List<String> changeStatus(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        System.out.println(person.get().getUserName());
        System.out.println(person.get().getId());

        if (person.isEmpty())
            throw new PersonNotFoundException();

        String oldStatus = person.get().getStatus();

        if (person.get().getStatus().equals("offline"))
            person.get().setStatus("online");

        else
            person.get().setStatus("offline");

        person.get().setStatusChangeAt(new Date());

        String newStatus = person.get().getStatus();

        List<String> responseList = new ArrayList<>();

        responseList.add("Old status: " + oldStatus);
        responseList.add("New status: " + newStatus);
        responseList.add("Id: " + person.get().getId());

        return responseList;
    }
}