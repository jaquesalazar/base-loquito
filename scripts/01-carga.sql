-- Datos iniciales del laboratorio de pruebas CatTube

BEGIN;

WITH nuevo_usuario AS (
    INSERT INTO usuarios (
        seguidores,
        dinero,
        cohesion,
        username
    )
    VALUES (
        1200,
        500,
        80,
        'El loquito'
    )
    RETURNING id_usuario
)

INSERT INTO videos (
    titulo,
    vistas,
    likes,
    id_usuario,
    tag,
    caption,
    hashtags,
    video_url
)
SELECT
    datos.titulo,
    datos.vistas,
    datos.likes,
    nuevo_usuario.id_usuario,
    datos.tag,
    datos.caption,
    datos.hashtags,
    datos.video_url
FROM nuevo_usuario
CROSS JOIN (
    VALUES
        (
            'Presentacion de El loquito',
            2500,
            350,
            'Presentacion',
            'Bienvenidos a mi perfil de CatTube',
            '#bienvenida #cattube',
            'https://example.com/videos/presentacion.mp4'
        ),
        (
            'Primer video de CatTube',
            1800,
            210,
            'Entretenimiento',
            'Este es mi primer video',
            '#primerVideo #cattube',
            'https://example.com/videos/primer-video.mp4'
        ),
        (
            'Noticias de la comunidad',
            950,
            125,
            'Noticias',
            'Las novedades de nuestra comunidad',
            '#noticias #comunidad',
            'https://example.com/videos/noticias.mp4'
        )
) AS datos(
    titulo,
    vistas,
    likes,
    tag,
    caption,
    hashtags,
    video_url
);

COMMIT;