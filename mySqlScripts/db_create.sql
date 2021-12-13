-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


DROP SCHEMA IF EXISTS `exhibitions` ;
CREATE SCHEMA IF NOT EXISTS `exhibitions` DEFAULT CHARACTER SET utf8 ;
USE `exhibitions` ;

-- -----------------------------------------------------
-- Table `exhibitions`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`user_role` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`user` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `user_roles_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_user_user_roles1_idx` (`user_roles_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_roles1`
    FOREIGN KEY (`user_roles_id`)
    REFERENCES `exhibitions`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`exhibition_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`exhibition_status` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`exhibition_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL DEFAULT 'UNASSIGNED',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`currency` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`currency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `currency_UNIQUE` (`currency` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`exhibition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`exhibition` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`exhibition` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topic` VARCHAR(45) NOT NULL,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `start_time` TIME NULL,
  `end_time` TIME NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `tickets_available` INT UNSIGNED NOT NULL,
  `status_id` INT NOT NULL,
  `currency_id` INT NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_exhibition_exhibition_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_exhibitions_currency1_idx` (`currency_id` ASC) VISIBLE,
  CONSTRAINT `fk_exhibition_exhibition_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `exhibitions`.`exhibition_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_exhibitions_currency1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `exhibitions`.`currency` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`hall_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`hall_status` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`hall_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL DEFAULT 'FREE',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`hall`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`hall` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`hall` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `floor` INT NULL,
  `floor_space` DOUBLE UNSIGNED NULL,
  `hall_no` INT UNSIGNED NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`, `status_id`),
  INDEX `fk_hall_hall_status1_idx` (`status_id` ASC) VISIBLE,
  CONSTRAINT `fk_hall_hall_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `exhibitions`.`hall_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`exhibition_has_hall`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`exhibition_has_hall` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`exhibition_has_hall` (
  `exhibition_id` INT NOT NULL,
  `hall_id` INT NOT NULL,
  PRIMARY KEY (`exhibition_id`, `hall_id`),
  INDEX `fk_exhibition_has_hall_hall1_idx` (`hall_id` ASC) VISIBLE,
  INDEX `fk_exhibition_has_hall_exhibition1_idx` (`exhibition_id` ASC) VISIBLE,
  UNIQUE INDEX `hall_id_UNIQUE` (`hall_id` ASC) VISIBLE,
  CONSTRAINT `fk_exhibition_has_hall_exhibition1`
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibitions`.`exhibition` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exhibition_has_hall_hall1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `exhibitions`.`hall` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`booking_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`booking_status` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`booking_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exhibitions`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exhibitions`.`booking` ;

CREATE TABLE IF NOT EXISTS `exhibitions`.`booking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ticket_qty` INT NOT NULL DEFAULT 1,
  `exhibition_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_booking_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_booking_booking_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_booking_exhibition1_idx` (`exhibition_id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `exhibitions`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_booking_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `exhibitions`.`booking_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_exhibition1`
    FOREIGN KEY (`exhibition_id`)
    REFERENCES `exhibitions`.`exhibition` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
