CREATE DATABASE turf_management_DB;

show databases;

use turf_management_db;

CREATE TABLE user (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    userFName VARCHAR(50) NOT NULL,
    userLName VARCHAR(50) NOT NULL,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(80) NOT NULL UNIQUE,
    userGroupId int,
    FOREIGN KEY (userGroupId)
        REFERENCES usergroup(userGroupId),
    password VARCHAR(60) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usergroup (
    userGroupId INT AUTO_INCREMENT PRIMARY KEY,
    userGroupName VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE manager (
	managerId int,
    FOREIGN KEY (managerId)
        REFERENCES user (userId),
        locationId int,
    FOREIGN KEY (locationId)
        REFERENCES locations (locationId)
);

CREATE TABLE turfs (
    turfId INT AUTO_INCREMENT PRIMARY KEY,
    turfName VARCHAR(30) NOT NULL,
    locationId int,
    FOREIGN KEY (locationId)
        REFERENCES locations (locationId),
    sizeDetails VARCHAR(60),
    pricing INT NOT NULL,
    availabilty ENUM('enabled', 'disabled') DEFAULT 'disabled',
    min_advance INT
);

CREATE TABLE locations (
    locationId INT AUTO_INCREMENT PRIMARY KEY,
    locationName VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(250) NOT NULL,
    contact_info INT(15) NOT NULL
);

CREATE TABLE bookings (
    bookingId INT AUTO_INCREMENT PRIMARY KEY,
    userId int,
    FOREIGN KEY (userId)
        REFERENCES user (userId),
	TurfId int,
    FOREIGN KEY (TurfId)
        REFERENCES turfs (turfId),
    booking_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    slot TIMESTAMP,
    status ENUM('Confirmed', 'Pending', 'Cancelled') DEFAULT 'Pending'
);

CREATE TABLE payments (
    paymentId varchar(50) PRIMARY KEY,
    bookingId int,
    FOREIGN KEY (bookingId)
        REFERENCES bookings (bookingId),
    amountPaid INT,
    amountDue INT,
    status ENUM('Completed', 'Pending', 'Failed') DEFAULT 'Pending'
);

CREATE TABLE notifications (
    notificationId INT AUTO_INCREMENT PRIMARY KEY,
    userId int,
    FOREIGN KEY (userId)
        REFERENCES user (userId),
	bookingId int,
    FOREIGN KEY (bookingId)
        REFERENCES bookings (bookingId),
    type ENUM('sms', 'WhatsApp', 'Email') DEFAULT 'Whatsapp',
    status ENUM('Sent', 'Failed') DEFAULT 'failed'
);