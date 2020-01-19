DROP DATABASE IF EXISTS InfoBlog;

CREATE database InfoBlog;

use InfoBlog;


Create table utente(
email varchar(50),
password varchar(30) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(30) not null,
primary key(email)
);

Create table autore(
email varchar(50),
password varchar(30) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(30) not null,
primary key(email)
);


Create table moderatore(
email varchar(50),
password varchar(30) not null,
nome varchar(30) not null,
cognome	varchar(30) not null,
username varchar(30) not null,
categoriaModerazione varchar(30) not null,
primary key(email)
);

create table seguace(
utente varchar(50),
foreign key (utente) references utente(email)
on update cascade
on delete cascade,
autore varchar(50),
foreign key (autore) references autore(email)
on update cascade
on delete cascade,
primary key (utente,autore)
);

create table articolo(
Titolo varchar(50) not null,
id int auto_increment,
categoria varchar(20) not null,
contenuto varchar(15000) not null,
stato enum('daPubblicare','inModerazione','respinto','Pubblicato') not null default 'daPubblicare',
dataPubblicazione date,
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
contenuto varchar(700) not null,
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
contenuto varchar (700) not null,
id int auto_increment,
stato enum('inviato','letto') default 'inviato',
data datetime,
mittente varchar(50),
foreign key (mittente) references utente(email)
on update cascade
on delete cascade,
destinatario varchar(50),
foreign key (destinatario) references autore(email)
on update cascade
on delete cascade,
primary key (id)
);

create table risposta(
contenuto varchar (700) not null,
id int auto_increment,
stato enum('inviato','letto') default 'inviato',
data datetime,
mittente varchar(50),
foreign key (mittente) references autore(email)
on update cascade
on delete cascade,
destinatario varchar(50),
foreign key (destinatario) references utente(email)
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
via varchar(30) not null,
città varchar(30) not null,
nome varchar(50) not null,
argomento varchar(500) not null,
id int auto_increment,
email varchar(50),
foreign key (email) references autore(email)
on update cascade
on delete cascade,
primary key(id,data,via,città)
);

create table allegato(
percorsoFile varchar(100) not null,
primary key(percorsoFile),
id int,
foreign key (id) references articolo(id)
on update cascade
on delete cascade
);
