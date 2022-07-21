package ru.kuzmin.homework.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuzmin.homework.rest.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
