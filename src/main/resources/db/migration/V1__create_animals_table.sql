CREATE TABLE animals (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     description VARCHAR(255) NOT NULL,
     image_url VARCHAR(255) NOT NULL,
     category VARCHAR(255) NOT NULL,
     birth_date TIMESTAMP NOT NULL,
     status VARCHAR(255) NOT NULL
);