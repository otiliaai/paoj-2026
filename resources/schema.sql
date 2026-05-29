use biblioteca_db;

drop table if exists participanti_evenimente;
drop table if exists imprumuturi;
drop table if exists evenimente;
drop table if exists carti;
drop table if exists cititori;
drop table if exists abonamente;
drop table if exists sectiuni;
drop table if exists autori;

create table autori (
    id int primary key auto_increment,
    nume_complet varchar(100) not null,
    nationalitate varchar(50) not null
);

create table sectiuni(
    id int primary key auto_increment,
    nume_sectiune varchar(50) not null unique,
    descriere varchar(255),
    gen_literar varchar(50) not null
);

create table abonamente (
    id int primary key auto_increment,
    tip_abonament varchar(50) not null,
    data_start date not null,
    data_expirare date not null,
    activ boolean not null default true
);

create table cititori(
    id int primary key auto_increment,
    nume_complet varchar(100) not null,
    email varchar(100) not null unique,
    numar_carti int not null default 0,
    abonament_id int unique,

    foreign key (abonament_id) references abonamente(id) on delete set null
);

create table carti(
    id int primary key auto_increment,
    autor_id int not null,
    sectiune_id int not null,
    titlu varchar(50) not null,
    isbn varchar(20) not null unique,
    disponibilitate boolean not null default true,

    foreign key (autor_id) references autori(id) on delete cascade,
    foreign key (sectiune_id) references sectiuni(id) on delete cascade
);

create table evenimente(
    id int primary key auto_increment,
    autor_id int not null,
    titlu varchar(50) not null,
    data_eveniment date not null,
    locatie varchar(100) not null,
    tip_eveniment varchar(50) not null,

    foreign key (autor_id) references autori(id) on delete cascade
);

create table imprumuturi(
    id int primary key auto_increment,
    cititor_id int not null,
    carte_id int not null,
    data_imprumut date not null,
    data_returnare date,
    status varchar(50) not null,

    foreign key (cititor_id) references cititori(id) on delete cascade,
    foreign key (carte_id) references carti(id) on delete cascade
);

create table participanti_evenimente(
    id_eveniment int not null,
    id_cititor int not null,

    primary key (id_cititor,id_eveniment),
    foreign key (id_eveniment) references evenimente(id) on delete cascade ,
    foreign key (id_cititor) references cititori(id) on delete cascade
);