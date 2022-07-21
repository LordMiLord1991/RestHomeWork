package ru.kuzmin.homework.rest.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kuzmin.homework.rest.dto.PersonDTO;
import ru.kuzmin.homework.rest.models.Person;
import ru.kuzmin.homework.rest.services.PeopleService;
import ru.kuzmin.homework.rest.util.PersonErrorResponse;
import ru.kuzmin.homework.rest.util.PersonNotCreatedException;
import ru.kuzmin.homework.rest.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neil Alishev
 */
@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper mapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper mapper) {
        this.peopleService = peopleService;
        this.mapper = mapper;
    }


    @GetMapping()
    public List<PersonDTO> getPeople(@RequestParam(required = false) String status,
                                     @RequestParam(required = false) Long timestamp) {
        if (status == null || timestamp == null) {
            return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
        } else {
            return peopleService.findAll(status, new Date(timestamp)).stream().map(this::convertToPersonDTO)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }


    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        checkAndThrowException(bindingResult);

        int id = peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok("Id is: " + id);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult,
                                                   @PathVariable("id") int id) {
        checkAndThrowException(bindingResult);

        peopleService.update(id, convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    //Запрашиваем информацию пользователя по указанному Id
    @GetMapping("/info")
    public PersonDTO getInfo(@RequestBody Integer id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    // меняем статус пользователя
    @PatchMapping("/{id}/status")
    public List<String> changeStatus(@PathVariable("id") int id) {
        return peopleService.changeStatus(id);
    }

    //Выбрасываем исключение по невалидным полям
    private void checkAndThrowException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse("Person with this id wasn`t found!",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return mapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return mapper.map(person, PersonDTO.class);
    }

}
