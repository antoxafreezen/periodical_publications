create table periodical_publications.user(
	  id int NOT NULL AUTO_INCREMENT,
    first_name varchar(30) DEFAULT NUll,
    second_name varchar(30) DEFAULT NULL,
    address varchar(50) DEFAULT NULL,
    email varchar(40) DEFAULT NULL,
    password varchar(45) DEFAULT NULL,
    admin tinyint(1) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY email_unique(email)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;