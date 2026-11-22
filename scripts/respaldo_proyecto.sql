--
-- PostgreSQL database dump
--

\restrict c5D7MRThHJGEXT2l7L8baPWkYLgh0Vxpt8IUAGDPzwYarcg8ZTS6OBXeCbIdj3V

-- Dumped from database version 17.11
-- Dumped by pg_dump version 18.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: comentarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comentarios (
    id_comentario integer NOT NULL,
    id_usuario integer NOT NULL,
    id_video integer NOT NULL,
    texto text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.comentarios OWNER TO postgres;

--
-- Name: comentarios_id_comentario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.comentarios ALTER COLUMN id_comentario ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.comentarios_id_comentario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: historial_estadisticas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historial_estadisticas (
    id_historial bigint NOT NULL,
    id_usuario integer NOT NULL,
    fecha date DEFAULT CURRENT_DATE NOT NULL,
    seguidores integer DEFAULT 0 NOT NULL,
    dinero integer DEFAULT 0 NOT NULL,
    cohesion integer DEFAULT 0 NOT NULL,
    CONSTRAINT historial_estadisticas_cohesion_check CHECK ((cohesion >= 0)),
    CONSTRAINT historial_estadisticas_dinero_check CHECK ((dinero >= 0)),
    CONSTRAINT historial_estadisticas_seguidores_check CHECK ((seguidores >= 0))
);


ALTER TABLE public.historial_estadisticas OWNER TO postgres;

--
-- Name: historial_estadisticas_id_historial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.historial_estadisticas ALTER COLUMN id_historial ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.historial_estadisticas_id_historial_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: jugador_videos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jugador_videos (
    id_usuario integer NOT NULL,
    id_video integer NOT NULL
);


ALTER TABLE public.jugador_videos OWNER TO postgres;

--
-- Name: progreso_videos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.progreso_videos (
    id_usuario integer NOT NULL,
    id_video integer NOT NULL,
    liked boolean DEFAULT false NOT NULL,
    vistas integer DEFAULT 0 NOT NULL,
    likes_automaticos integer DEFAULT 0 NOT NULL,
    ultima_actualizacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    seguidores_ultima_actualizacion integer DEFAULT 0 NOT NULL,
    CONSTRAINT chk_progreso_vistas CHECK ((vistas >= 0))
);


ALTER TABLE public.progreso_videos OWNER TO postgres;

--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id_usuario integer NOT NULL,
    cohesion integer,
    dinero integer,
    seguidores integer,
    username character varying(50) NOT NULL
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.usuarios ALTER COLUMN id_usuario ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuarios_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: videos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.videos (
    id_video integer NOT NULL,
    likes integer DEFAULT 0,
    titulo character varying(150),
    vistas integer,
    tag character varying(30),
    caption text NOT NULL,
    hashtags character varying(200),
    video_url text NOT NULL,
    comments integer DEFAULT 0,
    shares integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.videos OWNER TO postgres;

--
-- Name: videos_id_video_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.videos ALTER COLUMN id_video ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.videos_id_video_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: comentarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comentarios OVERRIDING SYSTEM VALUE VALUES (9, 15, 3, 'hola hola', '2026-11-15 23:00:51.824237');
INSERT INTO public.comentarios OVERRIDING SYSTEM VALUE VALUES (10, 16, 1, 'jaja eso que', '2026-11-16 00:27:57.106204');
INSERT INTO public.comentarios OVERRIDING SYSTEM VALUE VALUES (11, 16, 1, 'Prueba de sincronización', '2026-11-16 13:20:16.008998');
INSERT INTO public.comentarios OVERRIDING SYSTEM VALUE VALUES (13, 16, 5, 'se podra comentar?', '2026-11-16 23:30:17.475436');
INSERT INTO public.comentarios OVERRIDING SYSTEM VALUE VALUES (14, 16, 2, 'Prueba offline video 2', '2026-11-17 00:19:33.475752');


--
-- Data for Name: historial_estadisticas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (4, 15, '2026-11-12', 500, 500, 30);
INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (5, 16, '2026-11-16', 310, 260, 40);
INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (6, 18, '2026-11-18', 420, 350, 65);
INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (7, 19, '2026-11-18', 7, 16, 50);
INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (8, 21, '2026-11-18', 3, 5, 50);
INSERT INTO public.historial_estadisticas OVERRIDING SYSTEM VALUE VALUES (9, 22, '2026-11-18', 3, 5, 50);


--
-- Data for Name: jugador_videos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.jugador_videos VALUES (15, 3);
INSERT INTO public.jugador_videos VALUES (16, 1);
INSERT INTO public.jugador_videos VALUES (16, 5);
INSERT INTO public.jugador_videos VALUES (16, 8);
INSERT INTO public.jugador_videos VALUES (16, 2);
INSERT INTO public.jugador_videos VALUES (16, 3);
INSERT INTO public.jugador_videos VALUES (16, 4);
INSERT INTO public.jugador_videos VALUES (18, 6);
INSERT INTO public.jugador_videos VALUES (18, 7);
INSERT INTO public.jugador_videos VALUES (18, 3);
INSERT INTO public.jugador_videos VALUES (22, 1);


--
-- Data for Name: progreso_videos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.progreso_videos VALUES (15, 3, true, 6301450, 1043200, '2026-11-19 01:26:56.357888', 500);
INSERT INTO public.progreso_videos VALUES (16, 1, true, 3759006, 631905, '2026-11-19 01:26:45.257204', 310);
INSERT INTO public.progreso_videos VALUES (16, 2, true, 2429267, 404130, '2026-11-19 01:26:38.221991', 310);
INSERT INTO public.progreso_videos VALUES (16, 3, false, 2442172, 406190, '2026-11-19 01:26:54.774724', 310);
INSERT INTO public.progreso_videos VALUES (16, 4, false, 2426277, 413465, '2026-11-19 01:26:09.489613', 310);
INSERT INTO public.progreso_videos VALUES (16, 5, true, 3795754, 630665, '2026-11-19 01:26:45.257204', 310);
INSERT INTO public.progreso_videos VALUES (16, 8, true, 3799377, 631115, '2026-11-19 01:26:45.257204', 310);
INSERT INTO public.progreso_videos VALUES (18, 3, true, 1909812, 317610, '2026-11-19 01:26:32.740096', 420);
INSERT INTO public.progreso_videos VALUES (18, 6, false, 1922666, 321455, '2026-11-19 01:26:32.740096', 420);
INSERT INTO public.progreso_videos VALUES (18, 7, false, 1893211, 317750, '2026-11-19 01:26:32.740096', 420);
INSERT INTO public.progreso_videos VALUES (22, 1, false, 320, 15, '2026-11-19 01:26:40.062117', 3);


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (15, 30, 500, 500, 'Gonzalo');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (16, 40, 260, 310, 'Jaque');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (18, 65, 350, 420, 'Alex');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (19, 50, 16, 7, 'Fernando');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (20, 0, 0, 0, 'Denise');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (21, 50, 5, 3, 'Juan');
INSERT INTO public.usuarios OVERRIDING SYSTEM VALUE VALUES (22, 50, 5, 3, 'Ulises');


--
-- Data for Name: videos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (1, 0, 'La fuente cambia según tus pensamientos', 0, 'Teoría', 'La fuente cambia según tus pensamientos', '#Mente #colores #pensamientos', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/fuente.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (2, 0, 'La fuente mantiene el equilibrio de la plaza', 0, 'Teoría', 'La fuente mantiene el equilibrio de la plaza', '#Temblores #equilibrio #fuente', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/FuenteChiquita.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (3, 0, 'La paloma es un dron espía', 0, 'Teoría', 'La paloma es un dron espía', '#Paloma #Cámara #Espionaje', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/YlaPaloma.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (5, 0, 'El WIFI se alimenta de las conversaciones', 0, 'Teoría', 'El WIFI se alimenta de las conversaciones', '#Conversaciones #WIFI #Energía', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/ElWaifi.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (6, 0, 'Los gatos, mugrosos gatos', 0, 'Teoría', 'Los gatos, mugrosos gatos', '#Gobierno #gatos #verdad', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/LosGatosSaben.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (7, 0, 'Los puestos de comida sirven pa otra cosa', 0, 'Teoría', 'Los puestos de comida sirven pa otra cosa', '#Tacos #secta #precios', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/LosTacos.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (8, 0, 'Las alcantarillas respiran', 0, 'Teoría', 'Las alcantarillas respiran', '#Coladeras #respiración #ciudad', 'https://raw.githubusercontent.com/valeriacastillolopez/loquito_app/main/Coladeras.mp4', 0, 0, '2026-11-16 03:25:56.357888');
INSERT INTO public.videos OVERRIDING SYSTEM VALUE VALUES (4, 0, 'Los puentes cambian de lugar durante la noche', 0, 'Teoría', 'Los puentes cambian de lugar durante la noche', '#Puentes #dimensiones #ubicación', 'https://raw.githubusercontent.com/valeriacastillolopez/VideosLoquito/main/ElPuenteSeMueve.mp4', 0, 0, '2026-11-16 03:25:56.357888');


--
-- Name: comentarios_id_comentario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comentarios_id_comentario_seq', 14, true);


--
-- Name: historial_estadisticas_id_historial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historial_estadisticas_id_historial_seq', 9, true);


--
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_usuario_seq', 22, true);


--
-- Name: videos_id_video_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.videos_id_video_seq', 8, true);


--
-- Name: comentarios comentarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentarios
    ADD CONSTRAINT comentarios_pkey PRIMARY KEY (id_comentario);


--
-- Name: historial_estadisticas historial_estadisticas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_estadisticas
    ADD CONSTRAINT historial_estadisticas_pkey PRIMARY KEY (id_historial);


--
-- Name: jugador_videos jugador_videos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jugador_videos
    ADD CONSTRAINT jugador_videos_pkey PRIMARY KEY (id_usuario, id_video);


--
-- Name: progreso_videos progreso_videos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.progreso_videos
    ADD CONSTRAINT progreso_videos_pkey PRIMARY KEY (id_usuario, id_video);


--
-- Name: historial_estadisticas uq_historial_estadisticas_usuario_fecha; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_estadisticas
    ADD CONSTRAINT uq_historial_estadisticas_usuario_fecha UNIQUE (id_usuario, fecha);


--
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id_usuario);


--
-- Name: videos videos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.videos
    ADD CONSTRAINT videos_pkey PRIMARY KEY (id_video);


--
-- Name: idx_comentarios_usuario; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_comentarios_usuario ON public.comentarios USING btree (id_usuario);


--
-- Name: idx_comentarios_video; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_comentarios_video ON public.comentarios USING btree (id_video);


--
-- Name: comentarios fk_comentario_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentarios
    ADD CONSTRAINT fk_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario) ON DELETE CASCADE;


--
-- Name: comentarios fk_comentario_video; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comentarios
    ADD CONSTRAINT fk_comentario_video FOREIGN KEY (id_video) REFERENCES public.videos(id_video) ON DELETE CASCADE;


--
-- Name: historial_estadisticas fk_historial_estadisticas_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_estadisticas
    ADD CONSTRAINT fk_historial_estadisticas_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario) ON DELETE CASCADE;


--
-- Name: jugador_videos fk_jugador_videos_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jugador_videos
    ADD CONSTRAINT fk_jugador_videos_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario) ON DELETE CASCADE;


--
-- Name: jugador_videos fk_jugador_videos_video; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jugador_videos
    ADD CONSTRAINT fk_jugador_videos_video FOREIGN KEY (id_video) REFERENCES public.videos(id_video) ON DELETE CASCADE;


--
-- Name: progreso_videos fk_progreso_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.progreso_videos
    ADD CONSTRAINT fk_progreso_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario) ON DELETE CASCADE;


--
-- Name: progreso_videos fk_progreso_video; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.progreso_videos
    ADD CONSTRAINT fk_progreso_video FOREIGN KEY (id_video) REFERENCES public.videos(id_video) ON DELETE CASCADE;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT USAGE ON SCHEMA public TO postgres;
GRANT USAGE ON SCHEMA public TO anon;
GRANT USAGE ON SCHEMA public TO authenticated;
GRANT USAGE ON SCHEMA public TO service_role;


--
-- Name: TABLE comentarios; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.comentarios TO anon;
GRANT ALL ON TABLE public.comentarios TO authenticated;
GRANT ALL ON TABLE public.comentarios TO service_role;


--
-- Name: SEQUENCE comentarios_id_comentario_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.comentarios_id_comentario_seq TO anon;
GRANT ALL ON SEQUENCE public.comentarios_id_comentario_seq TO authenticated;
GRANT ALL ON SEQUENCE public.comentarios_id_comentario_seq TO service_role;


--
-- Name: TABLE historial_estadisticas; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.historial_estadisticas TO anon;
GRANT ALL ON TABLE public.historial_estadisticas TO authenticated;
GRANT ALL ON TABLE public.historial_estadisticas TO service_role;


--
-- Name: SEQUENCE historial_estadisticas_id_historial_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.historial_estadisticas_id_historial_seq TO anon;
GRANT ALL ON SEQUENCE public.historial_estadisticas_id_historial_seq TO authenticated;
GRANT ALL ON SEQUENCE public.historial_estadisticas_id_historial_seq TO service_role;


--
-- Name: TABLE jugador_videos; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.jugador_videos TO anon;
GRANT ALL ON TABLE public.jugador_videos TO authenticated;
GRANT ALL ON TABLE public.jugador_videos TO service_role;


--
-- Name: TABLE progreso_videos; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.progreso_videos TO anon;
GRANT ALL ON TABLE public.progreso_videos TO authenticated;
GRANT ALL ON TABLE public.progreso_videos TO service_role;


--
-- Name: TABLE usuarios; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.usuarios TO anon;
GRANT ALL ON TABLE public.usuarios TO authenticated;
GRANT ALL ON TABLE public.usuarios TO service_role;


--
-- Name: SEQUENCE usuarios_id_usuario_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.usuarios_id_usuario_seq TO anon;
GRANT ALL ON SEQUENCE public.usuarios_id_usuario_seq TO authenticated;
GRANT ALL ON SEQUENCE public.usuarios_id_usuario_seq TO service_role;


--
-- Name: TABLE videos; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.videos TO anon;
GRANT ALL ON TABLE public.videos TO authenticated;
GRANT ALL ON TABLE public.videos TO service_role;


--
-- Name: SEQUENCE videos_id_video_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.videos_id_video_seq TO anon;
GRANT ALL ON SEQUENCE public.videos_id_video_seq TO authenticated;
GRANT ALL ON SEQUENCE public.videos_id_video_seq TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON SEQUENCES TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: supabase_admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON SEQUENCES TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON SEQUENCES TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON SEQUENCES TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON SEQUENCES TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON FUNCTIONS TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON FUNCTIONS TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON FUNCTIONS TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON FUNCTIONS TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: public; Owner: supabase_admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON FUNCTIONS TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON FUNCTIONS TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON FUNCTIONS TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON FUNCTIONS TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT ALL ON TABLES TO service_role;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: supabase_admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON TABLES TO postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON TABLES TO anon;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON TABLES TO authenticated;
ALTER DEFAULT PRIVILEGES FOR ROLE supabase_admin IN SCHEMA public GRANT ALL ON TABLES TO service_role;


--
-- PostgreSQL database dump complete
--

\unrestrict c5D7MRThHJGEXT2l7L8baPWkYLgh0Vxpt8IUAGDPzwYarcg8ZTS6OBXeCbIdj3V

