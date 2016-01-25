create table periodical_publications.subscription_part(
	  id int NOT NULL AUTO_INCREMENT,
    id_subs int DEFAULT NULL,
    id_publ int DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_subs) REFERENCES periodical_publications.subscription(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY(id_publ) REFERENCES periodical_publications.publication(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;