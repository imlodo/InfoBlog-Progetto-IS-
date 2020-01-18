/*FARE IL DROP DEL DB E RICREARLO PRIMA DI FARE IL POPOLA PER QUESTO TESTING*/
USE infoblog;
INSERT INTO `infoblog`.`utente` (`email`, `password`, `nome`, `cognome`, `username`) VALUES ('testutente@test.com', 'TestLogin1!', 'Tester', 'Tester', 'testerUtente');
INSERT INTO `infoblog`.`autore` (`email`, `password`, `nome`, `cognome`, `username`) VALUES ('testautore@test.it', 'TestLogin1!', 'Tester', 'Tester', 'testerAutore');
INSERT INTO `infoblog`.`moderatore` (`email`, `password`, `nome`, `cognome`, `username`, `categoriaModerazione`) VALUES ('testmod@test.com', 'TestLogin1!', 'Tester', 'Tester', 'testerModeratore', 'Hardware');