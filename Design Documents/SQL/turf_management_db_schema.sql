CREATE DATABASE turf_management_DB;

show databases;

use turf_management_db;

CREATE TABLE usergroup (
    usergroup_id INT AUTO_INCREMENT PRIMARY KEY,
    usergroup_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_fname VARCHAR(50) NOT NULL,
    user_lname VARCHAR(50) NOT NULL,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(80) NOT NULL UNIQUE,
    usergroup_id int,
    FOREIGN KEY (usergroup_id)
        REFERENCES usergroup(usergroup_id),
    password VARCHAR(60) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(250) NOT NULL,
    contact_info INT NOT NULL
);

CREATE TABLE turfs (
    turf_id INT AUTO_INCREMENT PRIMARY KEY,
    turf_name VARCHAR(30) NOT NULL,
    location_id int,
    FOREIGN KEY (location_id)
        REFERENCES locations (location_id),
    size_details VARCHAR(60),
    pricing INT NOT NULL,
    availabilty ENUM('enabled', 'disabled') DEFAULT 'disabled',
    min_advance INT
);

CREATE TABLE manager (
	manager_id int,
    FOREIGN KEY (manager_id)
        REFERENCES user (user_id),
        location_id int,
    FOREIGN KEY (location_id)
        REFERENCES locations (location_id)
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id int,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
	turf_id int,
    FOREIGN KEY (turf_id)
        REFERENCES turfs (turf_id),
    booking_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    slot TIMESTAMP,
    current_status ENUM('Confirmed', 'Pending', 'Cancelled') DEFAULT 'Pending'
);

CREATE TABLE payments (
    payment_id varchar(50) PRIMARY KEY,
    booking_id int,
    FOREIGN KEY (booking_id)
        REFERENCES bookings (booking_id),
    amount_paid INT,
    amount_due INT,
    current_status ENUM('Completed', 'Pending', 'Failed') DEFAULT 'Pending'
);

CREATE TABLE notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id int,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
	booking_id int,
    FOREIGN KEY (booking_id)
        REFERENCES bookings (booking_id),
    notification_type ENUM('sms', 'WhatsApp', 'Email') DEFAULT 'Whatsapp',
    current_status ENUM('Sent', 'Failed') DEFAULT 'failed'
);