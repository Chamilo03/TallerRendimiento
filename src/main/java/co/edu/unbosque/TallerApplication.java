package co.edu.unbosque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerApplication.class, args);
	}

}



/**-- Migrations will appear here as you chat with AI

create table categorias (
  id bigint primary key generated always as identity,
  nombre text not null,
  descripcion text,
  categoria_padre_id bigint,
  constraint fk_categoria_padre foreign key (categoria_padre_id) references categorias (id) on delete set null
);

create table productos (
  id bigint primary key generated always as identity,
  nombre text not null,
  descripcion text,
  precio numeric(12, 2) not null,
  categoria_id bigint not null,
  creado_en timestamp default now(),
  actualizado_en timestamp default now(),
  constraint fk_categoria foreign key (categoria_id) references categorias (id)
);

create index idx_productos_nombre on productos using btree (nombre);

create index idx_productos_precio on productos using btree (precio);

create index idx_productos_categoria on productos using btree (categoria_id);

create table inventario (
  id bigint primary key generated always as identity,
  producto_id bigint not null unique,
  cantidad int not null default 0,
  actualizado_en timestamp default now(),
  constraint fk_inventario_producto foreign key (producto_id) references productos (id) on delete cascade
);

create type tipo_transaccion as enum('VENTA', 'ENTRADA', 'DEVOLUCION', 'AJUSTE');

create table transacciones_inventario (
  id bigint primary key generated always as identity,
  producto_id bigint not null,
  tipo tipo_transaccion not null,
  cantidad int not null,
  fecha timestamp default now(),
  usuario_id bigint,
  constraint fk_trans_producto foreign key (producto_id) references productos (id) on delete cascade
);

create index idx_trans_producto_fecha on transacciones_inventario using btree (producto_id, fecha);

create index idx_trans_tipo on transacciones_inventario using btree (tipo);

create table usuarios (
  id bigint primary key generated always as identity,
  nombre text not null,
  correo text unique,
  rol text not null check (rol in ('CLIENTE', 'ANALISTA', 'BODEGA', 'ADMIN'))
);

alter table transacciones_inventario
add constraint fk_trans_usuario foreign key (usuario_id) references usuarios (id) on delete set null;





















Microsoft Windows [Versión 10.0.26100.6584]
(c) Microsoft Corporation. Todos los derechos reservados.

C:\Users\Camilo>docker images
REPOSITORY   TAG          IMAGE ID       CREATED        SIZE
postgres     alpine3.22   70b32afe0c27   47 hours ago   409MB

C:\Users\Camilo>$ docker run --name some-postgres -e POSTGRES_PASSWORD_FILE=/run/secrets/postgres-passwd -d postgres
"$" no se reconoce como un comando interno o externo,
programa o archivo por lotes ejecutable.

C:\Users\Camilo>docker run --name some-postgres -e POSTGRES_PASSWORD_FILE=/run/secrets/postgres-passwd -d postgres
Unable to find image 'postgres:latest' locally
latest: Pulling from library/postgres
af60ce4418c9: Pull complete
eed0ac863490: Pull complete
f0d70120d9e2: Pull complete
dd6d7b9d8ba8: Pull complete
751039babae5: Pull complete
203b16f56a7d: Pull complete
edd90ab5059f: Pull complete
f69a7c424b50: Pull complete
f5af7533693a: Pull complete
a585c5f82f15: Pull complete
9a68d6020eab: Pull complete
8c7716127147: Pull complete
1014e14b3351: Pull complete
2433c366ca00: Pull complete
Digest: sha256:073e7c8b84e2197f94c8083634640ab37105effe1bc853ca4d5fbece3219b0e8
Status: Downloaded newer image for postgres:latest
54e3c1a92a574e5f1e0062ba8636434e24321d4eb51c4579c9fecc032e89f529

C:\Users\Camilo>docker run --name some-postgres -e POSTGRES_PASSWORD=Bdmayo01 -d postgres
docker: Error response from daemon: Conflict. The container name "/some-postgres" is already in use by container "54e3c1a92a574e5f1e0062ba8636434e24321d4eb51c4579c9fecc032e89f529". You have to remove (or rename) that container to be able to reuse that name.

Run 'docker run --help' for more information

C:\Users\Camilo>docker run --name some-postgre -e POSTGRES_PASSWORD=Bdmayo01 -d postgres
b08dc24807bf698eb0a86ee3604dacb3acc01ffc6e60ff019844cdbb5d16e523

C:\Users\Camilo>docker container ls
CONTAINER ID   IMAGE      COMMAND                  CREATED          STATUS          PORTS      NAMES
b08dc24807bf   postgres   "docker-entrypoint.s…"   14 seconds ago   Up 13 seconds   5432/tcp   some-postgre

C:\Users\Camilo>docker exec -it some-postgre bash
root@b08dc24807bf:/# psql -U postgres --password
Password:
psql (18.0 (Debian 18.0-1.pgdg13+3))
Type "help" for help.

postgres=# exit
root@b08dc24807bf:/# exit
exit

C:\Users\Camilo>docker ps -a
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS                      PORTS      NAMES
b08dc24807bf   postgres              "docker-entrypoint.s…"   6 minutes ago    Up 6 minutes                5432/tcp   some-postgre
54e3c1a92a57   postgres              "docker-entrypoint.s…"   8 minutes ago    Exited (1) 8 minutes ago               some-postgres
ce124131cac1   postgres:alpine3.22   "docker-entrypoint.s…"   13 minutes ago   Exited (1) 13 minutes ago              rendimiento

C:\Users\Camilo>docker stop 54e3c1a92a57
54e3c1a92a57

C:\Users\Camilo>docker rm 54e3c1a92a57
54e3c1a92a57

C:\Users\Camilo>docker ps -a
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS                      PORTS      NAMES
b08dc24807bf   postgres              "docker-entrypoint.s…"   8 minutes ago    Up 8 minutes                5432/tcp   some-postgre
ce124131cac1   postgres:alpine3.22   "docker-entrypoint.s…"   15 minutes ago   Exited (1) 15 minutes ago              rendimiento

C:\Users\Camilo>**/




/**
 * 
 * ////////// DOCKER ///////////
 * mvn clean package -DskipTests 
 * 
 * docker compose build
 * 
 * docker compose up
 */