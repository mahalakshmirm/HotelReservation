-- ----------------------------------------
-- Hotel Reservation System - Manual SQL Queries
-- ----------------------------------------

--  Step 1: Create the Database
CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- Step 2: Create ROOMS table
CREATE TABLE rooms (
    room_number INT PRIMARY KEY,
    room_type VARCHAR(45) NOT NULL,
    room_status VARCHAR(45) DEFAULT 'Available'
);

-- Step 3: Create RESERVATIONS table
CREATE TABLE reservations (
    r_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(45) NOT NULL,
    guest_contact VARCHAR(45) NOT NULL,
    room_number INT NOT NULL,
    r_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_room_number FOREIGN KEY (room_number) REFERENCES rooms(room_number)
);

--  4: Preinsert some data into ROOMS table
INSERT INTO rooms (room_number, room_type, room_status) VALUES
(101,'Single','Reserved'),
(102,'Single','Reserved'),
(103,'Single','Available'),
(104,'Single','Available'),
(105,'Single','Available'),
(106,'Double','Reserved'),
(107,'Double','Reserved'),
(108,'Double','Available'),
(109,'Double','Available'),
(110,'Double','Available'),
(111,'Deluxe','Reserved'),
(112,'Deluxe','Reserved'),
(113,'Deluxe','Available'),
(114,'Deluxe','Available'),
(115,'Deluxe','Available'),
(116,'Deluxe','Available'),
(117,'Deluxe','Available'),
(118,'Deluxe','Available'),
(119,'Deluxe','Available'),
(120,'Deluxe','Available');


