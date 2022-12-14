CREATE TABLE disponibilite (
  id int generated by default as identity primary key,
  jour int,
  date_debut_matin time,
  date_fin_matin time,
  date_debut_midi time,
  date_fin_midi time,
  date_debut_soir time,
  date_fin_soir time,
  nounou_id varchar
);

ALTER TABLE disponibilite ADD FOREIGN KEY (nounou_id) REFERENCES nounou (email);