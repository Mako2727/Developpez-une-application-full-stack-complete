LOCK TABLES `users` WRITE;

INSERT INTO `users` VALUES (1,'2025-08-08 02:10:30.623258','mariusxx@123.fr','$2a$10$Dw2i16szIA.yjztGSjyqmuyO597q3q5CGH0IVvpOCIZMo4pwXdSFu','2025-08-08 03:07:52.455427','mariusxx')

UNLOCK TABLES;

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES 
(1,'2025-08-09 21:17:50.454745','Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Java sit amet justo vel ligula faucibus.','java',NULL,18),
(2,'2025-08-09 22:17:50.454745','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Java enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','angular',NULL,1),
(3,'2025-08-09 23:17:50.454745','is simply dummy text of the printing and typesetting industry. Lorem ipsum dolor sit amet, consectetur adipiscing elit.','rxjs',NULL,1),
(5,'2025-08-09 23:22:29.548839','Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, Java enim ad minim veniam.','mysql',NULL,1),
(6,'2025-08-09 23:25:04.653447','Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Java Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore.','spring',NULL,1),
(7,'2025-08-09 23:33:06.629536','Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Java Curabitur pretium tincidunt lacus.','jwt',NULL,1);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;