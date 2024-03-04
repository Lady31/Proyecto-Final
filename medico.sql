create database medico
use medico 

create table personal_medico (
id_medico int auto_increment primary key,
	usuario varchar(10),
	codigo varchar(10),
	cedula varchar (10),
	contraseña varchar (15),
	rol varchar (10)
);

create table administrador (
	id_administrador int auto_increment primary key,
	usuario varchar(10),
	codigo varchar(10),
	cedula varchar (10),
	contraseña varchar (15),
	rol varchar (10)
    );

create table pacientes (
	id_paciente int auto_increment primary key,
    nombre varchar (25),
	cedula varchar (10),
	fecha_nacimiento date,
	genero varchar (10),
	telefono varchar(10),
	correo_electronico varchar (35)
);

create table citas (
	id_cita int auto_increment primary key,
    id_paciente int,
    fecha date, 
    hora time,
    comentarios text,
    foreign key (id_paciente) references pacientes (id_paciente)
    );
    
    create table tratamientos(
    id_tratamiento int primary key,
    id_paciente int,
    id_medico int,
    description text,
    fecha_incio date,
    facha_fin date,
    foreign key (id_paciente) references pacientes(id_paciente),
    foreign key (id_medico) references personal_medico (id_medico)
    );
    
    create table medicamentos(
	id_madicamentos int primary key, 
    nombre varchar (100),
    description text 
    );
    
    create table historiales_medicos(
    id_historial int primary key,
    id_paciente int,
    id_medico int,
    fecha date,
    diagnostico text,
    notas text,
    foreign key (id_paciente) references pacientes (id_paciente),
    foreign key (id_medico) references personal_medico (id_medico)
    );
    
    create table resultados_examenes(
    id_resultado_examen int primary key,
    id_paciente int,
    id_medico int,
    fecha date,
    tipo_examen varchar (100),
    resultado text,
    foreign key (id_paciente) references pacientes (id_paciente),
    foreign key (id_medico) references personal_medico (id_medico)
    );
    
    insert into personal_medico(usuario, codigo, cedula, contraseña, rol)
    values
    ('Scarlett','123','1721779583','123456','medico'),
    ('David','456','1724154898','654321','medico');
    
    insert into administrador(usuario, codigo, cedula, contraseña, rol)
    values
    ('Lady','789', '1726423005','987654','admin'),
    ('Yuverly', '987','1720970779','456789','admin');
    select * from administrador;
    
    delete from administrador where id_administrador = 2;
    
    insert into administrador(usuario, codigo, cedula, contraseña, rol)
    values
    ('Hidokun','206', '1108226431','fnfsus','admin')