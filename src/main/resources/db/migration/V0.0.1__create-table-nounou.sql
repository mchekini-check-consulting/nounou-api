create table if not exists nounou (
  email varchar primary key,
  nom varchar(50),
  prenom varchar(50),
  rue varchar(300),
  code_postal varchar(300),
  ville varchar(300),
  numero_telephone varchar(10),
  pseudo varchar(50)
);
