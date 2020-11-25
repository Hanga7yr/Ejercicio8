-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-11-2020 a las 23:41:41
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `orm_gestion_proyectos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asig_proyecto`
--

CREATE TABLE `asig_proyecto` (
  `dni_emp` char(9) NOT NULL,
  `id_proy` int(11) NOT NULL,
  `f_inicio` date NOT NULL,
  `f_fin` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `asig_proyecto`
--

INSERT INTO `asig_proyecto` (`dni_emp`, `id_proy`, `f_inicio`, `f_fin`) VALUES
('12345678K', 1, '2000-01-03', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `datos_prof`
--

CREATE TABLE `datos_prof` (
  `dni` char(9) NOT NULL,
  `categoria` char(2) NOT NULL,
  `sueldo_bruto_anual` decimal(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `dni` char(9) NOT NULL,
  `nom_emp` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`dni`, `nom_emp`) VALUES
('12344321K', 'p'),
('12345678K', 'Prueba1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emp_plantilla`
--

CREATE TABLE `emp_plantilla` (
  `dni` char(9) NOT NULL,
  `num_emp` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `emp_plantilla`
--

INSERT INTO `emp_plantilla` (`dni`, `num_emp`) VALUES
('12344321K', 2),
('12345678K', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE `proyecto` (
  `id_proy` int(11) NOT NULL,
  `nom_proy` varchar(32) NOT NULL,
  `f_inicio` date NOT NULL,
  `f_fin` date DEFAULT NULL,
  `dni_jefe_proy` char(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`id_proy`, `nom_proy`, `f_inicio`, `f_fin`, `dni_jefe_proy`) VALUES
(1, 'Proy', '2000-01-03', '2000-01-03', '12345678K'),
(2, 'Proyecto10', '2000-01-03', '2000-01-03', '12344321K');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asig_proyecto`
--
ALTER TABLE `asig_proyecto`
  ADD PRIMARY KEY (`dni_emp`,`id_proy`,`f_inicio`),
  ADD KEY `f_asig_proy` (`id_proy`);

--
-- Indices de la tabla `datos_prof`
--
ALTER TABLE `datos_prof`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `emp_plantilla`
--
ALTER TABLE `emp_plantilla`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD PRIMARY KEY (`id_proy`),
  ADD KEY `fk_proy_jefe` (`dni_jefe_proy`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  MODIFY `id_proy` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asig_proyecto`
--
ALTER TABLE `asig_proyecto`
  ADD CONSTRAINT `f_asig_emp` FOREIGN KEY (`dni_emp`) REFERENCES `empleado` (`dni`),
  ADD CONSTRAINT `f_asig_proy` FOREIGN KEY (`id_proy`) REFERENCES `proyecto` (`id_proy`);

--
-- Filtros para la tabla `datos_prof`
--
ALTER TABLE `datos_prof`
  ADD CONSTRAINT `fk_datosprof_empl` FOREIGN KEY (`dni`) REFERENCES `empleado` (`dni`);

--
-- Filtros para la tabla `emp_plantilla`
--
ALTER TABLE `emp_plantilla`
  ADD CONSTRAINT `dni_emp` FOREIGN KEY (`dni`) REFERENCES `empleado` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD CONSTRAINT `fk_proy_jefe` FOREIGN KEY (`dni_jefe_proy`) REFERENCES `emp_plantilla` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
