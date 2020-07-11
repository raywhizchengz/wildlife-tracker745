CREATE DATABASE wildlife_tracker;
\c wildlife_tracker;
CREATE TABLE sightingAnimals(id SERIAL PRIMARY KEY, animalName VARCHAR, rangerid INTEGER);
CREATE TABLE endAnimals(id SERIAL PRIMARY KEY, endangeredName VARCHAR, endangeredHealth VARCHAR, rangerid INTEGER);
CREATE TABLE rangers(id SERIAL PRIMARY KEY, rangerName VARCHAR, rangerLocation VARCHAR);
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;