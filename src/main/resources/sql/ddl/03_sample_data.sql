-- ---------- ALUMNOS ----------
INSERT INTO alumno (nif, nombre, email) VALUES
                                            ('50299841Z', 'Rocío García', 'rocio@example.com'),
                                            ('66280457T', 'Miriam López', 'miriam@example.com'),
                                            ('12345678A', 'Adrián Torres', 'adrian@example.com'),
                                            ('98765432B', 'Sara Martín', 'sara@example.com')
    ON CONFLICT (nif) DO NOTHING;

-- ---------- MÓDULOS ----------
INSERT INTO modulo (codigo, nombre, horas) VALUES
                                               ('PSP', 'Programación de Servicios y Procesos', 180),
                                               ('BBDD', 'Bases de Datos', 200),
                                               ('SI', 'Sistemas Informáticos', 120),
                                               ('ED', 'Entornos de Desarrollo', 90)
    ON CONFLICT (codigo) DO NOTHING;

-- ---------- MATRÍCULAS ----------
-- Rocío García
INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES
                                                        ((SELECT id_alumno FROM alumno WHERE nif = '50299841Z'),
                                                         (SELECT id_modulo FROM modulo WHERE codigo = 'PSP'),
                                                         '2024-09-10'),

                                                        ((SELECT id_alumno FROM alumno WHERE nif = '50299841Z'),
                                                         (SELECT id_modulo FROM modulo WHERE codigo = 'BBDD'),
                                                         '2024-09-11')
    ON CONFLICT DO NOTHING;

-- Miriam López
INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES
    ((SELECT id_alumno FROM alumno WHERE nif = '66280457T'),
     (SELECT id_modulo FROM modulo WHERE codigo = 'SI'),
     '2024-09-15')
    ON CONFLICT DO NOTHING;

-- Adrián Torres
INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES
    ((SELECT id_alumno FROM alumno WHERE nif = '12345678A'),
     (SELECT id_modulo FROM modulo WHERE codigo = 'ED'),
     '2024-09-20')
    ON CONFLICT DO NOTHING;

-- Sara Martín
INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES
                                                        ((SELECT id_alumno FROM alumno WHERE nif = '98765432B'),
                                                         (SELECT id_modulo FROM modulo WHERE codigo = 'PSP'),
                                                         '2024-09-18'),
                                                        ((SELECT id_alumno FROM alumno WHERE nif = '98765432B'),
                                                         (SELECT id_modulo FROM modulo WHERE codigo = 'ED'),
                                                         '2024-09-18')
    ON CONFLICT DO NOTHING;
