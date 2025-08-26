LOCK TABLES `users` WRITE;

INSERT INTO `users` VALUES (1,'2025-08-08 02:10:30.623258','mariusxx@123.fr','$2a$10$Dw2i16szIA.yjztGSjyqmuyO597q3q5CGH0IVvpOCIZMo4pwXdSFu','2025-08-08 03:07:52.455427','mariusxx')

UNLOCK TABLES;

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'2025-08-09 21:17:50.454745','la vie des oiseaux','topic_1',NULL,18),
(2,'2025-08-09 22:17:50.454745','la vie des poissons','topic_2',NULL,1),
(3,'2025-08-09 23:17:50.454745','description topic 3','topic_3',NULL,1),
(5,'2025-08-09 23:22:29.548839','ma description','topic_4',NULL,1),
(6,'2025-08-09 23:25:04.653447','my description','topic_5',NULL,1),
(7,'2025-08-09 23:33:06.629536','my description4','topic_6',NULL,1);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;