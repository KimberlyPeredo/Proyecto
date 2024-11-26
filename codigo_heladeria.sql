-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema heladeria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema heladeria
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `heladeria` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `heladeria` ;

-- -----------------------------------------------------
-- Table `heladeria`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `contacto` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `heladeria`.`empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`empleados` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `heladeria`.`sabores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`sabores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `precio` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `heladeria`.`tamanos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`tamanos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(50) NOT NULL,
  `precio` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `heladeria`.`promociones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`promociones` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(100) NOT NULL,
  `descuento` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `heladeria`.`pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `heladeria`.`pedidos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cliente_id` INT NOT NULL,
  `empleado_id` INT NOT NULL,
  `sabor_id` INT NOT NULL,
  `tamano_id` INT NOT NULL,
  `promocion_id` INT NOT NULL,
  `precio_total` DOUBLE NOT NULL,
  `fecha` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `cliente_id` (`cliente_id` ASC) VISIBLE,
  INDEX `empleado_id` (`empleado_id` ASC) VISIBLE,
  INDEX `sabor_id` (`sabor_id` ASC) VISIBLE,
  INDEX `tamano_id` (`tamano_id` ASC) VISIBLE,
  INDEX `promocion_id` (`promocion_id` ASC) VISIBLE,
  CONSTRAINT `pedidos_ibfk_1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `heladeria`.`clientes` (`id`),
  CONSTRAINT `pedidos_ibfk_2`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `heladeria`.`empleados` (`id`),
  CONSTRAINT `pedidos_ibfk_3`
    FOREIGN KEY (`sabor_id`)
    REFERENCES `heladeria`.`sabores` (`id`),
  CONSTRAINT `pedidos_ibfk_4`
    FOREIGN KEY (`tamano_id`)
    REFERENCES `heladeria`.`tamanos` (`id`),
  CONSTRAINT `pedidos_ibfk_5`
    FOREIGN KEY (`promocion_id`)
    REFERENCES `heladeria`.`promociones` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
