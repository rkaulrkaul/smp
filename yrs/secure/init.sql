-- TODO: Put ALL SQL in between `BEGIN TRANSACTION` and `COMMIT`
BEGIN TRANSACTION;

-- TODO: create tables
CREATE TABLE `users` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `username` TEXT NOT NULL UNIQUE,
  `password` TEXT NOT NULL
);

INSERT INTO `users` (id, username, password) VALUES (1, 'cw762',"$2y$10$nFivOV0hsfuy9J9lPMHn0O4gB4ADGE3.m6J2SKxPjdY..4BG3n3Iq"); -- password: password1
INSERT INTO `users` (id, username, password) VALUES (2, 'ajc463','$2y$10$YrOv/pHWB3z9OwuZFxznt.6XqERHh3Sa0RceJbkVtHCQovS7D2icG'); -- pasword: password2
INSERT INTO `users` (id, username, password) VALUES (3, 'ksf56','$2y$10$9X2pamSJGPCuTyS6BkI4Dey3ws9MJ8KsPISypQlJrWFMgvU.u0HES'); -- pasword: password3
INSERT INTO `users` (id, username, password) VALUES (4, 'aan27','$2y$10$X7YkeKRqorCOwPV1ssTFI.Eo9v/wyygi/Zruzt/ew/Is79Acd50hC'); -- pasword: password4
INSERT INTO `users` (id, username, password) VALUES (5, 'jjp282','$2y$10$Z9NrawTG8DppEizsYfWxCeLa.MEkf3vfm95CDDzkgmaceHoWbqRNq'); -- pasword: password5
INSERT INTO `users` (id, username, password) VALUES (6, 'jws427','$2y$10$5slinov1APCV7erJvBVdneOQYbRbrKSONVzF7gv23GrG0dD0AlAB'); -- pasword: password6
INSERT INTO `users` (id, username, password) VALUES (7, 'rcc262','$2y$10$S7HFFRA5nlrKMiO6yw3thecDivHBC3vTBASORIgj6zaC9BS2OIQDy
'); -- pasword: password7
INSERT INTO `users` (id, username, password) VALUES (8, 'cgq43','$2y$10$5vh9A5FsGxDVB59tQuhd.uU3VEdXJg.tseb8I9qMKrcvywS8LKB3S'); -- pasword: password8
INSERT INTO `users` (id, username, password) VALUES (9, 'sl765','$2y$10$z1n/5smkuiJ.bQsJ1aSoe.FPS2FvcBLV2kjwNuqi.6InLAG4Q4BXW'); -- pasword: password9
INSERT INTO `users` (id, username, password) VALUES (10, 'jl2483','$2y$10$k9STlOOQ.KVmqI/Q32FG4eCk7yWeXHBtTAvjIc30IPf3L4ViGGHze'); -- pasword: password10
INSERT INTO `users` (id, username, password) VALUES (11, 'test','$2y$10$WvJFppOMBqBv/fT3.BRY3.men2Hc0xufge24Ej1kdoUpMf4i0loAa'); -- password: test



CREATE TABLE `sessions` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `user_id` INTEGER NOT NULL,
	`session` TEXT NOT NULL UNIQUE
);


CREATE TABLE `albums` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `album_name` TEXT NOT NULL UNIQUE
);


INSERT INTO `albums` (id,album_name) VALUES (1,'Spring 2019');
INSERT INTO `albums` (id,album_name) VALUES (2,'Fall 2018');
INSERT INTO `albums` (id,album_name) VALUES (3,'Spring 2018');
INSERT INTO `albums` (id,album_name) VALUES (4,'Fall 2017');
INSERT INTO `albums` (id,album_name) VALUES (5,'Spring 2017');
INSERT INTO `albums` (id,album_name) VALUES (6,'Fall 2016');
INSERT INTO `albums` (id,album_name) VALUES (7,'Spring 2016');



