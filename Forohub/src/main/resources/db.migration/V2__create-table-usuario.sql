create table autor(

    id bigint not null auto_increment,
    nombre varchar(50) not null,
    correo varchar(30) not null unique,
    clave varchar(20) not null unique,



    primary key(id)

);