-- Historial diario de estadísticas individuales de cada jugador.

BEGIN;

CREATE TABLE historial_estadisticas (
    id_historial BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    seguidores INTEGER NOT NULL DEFAULT 0 CHECK (seguidores >= 0),
    dinero INTEGER NOT NULL DEFAULT 0 CHECK (dinero >= 0),
    cohesion INTEGER NOT NULL DEFAULT 0 CHECK (cohesion >= 0),

    CONSTRAINT fk_historial_estadisticas_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE,

    CONSTRAINT uq_historial_estadisticas_usuario_fecha
        UNIQUE (id_usuario, fecha)
);

COMMIT;
