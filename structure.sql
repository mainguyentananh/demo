create database demo;
use demo;
drop database demo;

create table chuc_vu(
	id int primary key auto_increment,
    ten_chuc_vu varchar(255) not null,
    tham_quyen varchar(255) not null
);

create table phong_ban(
	id int primary key auto_increment,
    ten_phong_ban varchar(255) not null,
    chuc_nang varchar(255) not null
);

create table nhan_vien(
	id int primary key auto_increment,
    ma_nv varchar(255) not null,
    ho_va_ten varchar(255) not null,
    ngay_sinh date not null,
    que_quan varchar(255) not null,
    sdt varchar(255) not null,
    email varchar(255) not null,
    phong_ban_id int not null,
    chuc_vu_id int not null,
    luong double not null,
    trang_thai varchar(255) not null,
    constraint fk_phong_ban
		foreign key (phong_ban_id) references phong_ban(id),
	constraint fk_chuc_vu
		foreign key (chuc_vu_id) references chuc_vu(id)
);