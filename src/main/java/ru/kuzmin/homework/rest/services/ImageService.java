package ru.kuzmin.homework.rest.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kuzmin.homework.rest.models.Image;
import ru.kuzmin.homework.rest.models.Person;
import ru.kuzmin.homework.rest.repositories.ImageRepository;
import ru.kuzmin.homework.rest.repositories.PeopleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ImageService {

    @Autowired
    ServletContext servletContext;

    private final ImageRepository imageRepository;
    private final PeopleRepository peopleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ImageService(ImageRepository imageRepository, PeopleRepository peopleRepository) {
        this.imageRepository = imageRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public String upLoadPhoto(MultipartFile file, String nameFile, int personId) {
        Session session = entityManager.unwrap(Session.class);
        String nameImage = file.getName();

        try {
            Path path = Paths.get(servletContext.getRealPath("uploads") + file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);


            Optional<Person> person = peopleRepository.findById(personId);

            Image image = new Image(path.toString(), person.get());
            person.get().setImage(image);

            session.save(image);
            return "Вы удачно загрузили " + nameImage + " в " + nameFile + "-uploaded !";

        } catch (Exception e) {
            return "Вам не удалось загрузить " + nameImage + " => " + e.getMessage();
        }
    }

}
