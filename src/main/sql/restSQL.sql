create table person
(
    id               int auto_increment not null primary key,
    username         varchar(100) not null,
    year_of_birth    int,
    email            varchar(150) unique,
    password         varchar(50),
    status           varchar(20),
    status_change_at TIMESTAMP

);

create table Image
(
    id        int auto_increment primary key,
    person_id int unique references person (id) on delete set null,
    url       varchar(200)
);


create table authority
(
    id        int auto_increment primary key,
    role      varchar(30),
    person_id int references person (id)
);

insert into person(username, year_of_birth, email, status, password)
    VALUE ('volodya', 1992, 'test@mail.ru', 'offline', 'password');

insert into person(username, year_of_birth, email, status, password)
    VALUE ('ivan', 1992, 'test2@mail.ru', 'offline', 'password');