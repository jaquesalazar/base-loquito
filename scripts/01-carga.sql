
-- Datos iniciales del laboratorio de pruebas CatTube

BEGIN;
-- Perfil único: El loquito
INSERT INTO usuario (
    id_usuario,
    seguidores,
    dinero,
    influencia,
    cohesion
)
VALUES (1, 1200, 500, 75, 80);

-- Shorts pertenecientes a El loquito
INSERT INTO short (
    id_video,
    titulo,
    vistas,
    likes,
    id_usuario
)
VALUES
    (1, 'Presentacion de El loquito', 2500, 350, 1),
    (2, 'Primer video de CatTube', 1800, 210, 1),
    (3, 'Noticias de la comunidad', 950, 125, 1);

-- Sincronizar identificadores autogenerados
SELECT setval(
    pg_get_serial_sequence('usuario', 'id_usuario'),
    (SELECT MAX(id_usuario) FROM usuario)
);

SELECT setval(
    pg_get_serial_sequence('short', 'id_video'),
    (SELECT MAX(id_video) FROM short)
);

COMMIT;
