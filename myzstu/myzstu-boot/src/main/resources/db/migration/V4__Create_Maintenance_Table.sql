CREATE TABLE zstuca_maintenance  (
       id int(10) NOT NULL AUTO_INCREMENT,
       time BIGINT NOT NULL,
       name varchar(255) NULL DEFAULT NULL,
       telephone varchar(255) NULL DEFAULT NULL,
       qq varchar(255) NULL DEFAULT NULL,
       brand varchar(255) NULL DEFAULT NULL,
       address varchar(255) NULL DEFAULT NULL,
       description varchar(1024) NULL DEFAULT NULL,
       maintenancePeople varchar(255) NULL DEFAULT NULL,
       maintenanceTime BIGINT NULL DEFAULT NULL,
       maintenanceMethod varchar(1024) NULL DEFAULT NULL,
       PRIMARY KEY (id) USING BTREE
)