CREATE DATABASE bookstores;
CREATE TABLE category (
    categoryID VARCHAR(100) PRIMARY KEY,
    categoryName VARCHAR(500)
);

CREATE TABLE books (
    bookID VARCHAR(100) PRIMARY KEY,
    bookName VARCHAR(500),
    author VARCHAR(100),
    yearPublished INT,
    price DOUBLE,
    quantity INT,
    categoryID VARCHAR(100),
    urlImage VARCHAR(200),
    FOREIGN KEY (categoryID) REFERENCES category(categoryID)
);

CREATE TABLE discount (
    discountID VARCHAR(100) PRIMARY KEY,
    nameDiscount VARCHAR(200),
    typeDiscount VARCHAR(200),
    startDate DATE,
    endDate DATE,
    percent FLOAT(10,2)
);

CREATE TABLE discountdetails (
    bookID VARCHAR(100),
    discountID VARCHAR(100),
    percent FLOAT,
    PRIMARY KEY (bookID, discountID),
    FOREIGN KEY (bookID) REFERENCES books(bookID),
    FOREIGN KEY (discountID) REFERENCES discount(discountID)
);

CREATE TABLE customer (
    customerID VARCHAR(100) PRIMARY KEY,
    lastName VARCHAR(100),
    firstName VARCHAR(100),
    email VARCHAR(200),
    phoneNumber VARCHAR(200),
    gender VARCHAR(10),
    dateOfBirth DATE,
    totalMoney DOUBLE,
    creationDate DATE,
    note VARCHAR(200)
);

CREATE TABLE employees (
    employeeID VARCHAR(100) PRIMARY KEY,
    lastName VARCHAR(100),
    firstName VARCHAR(100),
    position VARCHAR(100),
    email VARCHAR(200),
    phoneNumber VARCHAR(100),
    salary DOUBLE,
    gender VARCHAR(50),
    dateOfBirth DATE,
    note VARCHAR(200)
);

CREATE TABLE orders (
    orderID VARCHAR(100) PRIMARY KEY,
    dayOfEstablishment DATE,
    status VARCHAR(100),
    customerID VARCHAR(100),
    employeeID VARCHAR(100),
    FOREIGN KEY (customerID) REFERENCES customer(customerID),
    FOREIGN KEY (employeeID) REFERENCES employees(employeeID)
);

CREATE TABLE orderdetails (
    bookID VARCHAR(100),
    orderID VARCHAR(100),
    quantity INT,
    price DOUBLE,
    PRIMARY KEY (bookID, orderID),
    FOREIGN KEY (bookID) REFERENCES books(bookID),
    FOREIGN KEY (orderID) REFERENCES orders(orderID)
);

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(200),
    password VARCHAR(200)
);

-- 3. Thêm dữ liệu mẫu

-- category
INSERT INTO category (categoryID, categoryName) VALUES 
('C01', 'Science Fiction'), 
('C02', 'Romance'),
('C03', 'History');

insert into user values(1,'admin','admin');