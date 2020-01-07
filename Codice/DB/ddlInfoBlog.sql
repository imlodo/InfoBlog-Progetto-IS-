DROP DATABASE IF EXISTS InfoBlog;

CREATE database InfoBlog;

use InfoBlog;


Create table utente(
email varchar(50),
password varchar(32) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(20) not null,
primary key(email)
);

Create table autore(
email varchar(50),
password varchar(32) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(20) not null,
primary key(email)
);


Create table moderatore(
email varchar(50),
password varchar(32) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(20) not null,
categoriaModerazione varchar(30) not null,
primary key(email)
);

create table articolo(
Titolo varchar(40) not null,
id int auto_increment,
categoria varchar(20) not null,
contenuto varchar(500) not null,
stato enum('daPubblicare','inModerazione','sottoModerazione','Pubblicato') not null default 'daPubblicare',
scrittore varchar(50),
foreign key (scrittore) references autore(email)
on update cascade
on delete cascade,
moderatore varchar(50),
foreign key (moderatore) references moderatore(email)
on update cascade
on delete cascade,
primary key(id)
);

create table notifica(
contenuto varchar(30) not null,
id int auto_increment,
stato enum('inviato','letto') default 'inviato',
autore varchar(50),
foreign key (autore) references autore(email)
on update cascade
on delete cascade,
moderatore varchar(50),
foreign key (moderatore) references moderatore(email)
on update cascade
on delete cascade,
primary key(id,autore,moderatore)
);

create table messaggio(
contenuto varchar (500) not null,
id int auto_increment,
stato enum('inviato','letto') default 'inviato',
autore varchar(50),
foreign key (autore) references autore(email)
on update cascade
on delete cascade,
utente varchar(50),
foreign key (utente) references utente(email)
on update cascade
on delete cascade,
primary key (id)
);

create table commento(
valutazione varchar(300) not null,
id int,
foreign key (id) references articolo(id)
on update cascade
on delete cascade,
email varchar(50),
foreign key (email) references utente(email)
on update cascade
on delete cascade,
primary key (id,email)
);

create table rating(
numeroStelle int not null,
id int,
foreign key (id) references articolo(id)
on update cascade
on delete cascade,
email varchar(50),
foreign key (email) references utente(email)
on update cascade
on delete cascade,
primary key (id,email)
);

create table evento(
data date not null,
via varchar(40) not null,
città varchar(40) not null,
nome varchar(50) not null,
argomento varchar(50) not null,
id int auto_increment,
email varchar(50),
foreign key (email) references autore(email)
on update cascade
on delete cascade,
primary key(id,data,via,città)
);

create table allegato(
percorsoFile varchar(50) not null,
primary key(percorsoFile),
id int,
foreign key (id) references articolo(id)
on update cascade
on delete cascade,
);