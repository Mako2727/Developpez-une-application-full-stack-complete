CREATE DATABASE  IF NOT EXISTS `mddapi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mddapi`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: mddapi
-- ------------------------------------------------------
-- Server version	8.0.42


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-08-08 02:10:30.623258','mako@123.fr','$2a$10$smMNq9RZj/5qDJfngFTBpOxWcEmZ8EoHO/K7i6.0ZZ/Vea261Nd4e','2025-08-08 03:07:52.455427','monnom'),(4,'2025-08-08 02:14:58.604916','marius2@123.fr','$2a$10$4c3N8Jrb9RR2DAKm5AVor.aON/S.rY.Y4iw1D0y9/QmDcSXlIgeSS','2025-08-08 02:14:58.604916','Marius1'),(10,'2025-08-08 02:32:31.763588','marius3@123.fr','$2a$10$w7doQhj0flnYHCMMdKdNM.nHTlLb/h40vEAJaCj8vr1pMGvkcl6ve','2025-08-08 02:32:31.763588','Marius2'),(13,'2025-08-08 02:33:51.434860','marius4@123.fr','$2a$10$1k2VrL3UCuQZRakRq/avneg9YLaMs7uKAG0wATPjRsuDN3MKP2q6G','2025-08-08 02:33:51.434860','Marius5'),(17,'2025-08-08 02:37:49.558509','marius5@123.fr','$2a$10$Whp/sqCut1.yJsNGcyRkRehX1KXzmLgTPgllj6u3HUp5hK.FiQpQi','2025-08-09 02:16:09.793618','johnny'),(18,'2025-08-08 02:38:09.564656','marius6@123.fr','$2a$10$olPxodWG.Teofcj6V0uuAu/KT08zNhdk2fIzsz5BiBUe4M4qbWPvS','2025-08-08 02:38:09.564656','Marius9'),(19,'2025-08-08 03:10:51.652865','marius7@123.fr','$2a$10$04m9zVnfUyIgRLpkUpOS..AhdVw318fCa4XeqBVcC.6.QZlO0AZB.','2025-08-08 03:10:51.652865','Marius10'),(20,'2025-08-11 21:06:39.818188','test@123.fr','$2a$10$TrKDcxVNDLnN877NHtLh/O21hbE3Q7ofDCzxcSmln40SuPORUqtm2','2025-08-11 21:06:39.818188','test angular'),(21,'2025-08-11 21:37:24.820065','nono@ppp.fr','$2a$10$xflIuvrnLMJ3Z5qd94Xq5uOWAFyTmmPjXgAgN0.F.LfgufOxcEB0q','2025-08-13 00:55:23.491453','nicolas'),(22,'2025-08-11 21:39:37.166492','nono1@ppp.fr','$2a$10$JQL8g3Tsi9VjLDAg3HCk7OcqbxLZf0AFWRuiReVj7wZHt9oEOf3FG','2025-08-11 21:39:37.166492','yoga1@studio.com'),(23,'2025-08-11 21:47:29.880546','aa@123.fr','$2a$10$u98Bw/V6FoWc64OJcgS0o.sRhHCfM/X98jkKE0wyiXV9VB/ZuJamS','2025-08-11 21:47:29.880546','test@studio.com'),(24,'2025-08-11 21:53:58.490969','aa1@123.fr','$2a$10$SkswlI/KbdrJxru.ExB5B.AiWBGOo7NyNfw9NRZVRA3IVfE740ct.','2025-08-11 21:53:58.490969','test1@studio.com'),(25,'2025-08-11 21:59:06.871110','aa2@123.fr','$2a$10$o4v/xG6DlBy5YPEVCiZAoenzz9ozrrK1bBeHYg4pVMqNGpA/MNRZu','2025-08-11 21:59:06.871110','test2@studio.com'),(26,'2025-08-11 22:00:33.760556','aa3@123.fr','$2a$10$pmGv5wyfnt/5Pe.k4yS0teDhhjkbIhLzONo7WlM6xsx4ihdWBsNX2','2025-08-11 22:00:33.760556','test3@studio.com'),(27,'2025-08-11 22:09:12.661871','nono3@ppp.fr','$2a$10$53yX7QUTetkXTUeXue.ZBOWlS5q1t2osxbtKV/GfXHj6rjD8dAMne','2025-08-11 22:09:12.661871','yoga3'),(28,'2025-08-11 22:17:34.828135','nono4@ppp.fr','$2a$10$0qH4c./mYHcNPQxeY8k6v.8qCmfSU41edyYpmVLtyai6e9ZlwoKdm','2025-08-11 22:17:34.828135','yoga4'),(29,'2025-08-11 22:30:48.142376','nono5@ppp.fr','$2a$10$0xPUGInAQP1HoJ4yDwpBTO1ZjS806FTF12Y7eA.GNmDn0eZjO.2My','2025-08-11 22:30:48.142376','yoga5'),(31,'2025-08-11 23:35:15.019053','nono6@ppp.fr','$2a$10$JMl2jGolbZZFA9EuoCSr8uexKa4x/tVWrxa1tWvu/r7Lvlni4XWSO','2025-08-11 23:35:15.019053','yoga6'),(32,'2025-08-11 23:44:20.468842','nono7@ppp.fr','$2a$10$DdAmtIZAwE4B.GjUtfWUYO696htyJcVKugLcZ9l3ioGzRNKTdLzZO','2025-08-11 23:44:20.468842','yoga7'),(33,'2025-08-11 23:46:41.796995','nono8@ppp.fr','$2a$10$dn6.BeAfw7OGfsqI8qVKkekoC3pLWtv3kOz7kLE3117PxC2K..X4W','2025-08-11 23:46:41.796995','yoga8'),(34,'2025-08-12 00:23:41.555833','nono9@ppp.fr','$2a$10$IqUnNZNjqXeYhAkcK8mkDuvL.RBv/MY50V1UcFdaJmFU5rARhBEGG','2025-08-12 00:23:41.555833','yoga9'),(35,'2025-08-13 00:56:16.342531','thom@123.fr','$2a$10$WPvhcDv6Oo17SX.1JqkE..MxIRFC0QI06I8YY0sX67qO/Vd5v4aI.','2025-08-13 00:56:16.342531','thom'),(36,'2025-08-13 00:58:22.451161','thom1@123.fr','$2a$10$kOBDfMj0xc9v6s7A/IaD/uS8m.N8BmFh5IuJ5XkZAdeT0nou5oYvK',NULL,'thom1'),(37,'2025-08-13 22:48:50.889754','thom2@123.fr','$2a$10$m8A0ekmpW6CzouSJpS.QkurIMjgApTSFKvCIHJpxP79EB7qn5kU1O',NULL,'thom2'),(38,'2025-08-13 22:50:10.472252','thom3@123.fr','$2a$10$E2CxkvoQy2FuUwjrYphYcex/q8OlW2JIhQR5Dtg27bRiqPO8hKQb6',NULL,'thom3'),(39,'2025-08-15 19:09:21.916022','thom13@123.fr','$2a$10$udzTBJ8We7Hf8wI5/6smdOrVenVm8sicgaU6eAJRvEFwCob5.c9JO','2025-08-15 19:10:36.483205','thom13');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `creator_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7tuhnscjpohbffmp7btit1uff` (`name`),
  KEY `FK39iycweywrsj2dvnhl0uq5lnl` (`creator_id`),
  CONSTRAINT `FK39iycweywrsj2dvnhl0uq5lnl` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'2025-08-09 21:17:50.454745','la vie des oiseaux','topic_1',NULL,18),(2,'2025-08-09 22:17:50.454745','la vie des poissons','topic_2',NULL,17),(3,'2025-08-09 23:17:50.454745','description topic 3','topic_3',NULL,17),(5,'2025-08-09 23:22:29.548839','ma description','topic_4',NULL,36),(6,'2025-08-09 23:25:04.653447','my description','topic_5',NULL,36),(7,'2025-08-09 23:33:06.629536','my description4','topic_6',NULL,17);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6xvn0811tkyo3nfjk2xvqx6ns` (`author_id`),
  KEY `FKrfchr8dax0kfngvvkbteh5n7h` (`topic_id`),
  CONSTRAINT `FK6xvn0811tkyo3nfjk2xvqx6ns` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKrfchr8dax0kfngvvkbteh5n7h` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'my content','2025-08-08 02:10:30.623258','mes amis',NULL,18,1),(2,'le plus ancien','2025-07-08 02:10:30.623258','encore mes amis',NULL,18,1),(3,'ils nagent trés vite','2025-08-08 23:20:02.313251','histoire autour des poissons','2025-08-08 23:20:02.313251',18,2),(4,'ca sens bons','2025-08-08 23:30:25.813426','histoire autour des plantes','2025-08-08 23:30:25.813426',18,2),(5,'cvbcvbv','2025-08-14 01:55:20.874107','bbvc','2025-08-14 01:55:20.874107',36,5),(6,'les baleines aussi ...','2025-08-14 01:55:59.154768','les petits poissons','2025-08-14 01:55:59.154768',36,2),(7,'voici mon nouveau article','2025-08-15 02:40:19.854376','un nouvel article','2025-08-15 02:40:19.854376',36,3),(8,'voici mon article du jeudi soir','2025-08-15 02:43:19.538317','article du jeudi soir','2025-08-15 02:43:19.538317',36,3),(9,'contenu 01','2025-08-15 19:25:02.310354','article test01','2025-08-15 19:25:02.310354',39,2),(10,'l article de vendredi soir','2025-08-22 22:34:11.886978','vendredi soir','2025-08-22 22:34:11.886978',39,5);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn2na60ukhs76ibtpt9burkm27` (`author_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKn2na60ukhs76ibtpt9burkm27` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'my comment',NULL,NULL,18,1),(2,'ca pousse plus vite en été',NULL,NULL,18,4),(3,'2','2025-08-08 23:46:03.297106','2025-08-08 23:46:03.297106',18,4),(4,'ca pousse trés vite en été','2025-08-08 23:46:28.816622','2025-08-08 23:46:28.816622',18,4),(5,'dvsd','2025-08-21 01:07:55.472633',NULL,39,4),(6,'nouveau commentaire','2025-08-21 01:08:07.147760',NULL,39,4),(7,'nouveau commentaire','2025-08-21 18:32:26.027705',NULL,39,2),(8,'coommm','2025-08-22 22:20:58.330570',NULL,39,4),(9,'encore un test','2025-08-22 22:21:06.610615',NULL,39,4),(10,'commentaire de vendredid soir','2025-08-22 22:34:32.516022',NULL,39,10),(11,'new comment','2025-08-23 23:14:17.063721',NULL,39,1),(12,'yes...','2025-08-23 23:14:48.879422',NULL,39,1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `subscribed_at` datetime(6) DEFAULT NULL,
  `topic_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKibqtk993y6263yge9f70q4t3f` (`user_id`,`topic_id`),
  KEY `FKfuag8et2vdg3ds9h8trqx2ldq` (`topic_id`),
  CONSTRAINT `FKfuag8et2vdg3ds9h8trqx2ldq` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`),
  CONSTRAINT `FKhro52ohfqfbay9774bev0qinr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES (2,NULL,1,17),(3,'2025-08-09 00:07:12.297760',1,18),(4,'2025-08-21 02:31:02.838249',2,17),(12,'2025-08-21 02:40:38.285894',7,39),(37,'2025-08-23 23:16:16.849383',3,39);
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

