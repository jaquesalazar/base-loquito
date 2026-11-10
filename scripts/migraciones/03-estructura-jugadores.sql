-- Nueva estructura de CatTube para múltiples jugadores.

BEGIN;

CREATE TABLE jugador_videos (
    id_usuario INTEGER NOT NULL,
    id_video INTEGER NOT NULL,
    PRIMARY KEY (id_usuario, id_video),
    CONSTRAINT fk_jugador_videos_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE,
    CONSTRAINT fk_jugador_videos_video
        FOREIGN KEY (id_video)
        REFERENCES videos(id_video)
        ON DELETE CASCADE
);

ALTER TABLE videos
DROP CONSTRAINT fk_videos_usuarios;

ALTER TABLE videos
DROP COLUMN id_usuario;

TRUNCATE TABLE
    comentarios,
    progreso_videos,
    jugador_videos,
    videos,
    usuarios
RESTART IDENTITY;

INSERT INTO usuarios (username, cohesion, dinero, seguidores)
VALUES ('jugador_prueba', 0, 0, 0);

INSERT INTO videos (
    likes,
    titulo,
    vistas,
    tag,
    caption,
    hashtags,
    video_url,
    comments,
    shares
)
VALUES
(
    0,
    'Presentacion de El loquito',
    0,
    'Presentacion',
    'Bienvenidos a mi perfil de CatTube',
    '#bienvenida #cattube',
    'https://cdn.jsdelivr.net/gh/valeriacastillolopez/VideosLoquito@main/YlaPaloma.mp4',
    0,
    0
),
(
    0,
    'Primer video de CatTube',
    0,
    'Entretenimiento',
    'Este es mi primer video',
    '#primerVideo #cattube',
    'https://cdn.jsdelivr.net/gh/valeriacastillolopez/VideosLoquito@main/LosTacos.mp4',
    0,
    0
),
(
    0,
    'Noticias de la comunidad',
    0,
    'Noticias',
    'Las novedades de nuestra comunidad',
    '#noticias #comunidad',
    'https://cdn.jsdelivr.net/gh/valeriacastillolopez/VideosLoquito@main/LosGatosSaben.mp4',
    0,
    0
);

INSERT INTO jugador_videos (id_usuario, id_video)
SELECT u.id_usuario, v.id_video
FROM usuarios u
CROSS JOIN videos v
WHERE u.username = 'jugador_prueba';

INSERT INTO progreso_videos (
    id_usuario,
    id_video,
    liked,
    vistas
)
SELECT
    jv.id_usuario,
    jv.id_video,
    FALSE,
    0
FROM jugador_videos jv;

COMMIT;
