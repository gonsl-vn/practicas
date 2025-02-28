


INSERT INTO directores (dni, name, surname, age, nationality) VALUES ('12345678A','Peter', 'Jackson', 75, 'Neozelandes')
INSERT INTO directores (dni, name, surname, age, nationality) VALUES ('98765432B', 'Christopher', 'Nolan', 55, 'Britanico')


INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('44443331C','Elijah', 'Wood', 65, 'Autraliano')
INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('33344441D','Cillian', 'Murphy', 35, 'Irlandes')
INSERT INTO actores (dni, name, surname, age, nationality) VALUES ('12233344E','Ian', 'Mckellen', 95, 'Austriaco')


INSERT INTO productora (name, founded_in_year) VALUES ('Warner Bros', 1920)
INSERT INTO productora (name, founded_in_year) VALUES ('Universal', 1930)


INSERT INTO peliculas (title, creation_year, director_dni, productora_id ) VALUES ('LOTR', 2001, '12345678A', 1)
INSERT INTO peliculas (title, creation_year, director_dni, productora_id ) VALUES ('OpenHeimer',2023, '98765432B', 2)

INSERT INTO series (title, creation_year, director_dni, productora_id) VALUES ('SerieEjemplo1', 2000, '98765432B',1)
INSERT INTO series (title, creation_year, director_dni, productora_id) VALUES ('SerieEjemplo2', 2020, '12345678A',2)

INSERT INTO actores_de_pelicula (pelicula_id, actor_dni) VALUES (1,'44443331C')
INSERT INTO actores_de_pelicula (pelicula_id, actor_dni) VALUES (1,'12233344E')
INSERT INTO actores_de_pelicula (pelicula_id, actor_dni) VALUES (2,'33344441D')

INSERT INTO actores_de_serie (serie_id, actor_dni) VALUES (1,'33344441D')
INSERT INTO actores_de_serie (serie_id, actor_dni) VALUES (2,'44443331C')
INSERT INTO actores_de_serie (serie_id, actor_dni) VALUES (2,'12233344E')
