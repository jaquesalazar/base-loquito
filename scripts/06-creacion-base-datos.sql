-- Creación de la estructura actual de la base de datos CatTube.

CREATE TABLE public.usuarios (
    id_usuario INTEGER GENERATED ALWAYS AS IDENTITY,
    cohesion INTEGER,
    dinero INTEGER,
    seguidores INTEGER,
    username VARCHAR(50) NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (id_usuario)
);

CREATE TABLE public.videos (
    id_video INTEGER GENERATED ALWAYS AS IDENTITY,
    likes INTEGER DEFAULT 0,
    titulo VARCHAR(150),
    vistas INTEGER,
    tag VARCHAR(30),
    caption TEXT NOT NULL,
    hashtags VARCHAR(200),
    video_url TEXT NOT NULL,
    comments INTEGER DEFAULT 0,
    shares INTEGER DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    CONSTRAINT videos_pkey PRIMARY KEY (id_video)
);

CREATE TABLE public.jugador_videos (
    id_usuario INTEGER NOT NULL,
    id_video INTEGER NOT NULL,
    CONSTRAINT jugador_videos_pkey PRIMARY KEY (id_usuario, id_video),
    CONSTRAINT fk_jugador_videos_usuario
        FOREIGN KEY (id_usuario) REFERENCES public.usuarios (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_jugador_videos_video
        FOREIGN KEY (id_video) REFERENCES public.videos (id_video) ON DELETE CASCADE
);

CREATE TABLE public.progreso_videos (
    id_usuario INTEGER NOT NULL,
    id_video INTEGER NOT NULL,
    liked BOOLEAN NOT NULL DEFAULT false,
    vistas INTEGER NOT NULL DEFAULT 0,
    likes_automaticos INTEGER NOT NULL DEFAULT 0,
    ultima_actualizacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    seguidores_ultima_actualizacion INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT progreso_videos_pkey PRIMARY KEY (id_usuario, id_video),
    CONSTRAINT chk_progreso_vistas CHECK (vistas >= 0),
    CONSTRAINT fk_progreso_usuario
        FOREIGN KEY (id_usuario) REFERENCES public.usuarios (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_progreso_video
        FOREIGN KEY (id_video) REFERENCES public.videos (id_video) ON DELETE CASCADE
);

CREATE TABLE public.comentarios (
    id_comentario INTEGER GENERATED ALWAYS AS IDENTITY,
    id_usuario INTEGER NOT NULL,
    id_video INTEGER NOT NULL,
    texto TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT comentarios_pkey PRIMARY KEY (id_comentario),
    CONSTRAINT fk_comentario_usuario
        FOREIGN KEY (id_usuario) REFERENCES public.usuarios (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_comentario_video
        FOREIGN KEY (id_video) REFERENCES public.videos (id_video) ON DELETE CASCADE
);

CREATE TABLE public.historial_estadisticas (
    id_historial BIGINT GENERATED ALWAYS AS IDENTITY,
    id_usuario INTEGER NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    seguidores INTEGER NOT NULL DEFAULT 0,
    dinero INTEGER NOT NULL DEFAULT 0,
    cohesion INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT historial_estadisticas_pkey PRIMARY KEY (id_historial),
    CONSTRAINT uq_historial_estadisticas_usuario_fecha UNIQUE (id_usuario, fecha),
    CONSTRAINT historial_estadisticas_seguidores_check CHECK (seguidores >= 0),
    CONSTRAINT historial_estadisticas_dinero_check CHECK (dinero >= 0),
    CONSTRAINT historial_estadisticas_cohesion_check CHECK (cohesion >= 0),
    CONSTRAINT fk_historial_estadisticas_usuario
        FOREIGN KEY (id_usuario) REFERENCES public.usuarios (id_usuario) ON DELETE CASCADE
);
