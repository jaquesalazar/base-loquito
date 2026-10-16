-- Limpieza de datos y reinicio de identificadores

BEGIN;

TRUNCATE TABLE short, usuario
RESTART IDENTITY;

COMMIT;
