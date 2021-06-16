create table user
(
  id int(11) NOT NULL auto_increment,
  username varchar(32) NULL,
  password varchar(255) NULL,
  name varchar(32) NULL,
  enabled tinyint(1) NULL,
  locked tinyint(1) NULL,
  openid varchar(64) NULL,
  ecardpw varchar(64) NULL,
  edupw varchar(64) NULL,
  libpw varchar(64) NULL,
  ssopw varchar(64) NULL,
  PRIMARY KEY (`id`)
);
