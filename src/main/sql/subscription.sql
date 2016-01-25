create table periodical_publications.subscription(
	  id int NOT NULL AUTO_INCREMENT,
    id_user int DEFAULT NULL,
    paid tinyint(1) DEFAULT NULL,
    price float DEFAULT NULL,
    start_date datetime DEFAULT NULL,
    end_date datetime DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_user) REFERENCES periodical_publications.user(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;