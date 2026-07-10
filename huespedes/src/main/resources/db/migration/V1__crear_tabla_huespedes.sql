CREATE TABLE huespedes (
    run VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    comuna_id BIGINT NOT NULL,
    PRIMARY KEY (run)
);

INSERT INTO huespedes (run, nombre, apellido, telefono, correo, comuna_id) VALUES
('12345678-9', 'Ana', 'Pérez', '912345678', 'ana.perez@example.com', 1),
('23456789-K', 'Carlos', 'Soto', '987654321', 'carlos.soto@example.com', 2),
('34567890-1', 'María', 'González', '956789012', 'maria.gonzalez@example.com', 3),
('45678901-2', 'Pedro', 'Ramírez', '923456789', 'pedro.ramirez@example.com', 4),
('56789012-3', 'Lucía', 'Torres', '934567890', 'lucia.torres@example.com', 5),
('67890123-4', 'Jorge', 'Fernández', '945678901', 'jorge.fernandez@example.com', 6),
('78901234-5', 'Sofía', 'Martínez', '956789013', 'sofia.martinez@example.com', 7),
('89012345-6', 'Diego', 'Castro', '967890124', 'diego.castro@example.com', 8),
('90123456-7', 'Valentina', 'López', '978901235', 'valentina.lopez@example.com', 9),
('11223344-K', 'Felipe', 'Morales', '989012346', 'felipe.morales@example.com', 10);