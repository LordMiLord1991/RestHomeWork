package ru.kuzmin.homework.rest.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException (String msg){
        super(msg);
    }
}
