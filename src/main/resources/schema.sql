create table if not exists FR_MPA
(
    MPA_ID INTEGER auto_increment,
    NAME   CHARACTER VARYING(10) not null,
    constraint FR_MPA_PK
        primary key (MPA_ID)
);

create table if not exists FR_FILM
(
    FILM_ID           INTEGER auto_increment,
    NAME         CHARACTER VARYING(150) not null,
    DESCRIPTION  CHARACTER VARYING(199) not null,
    RELEASE_DATE DATE                not null,
    DURATION     INTEGER                not null,
    COUNT_LIKES  INTEGER,
    MPA_ID            INTEGER,
    constraint FR_FILM_PK
        primary key (FILM_ID),
    constraint FR_FILM_FR_MPA_MPA_ID_FK
        foreign key (MPA_ID) references FR_MPA
);

create table if not exists FR_GENRE
(
    GENRE_ID INTEGER auto_increment,
    NAME     CHARACTER VARYING(50) not null,
    constraint FR_GENRE_PK
        primary key (GENRE_ID)
);

create table if not exists FR_FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint FR_FILM_GENRE_PK
        primary key (FILM_ID, GENRE_ID),
    constraint FR_FILM_GENRE_FR_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FR_FILM,
    constraint FR_FILM_GENRE_FR_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references FR_GENRE
);

create table if not exists FR_USER
(
    USER_ID    INTEGER auto_increment,
    EMAIL      CHARACTER VARYING(150) not null,
    LOGIN      CHARACTER VARYING(100) not null,
    NAME       CHARACTER VARYING(100) not null,
    BIRTH_DATE DATE,
    constraint FR_USER_PK
        primary key (USER_ID)
);

create table if not exists FR_FILM_LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint FR_FILM_LIKES_PK
        primary key (FILM_ID, USER_ID),
    constraint FR_FILM_LIKES_FR_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FR_FILM,
    constraint FR_FILM_LIKES_FR_USER_USER_ID_FK
        foreign key (USER_ID) references FR_USER
);

create table if not exists FR_FRIENDSHIP
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    IS_STATUS BOOLEAN,
    constraint FR_FRIENDSHIP_PK
        primary key (USER_ID, FRIEND_ID),
    constraint FR_FRIENDSHIP_FR_FRIEND_USER_ID_FK_2
        foreign key (FRIEND_ID) references FR_USER,
    constraint FR_FRIENDSHIP_FR_USER_USER_ID_FK
        foreign key (USER_ID) references FR_USER
);