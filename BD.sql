DROP DATABASE IF EXISTS ConnectWork;
CREATE DATABASE ConnectWork;
USE ConnectWork;

CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(50),
    username VARCHAR(20) UNIQUE,
    password VARCHAR(255) NOT NULL,
    correo VARCHAR(40),
    telefono VARCHAR(10),
    direccion VARCHAR(30),
    cui VARCHAR(13),
    fecha_nacimiento DATE,
    rol ENUM('cliente', 'freelancer', 'administrador') NOT NULL DEFAULT 'cliente',

    activo BOOLEAN DEFAULT TRUE,
    saldo DECIMAL(12,2) DEFAULT 0.00,
    saldo_bloqueado DECIMAL(12,2) DEFAULT 0.00,

    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario_cliente (
    id_usuario INT PRIMARY KEY,
    descripcion TEXT,
    sector VARCHAR(80),
    sitio_web VARCHAR(120),

    FOREIGN KEY (id_usuario)
    REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
);


CREATE TABLE usuario_freelancer (
    id_usuario INT PRIMARY KEY,
    biografia TEXT,
    nivel_experiencia ENUM('junior','semi-senior','senior'),
    tarifa_hora DECIMAL(10,2),

    FOREIGN KEY (id_usuario)
    REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
);

CREATE TABLE categoria(
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE habilidad (
    id_habilidad INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60) UNIQUE,
    descripcion VARCHAR(50),
    id_categoria INT,
    activo BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_categoria_habilidad
        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);



CREATE TABLE historial_porcentaje_comision(
    id_historial_porcentaje INT PRIMARY KEY AUTO_INCREMENT,
    fecha_inicial DATE,
    fecha_final DATE,
    porcentaje_comision DECIMAL(10, 2),
    id_admin INT,

    CONSTRAINT fk_id_admin
        FOREIGN KEY (id_admin)
        REFERENCES usuario(id_usuario)

);

CREATE TABLE proyecto(
    id_proyecto INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,

    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT(100),

    id_categoria INT NOT NULL,

    presupuesto DECIMAL(10, 2) NOT NULL,
    fecha_limite DATE NOT NULL,

    estado ENUM(
    'ABIERTO',
    'EN_PROGRESO',
    'ENTREGA_PENDIENTE',
    'COMPLETADO',
    'CANCELADO'
    ) DEFAULT 'ABIERTO',

    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_id_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES usuario(id_usuario),

    CONSTRAINT fk_id_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)

);

CREATE TABLE proyecto_habilidad (
    id_proyecto INT,
    id_habilidad INT,

    PRIMARY KEY(id_proyecto,id_habilidad),

    FOREIGN KEY (id_proyecto)
    REFERENCES proyecto(id_proyecto)
    ON DELETE CASCADE,

    FOREIGN KEY (id_habilidad)
    REFERENCES habilidad(id_habilidad)
    ON DELETE CASCADE
);


CREATE TABLE propuesta(
    id_propuesta INT PRIMARY KEY AUTO_INCREMENT,
    id_proyecto INT NOT NULL,
    id_freelancer INT NOT NULL,

    monto_ofertado DECIMAL(10, 2),

    plazo_dias INT NOT NULL,
    carta_presentacion TEXT,

    estado ENUM('PENDIENTE', 'ACEPTADA', 'RECHAZADA', 'RETIRADA') DEFAULT 'PENDIENTE',

    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,

    UNIQUE(id_proyecto,id_freelancer),

    FOREIGN KEY (id_proyecto)
    REFERENCES proyecto(id_proyecto)
    ON DELETE CASCADE,

    FOREIGN KEY (id_freelancer)
    REFERENCES usuario(id_usuario)
);


CREATE TABLE contrato(
    id_contrato INT PRIMARY KEY AUTO_INCREMENT,
    id_propuesta INT,

    monto DECIMAL(12,2) NOT NULL,
    porcentaje_comision DECIMAL(5,2) NOT NULL,

    fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_fin DATETIME NULL,

    estado ENUM(
        'ACTIVO',
        'COMPLETADO',
        'CANCELADO'
    ) DEFAULT 'ACTIVO',

    motivo_cancelacion TEXT NULL,

    FOREIGN KEY (id_propuesta)
    REFERENCES propuesta(id_propuesta)

) ENGINE=InnoDB;

CREATE TABLE entrega(
    id_entrega INT PRIMARY KEY AUTO_INCREMENT,
    id_contrato INT,
    fecha_entrega DATE,
    descripcion VARCHAR(100),
    url_archivo VARCHAR(300),


    motivo_rechazo TEXT;
    estado ENUM(
        'PENDIENTE',
        'APROBADA',
        'RECHAZADA'
    ) DEFAULT 'PENDIENTE',


    CONSTRAINT fk_id_contrato
        FOREIGN KEY (id_contrato)
        REFERENCES contrato (id_contrato)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)ENGINE=InnoDB;

CREATE TABLE recarga_saldo (
    id_recarga INT AUTO_INCREMENT PRIMARY KEY,

    id_usuario INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,

    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_usuario)
    REFERENCES usuario(id_usuario)
);

CREATE TABLE solicitud_categoria (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,

    id_cliente INT NOT NULL,

    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(150),

    estado ENUM(
        'PENDIENTE',
        'ACEPTADA',
        'RECHAZADA'
    ) DEFAULT 'PENDIENTE',

    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_cliente)
    REFERENCES usuario(id_usuario)
);

CREATE TABLE solicitud_habilidad (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,

    id_freelancer INT NOT NULL,
    id_categoria INT NOT NULL,

    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(150),

    estado ENUM(
        'PENDIENTE',
        'ACEPTADA',
        'RECHAZADA'
    ) DEFAULT 'PENDIENTE',

    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_freelancer)
    REFERENCES usuario(id_usuario),

    FOREIGN KEY (id_categoria)
    REFERENCES categoria(id_categoria)
);
