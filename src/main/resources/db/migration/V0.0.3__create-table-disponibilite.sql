CREATE TABLE "disponibilite" (
  jour int,
  date_debut_matin timestamp,
  date_fin_matin timestamp,
  date_debut_midi timestamp,
  date_fin_midi timestamp,
  date_debut_soir timestamp,
  date_fin_soir timestamp,
  nounou_id varchar,
  PRIMARY KEY (nounou_id, jour)
);

ALTER TABLE disponibilite ADD FOREIGN KEY (nounou_id) REFERENCES nounou (email);