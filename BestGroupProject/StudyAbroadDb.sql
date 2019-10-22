drop database if exists StudyAbroadTest;
create database StudyAbroadTest;

use StudyAbroadTest;



create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null,
FirstName varchar(35) null,
LastName varchar(35) null,
SchoolName varchar (75) null,
EmergencyContactName varchar (75) null,
EmergencyContactPhone numeric (10) null
);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

create table Trip (
id int primary key auto_increment,
`name` varchar (75) not null,
StartDate date not null,
EndDate date not null);

create table UserTrip (
TripId int not null,
UserId int not null,
primary key (TripId, UserId),
foreign key (TripId) references Trip(id),
foreign key (UserId) references `User`(id)
);

create table Category (
id int primary key,
`name` varchar (30) not null);

create table `Event` (
id int primary key auto_increment,
`name` varchar (30) not null,
StartTime datetime not null,
EndTime datetime not null,
Location varchar (100) null,
Description varchar (500) not null,
CategoryId int null,
TransportId varchar(30) null,
TripId int not null,
foreign key (TripId) references Trip(id),
foreign key (CategoryId) references Category(id));



insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "$2a$10$MYKoD5hXMPWABFYbgDkBy.HEwnwpcYzqSeog/ofBba7ahRkfFoGOi", true),
        (2,"user","$2a$10$MYKoD5hXMPWABFYbgDkBy.HEwnwpcYzqSeog/ofBba7ahRkfFoGOi",true);

insert into `role`(`id`,`role`)
    values(1,"Teacher"), (2,"Student");
    
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);
    
INSERT INTO category (id, `name`)
VALUES
(1, "transportation"),
(2, "hotelReservation"),
(3, "attraction"),
(4, "meal"),
(5, "freetime");