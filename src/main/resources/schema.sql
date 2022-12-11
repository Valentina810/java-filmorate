create table FR_FILM
(
    FILM_ID           INTEGER auto_increment,
    FILM_NAME         CHARACTER VARYING(150) not null,
    FILM_DESCRIPTION  CHARACTER VARYING(199) not null,
    FILM_RELEASE_DATE INTEGER                not null,
    FILM_DURATION     INTEGER                not null,
    FILM_COUNT_LIKES  INTEGER,
    FILM_MPA INTEGER,
    constraint FR_FILM_PK primary key (FILM_ID)
);

create table FR_GENRE
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(50) not null,
    constraint FR_GENRE_PK primary key (GENRE_ID)
);

create table FR_MPA
(
    MPA_ID   INTEGER auto_increment,
    MPA_NAME CHARACTER VARYING(10) not null,
    constraint FR_MPA_PK primary key (MPA_ID)
);

create table FR_FILM_GENRE
(
    FILM_ID INTEGER,
    GENRE_ID INTEGER
);

create table FR_FILM_LIKES
(
    FILM_ID INTEGER,
    USER_ID INTEGER
);

create table FR_USER
(
    USER_ID           INTEGER auto_increment,
    USER_EMAIL         CHARACTER VARYING(150) not null,
    USER_LOGIN  CHARACTER VARYING(100) not null,
    USER_NAME  CHARACTER VARYING(100) not null,
    USER_BIRTH_DATE  timestamp,
    constraint FR_USER_PK primary key (USER_ID)
);

create table FR_FRIENDSHIP
(
    FRIENDSHIP_USER      INTEGER not null,
    FRIENDSHIP_FRIEND    INTEGER not null,
    FRIENDSHIP_IS_STATUS BOOLEAN,
    constraint FR_FRIENDSHIP_PK
        primary key (FRIENDSHIP_USER, FRIENDSHIP_FRIEND),
    constraint FR_FRIENDSHIP_FR_USER_USER_ID_FK
        foreign key (FRIENDSHIP_USER) references FR_USER,
    constraint FR_FRIENDSHIP_FR_USER_USER_ID_FK_2
        foreign key (FRIENDSHIP_FRIEND) references FR_USER
);