CREATE TABLE `image_album` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `image_id` INTEGER NOT NULL,
  `album_id` INTEGER NOT NULL
);

INSERT INTO `image_album` (image_id, album_id) VALUES (1,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (2,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (3,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (2,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (3,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (5,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (4,3);
INSERT INTO `image_album` (image_id, album_id) VALUES (6,3);
INSERT INTO `image_album` (image_id, album_id) VALUES (7,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (8,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (9,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (10,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (11,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (12,4);
INSERT INTO `image_album` (image_id, album_id) VALUES (13,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (14,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (15,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (16,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (17,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (18,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (19,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (20,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (21,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (22,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (23,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (24,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (25,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (26,1);
INSERT INTO `image_album` (image_id, album_id) VALUES (27,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (28,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (29,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (30,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (31,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (32,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (33,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (34,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (35,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (36,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (37,5);
INSERT INTO `image_album` (image_id, album_id) VALUES (38,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (39,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (40,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (41,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (42,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (43,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (44,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (45,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (46,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (47,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (48,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (49,7);
INSERT INTO `image_album` (image_id, album_id) VALUES (50,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (51,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (52,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (53,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (54,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (55,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (56,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (57,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (58,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (59,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (60,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (61,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (62,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (63,6);
INSERT INTO `image_album` (image_id, album_id) VALUES (64,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (65,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (66,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (67,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (68,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (69,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (70,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (71,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (72,2);
INSERT INTO `image_album` (image_id, album_id) VALUES (73,3);
INSERT INTO `image_album` (image_id, album_id) VALUES (74,3);
INSERT INTO `image_album` (image_id, album_id) VALUES (75,3);
INSERT INTO `image_album` (image_id, album_id) VALUES (76,3);


CREATE TABLE `images` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `file_name` TEXT NOT NULL,
  `file_ext` TEXT NOT NULL
);

INSERT INTO `images` (id, file_name, file_ext) VALUES (1, 'lollipop.jpg', ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (2, "poster.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (3, "halloween1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (4, "halloween2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (5, "halloween3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (6, "hair.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (7, "puppets1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (8, "puppets2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (9, "puppets3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (10, "puppets4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (11, "puppets5.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (12, "puppets6.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (13, "slingshot1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (14, "slingshot2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (15, "slingshot3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (16, "slingshot4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (17, "zoo1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (18, "zoo2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (19, "zoo3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (20, "zoo4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (21, "zoo5.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (22, "zoo6.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (23, "zoo7.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (24, "zoo8.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (25, "zoo9.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (26, "zoo10.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (27, "group1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (28, "group2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (29, "candles1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (30, "play.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (31, "crafts1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (32, "group3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (33, "crafts2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (34, "crafts3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (35, "bandage1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (36, "crafts4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (37, "bandage2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (38, "slime1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (39, "slime2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (40, "princess1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (41, "slime3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (42, "slime4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (43, "drum1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (44, "drum2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (45, "drum3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (46, "drum4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (47, "party1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (48, "party2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (49, "party3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (50, "apple1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (51, "apple2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (52, "game1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (53, "outside1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (54, "talk1.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (55, "outside2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (56, "talk2.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (57, "party4.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (59, "party5.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (60, "party6.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (61, "party7.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (62, "party8.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (63, "party9.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (64, "outside3.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (65, "crafts5.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (66, "party10.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (67, "party11.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (68, "party12.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (69, "party13.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (70, "party14.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (71, "party15.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (72, "party16.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (73, "party17.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (74, "party18.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (75, "party19.jpg", ".jpg");
INSERT INTO `images` (id, file_name, file_ext) VALUES (76, "party20.jpg", ".jpg");

-- All images are taken from the Cornell YOURS Facebook website --

CREATE TABLE `feedback` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `email` TEXT,
  `program` TEXT NOT NULL,
  `comment` TEXT NOT NULL
);


INSERT INTO `feedback` (email, program, comment) VALUES ("dam359@cornell.edu", 'Monday Makers', 'I had a great time on Monday! Well done!' );
INSERT INTO `feedback` (email, program, comment) VALUES ("ut863@cornell.edu", 'Tech Tuesdays', 'The kids were literally adorable. So happy I came.' );
-- INSERT INTO `feedback` (email, program, comment) VALUES ("lks2@cornell.edu", 'Tech Tuesdays', 'We should have had more snacks!' );
INSERT INTO `feedback` (email, program, comment) VALUES ("pdh421@cornell.edu", 'Literacy Thursdays', 'I had a horrible time on Thursday! Try harder!' );
INSERT INTO `feedback` (email, program, comment) VALUES ("yo93@cornell.edu", 'Wellness Wednesdays', "The presentation/game were kinda boring. Next time, let's have fun with food!" );
INSERT INTO `feedback` (email, program, comment) VALUES ("gjs2@cornell.edu", 'Literacy Thursdays', 'So much fun! I am recommending this to all of my friends!' );
-- INSERT INTO `feedback` (email, program, comment) VALUES ("og82@cornell.edu", 'Literacy Thursdays', 'I loved reading with the kids.' );
-- INSERT INTO `feedback` (email, program, comment) VALUES ("twh10@cornell.edu", 'Monday Makers', 'This is by far my favorite program. Keep up the great work!' );
INSERT INTO `feedback` (email, program, comment) VALUES ("jk465@cornell.edu", 'Tech Tuesdays', 'This was a great opportunity to share my passion for technology with the Ithaca community. So happy to be able to give back.' );
INSERT INTO `feedback` (email, program, comment) VALUES ("ijl48@cornell.edu", 'Wellness Wednesdays', 'I loved doing yoga with the kids. Their comments were really funny. But, we should try more hyper activities that kids love (running and stuff!).' );


CREATE TABLE `mentor_apps` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `net_id` TEXT NOT NULL,
  `year` TEXT NOT NULL,
  `veteran` TEXT NOT NULL,
  `experience` TEXT NOT NULL,
  `strengths` TEXT NOT NULL,
  `why` TEXT NOT NULL,
  `car` TEXT NOT NULL
);


INSERT INTO `mentor_apps` (net_id, year, veteran, experience, strengths, why, car) VALUES ('dam359', 'Sophomore', 'No', 'I have worked with kids in the past and I am in a peer mentorship club on campus for women of color. I am also a Boys & Girls Club volunteer.', 'I am patient and kind.', 'I want to be a mentor to give back to my community.', 'I have a car on campus; I would love to be a driver.');
INSERT INTO `mentor_apps` (net_id, year, veteran, experience, strengths, why, car) VALUES ('hi123', 'Freshman', 'Yes', 'I have worked with kids in the past and I am in a peer mentorship club on campus for women of color. I am also a Boys & Girls Club volunteer.', 'I am fun and love kids. I can cook and make fun snacks for kids.', 'I want to be a mentor to meet Ithaca residents.', 'I do not have a car.');


-- TODO: FOR HASHED PASSWORDS, LEAVE A COMMENT WITH THE PLAIN TEXT PASSWORD!

CREATE TABLE `announcements` (
  'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `name` TEXT,
  `announcement` TEXT NOT NULL
);

INSERT INTO `announcements` (name, announcement) VALUES ("Samantha", "May 7th, 2019 is our end of the year party! All mentors come to Klarman Hall G176 to enjoy some pizza. Come hungry!" );
INSERT INTO `announcements` (name, announcement) VALUES ("Jessica", 'Tech Tuesdays is cancelled for this week. Our program advisor is sick. Feel better Karen!' );

CREATE TABLE `programs`(
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `name` TEXT NOT NULL UNIQUE
);

INSERT INTO `programs` (name)  VALUES ("Tech Tuesdays");
INSERT INTO `programs` (name) VALUES ("Wellness Wednesdays");
INSERT INTO `programs` (name) VALUES ("Literacy Thursdays");
COMMIT;
