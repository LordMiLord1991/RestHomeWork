package ru.kuzmin.homework.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kuzmin.homework.rest.models.Authority;

public interface AuthoritiesRepository extends JpaRepository<Authority, Integer> {
}
