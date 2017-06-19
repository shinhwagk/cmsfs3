use cmsfs3;

DROP TABLE IF EXISTS `machine` ;

CREATE TABLE IF NOT EXISTS `cmsfs`.`machine` (
  `name` VARCHAR(20) NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_jdbc` ;

CREATE TABLE IF NOT EXISTS `connect_jdbc` (
  `name` VARCHAR(20) NOT NULL,
  `kind` VARCHAR(20) NOT NULL,
  `port` INT NOT NULL,
  `protocol` VARCHAR(45) NOT NULL,
  `service` VARCHAR(45) NOT NULL,
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `machine_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `f_machine_name_idx` (`machine_name` ASC),
  CONSTRAINT `f_machine_name`
    FOREIGN KEY (`machine_name`)
    REFERENCES `cmsfs`.`machine` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_ssh` ;

CREATE TABLE IF NOT EXISTS `connect_ssh` (
  `name` INT NOT NULL,
  `port` INT NOT NULL,
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  `private_key` TEXT NULL,
  `machine_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `f_machine_name_idx` (`machine_name` ASC),
  CONSTRAINT `f_machine_name`
    FOREIGN KEY (`machine_name`)
    REFERENCES `cmsfs`.`machine` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_group_jdbc` ;

CREATE TABLE IF NOT EXISTS `cmsfs`.`connect_group_jdbc` (
  `name` VARCHAR(20) NOT NULL,
  `connects` JSON NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_group_jdbc_oracle` ;

CREATE TABLE IF NOT EXISTS `cmsfs`.`connect_group_jdbc_oracle` (
  `name` VARCHAR(20) NOT NULL,
  `connects` JSON NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_group_jdbc_mysql` ;

CREATE TABLE IF NOT EXISTS `cmsfs`.`connect_group_jdbc_mysql` (
  `name` VARCHAR(20) NOT NULL,
  `connects` JSON NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `connect_group_ssh` ;

CREATE TABLE IF NOT EXISTS `cmsfs`.`connect_group_ssh` (
  `name` VARCHAR(20) NOT NULL,
  `connects` JSON NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;