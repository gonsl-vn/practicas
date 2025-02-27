


    INSERT INTO directores (dni, name, surname, age, nationality) VALUES ('12345678A','Peter', 'Jackson', 75, 'Neozelandes')
INSERT INTO directores (dni, name, surname, age, nationality) VALUES ('98765432B', 'Christopher', 'Nolan', 55, 'Britanico')


INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('44443331C','Elijah', 'Wood', 65, 'Autraliano')
INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('33344441D','Cillian', 'Murphy', 35, 'Irlandes')
INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('12233344E','Ian', 'Mckellen', 95, 'Austriaco')


INSERT INTO productora (name, founded_in_year) VALUES ('Warner Bros', 1920)
INSERT INTO productora (name, founded_in_year) VALUES ('Universal', 1930)




INSERT INTO series (title, year, directorId, productoraId) VALUES ('SerieEjemplo1', 2000, 2,1)
INSERT INTO series (title, year, directorId, productoraId) VALUES ('SerieEjemplo2', 2020, 1,2)

INSERT INTO actoresDePelicula (peliculaId, actorId) VALUES (1,1)
INSERT INTO actoresDePelicula (peliculaId, actorId) VALUES (1,3)
INSERT INTO actoresDePelicula (peliculaId, actorId) VALUES (2,2)

INSERT INTO actoresDeSerie (serieId, actorId) VALUES (1,2)
INSERT INTO actoresDeSerie (serieId, actorId) VALUES (2,1)
INSERT INTO actoresDeSerie (serieId, actorId) VALUES (2,3)
