PGDMP                         w            postgres    9.6.1    11.5     o           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            p           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            q           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            r           1262    12401    postgres    DATABASE     �   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE postgres;
             postgres    false            s           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                  postgres    false    2162                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                  false            t           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                       false    1            �            1259    17121    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public       postgres    false            �            1259    17288 
   permission    TABLE     �   CREATE TABLE public.permission (
    id integer NOT NULL,
    name character varying(100),
    api_path character varying(100)
);
    DROP TABLE public.permission;
       public         postgres    false            �            1259    17330    role    TABLE     e   CREATE TABLE public.role (
    id character varying NOT NULL,
    name character varying NOT NULL
);
    DROP TABLE public.role;
       public         postgres    false            �            1259    17366    role_permission_map    TABLE        CREATE TABLE public.role_permission_map (
    id integer NOT NULL,
    role_id character varying,
    permission_id integer
);
 '   DROP TABLE public.role_permission_map;
       public         postgres    false            �            1259    17392 
   user_roles    TABLE     g   CREATE TABLE public.user_roles (
    username character varying(60),
    role character varying(60)
);
    DROP TABLE public.user_roles;
       public         postgres    false            �            1259    17387    users    TABLE     �   CREATE TABLE public.users (
    username character varying(60) NOT NULL,
    password character varying(60),
    enabled boolean
);
    DROP TABLE public.users;
       public         postgres    false            h          0    17288 
   permission 
   TABLE DATA               8   COPY public.permission (id, name, api_path) FROM stdin;
    public       postgres    false    187   c       i          0    17330    role 
   TABLE DATA               (   COPY public.role (id, name) FROM stdin;
    public       postgres    false    188   �       j          0    17366    role_permission_map 
   TABLE DATA               I   COPY public.role_permission_map (id, role_id, permission_id) FROM stdin;
    public       postgres    false    189   �       l          0    17392 
   user_roles 
   TABLE DATA               4   COPY public.user_roles (username, role) FROM stdin;
    public       postgres    false    191          k          0    17387    users 
   TABLE DATA               <   COPY public.users (username, password, enabled) FROM stdin;
    public       postgres    false    190   S       u           0    0    hibernate_sequence    SEQUENCE SET     @   SELECT pg_catalog.setval('public.hibernate_sequence', 8, true);
            public       postgres    false    186            �           2606    17292    permission permission_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.permission DROP CONSTRAINT permission_pkey;
       public         postgres    false    187            �           2606    17339    role role_name_key 
   CONSTRAINT     M   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_name_key UNIQUE (name);
 <   ALTER TABLE ONLY public.role DROP CONSTRAINT role_name_key;
       public         postgres    false    188            �           2606    17373 ,   role_permission_map role_permission_map_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.role_permission_map
    ADD CONSTRAINT role_permission_map_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.role_permission_map DROP CONSTRAINT role_permission_map_pkey;
       public         postgres    false    189            �           2606    17337    role role_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
       public         postgres    false    188            �           2606    17391    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public         postgres    false    190            �           2606    17379 :   role_permission_map role_permission_map_permission_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.role_permission_map
    ADD CONSTRAINT role_permission_map_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES public.permission(id);
 d   ALTER TABLE ONLY public.role_permission_map DROP CONSTRAINT role_permission_map_permission_id_fkey;
       public       postgres    false    2021    189    187            �           2606    17374 4   role_permission_map role_permission_map_role_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.role_permission_map
    ADD CONSTRAINT role_permission_map_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.role(id);
 ^   ALTER TABLE ONLY public.role_permission_map DROP CONSTRAINT role_permission_map_role_id_fkey;
       public       postgres    false    189    188    2025            �           2606    17400    user_roles user_roles_role_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_fkey FOREIGN KEY (role) REFERENCES public.role(name);
 I   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_role_fkey;
       public       postgres    false    2023    191    188            �           2606    17395 #   user_roles user_roles_username_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES public.users(username);
 M   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_username_fkey;
       public       postgres    false    2029    191    190            h   1   x�3�LO-	N�+�/rI-I��)��G�2�,�/FW�!�eL�Y1z\\\ ~*,      i   (   x�340�tt����240�v2�8�C�ܸb���� z��      j   %   x�3400�4a. idr��F@��1W� q��      l   2   x�+J�J,�(JM�tt����*HL�
q��q%%r�8��q��qqq 4?D      k   +   x�+J�J,�(JM�,��J�
Sr�\N]�ș�c����  ��     