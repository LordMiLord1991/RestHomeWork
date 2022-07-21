create table person(
                       id int auto_increment not null primary key,
                       username varchar(100) not null,
                       year_of_birth int,
                       email varchar(150) unique
);

create table Image(
                      id int auto_increment primary key,
                      person_id int unique references person(id) on delete set null,
                      url varchar(200)
);

insert into person(username, year_of_birth, email, status, role, password)
    VALUE ('volodya', 1992, 'test@mail.ru', 'offline', 'ROLE_ADMIN', 'password');

insert into person(username, year_of_birth, email, status, role, password)
    VALUE ('ivan', 1992, 'test2@mail.ru', 'offline', 'ROLE_USER', 'password');