merge into FR_MPA (MPA_ID,NAME)
values
    (1,'G' ),
    (2,'PG'),
    (3,'PG-13'),
    (4,'R'),
    (5,'NC-17');

merge into FR_GENRE (GENRE_ID,NAME)
    values
        (1,'feature film' ),
        (2,'short film'),
        (3,'action'),
        (4,'adventure'),
        (5,'drama'),
        (6,'crime'),
        (7,'horror'),
        (8,'fantasy'),
        (9,'romance'),
        (10,'thriller'),
        (11,'animation'),
        (12,'family'),
        (13,'war'),
        (14,'documentary'),
        (15,'musical'),
        (16,'biography'),
        (17,'sci-fi'),
        (18,'western'),
        (19,'post-apocalyptic');

merge into FR_FILM (FILM_ID,NAME,DESCRIPTION,RELEASE_DATE,DURATION,COUNT_LIKES,MPA_ID)
    values
        (1,'The Green Mile','Paul Edgecomb is the head of the death row at Cold Mountain Prison, each of whose prisoners once walks the "green mile" on the way to the place of execution.','1999-12-6',189,0,4),
        (2,'Schindlers List','The story of a German industrialist who saved thousands of lives during the Holocaust','1993-11-30',195,0,4);

merge into FR_FILM_GENRE (film_id, genre_id)
    values
        (1,5),
        (1,1);

merge into FR_USER (USER_ID,EMAIL,LOGIN,NAME,BIRTH_DATE)
values
    (1,'tom@mail.ru','tom','tom','2000-03-13'),
    (2,'olga@mail.ru','olga','olga','1999-04-11'),
    (3,'krisi@mail.ru','krisi','krisi','1985-01-10'),
    (4,'matt@mail.ru','matt','matt','2003-12-12');

merge into FR_FILM_LIKES(film_id, user_id)
    values
        (2,1),
        (1,3),
        (2,3);

merge into FR_FRIENDSHIP (USER_ID,FRIEND_ID,IS_STATUS)
values
    (1,2,true),
    (1,3,true),
    (2,1,true),
    (3,1,true);