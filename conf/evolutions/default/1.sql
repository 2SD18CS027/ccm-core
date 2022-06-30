# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table administrator (
  id                            bigint auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  token                         varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_administrator primary key (id)
);

create table cart_items (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  item_id                       bigint,
  quantity                      integer,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_cart_items primary key (id)
);

create table delivery_associate (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  token                         varchar(255),
  mobile_number                 varchar(255),
  password                      varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_delivery_associate primary key (id)
);

create table items (
  id                            bigint auto_increment not null,
  item_id                       varchar(255),
  name                          varchar(255),
  category                      varchar(255),
  price                         double,
  image_url                     varchar(255),
  uom                           varchar(255),
  is_available                  tinyint(1) default 0,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_items primary key (id)
);

create table order_items (
  id                            bigint auto_increment not null,
  order_id                      bigint,
  item_id                       bigint,
  price                         double,
  quantity                      integer,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_order_items primary key (id)
);

create table order_reviews (
  id                            bigint auto_increment not null,
  order_id                      bigint,
  user_id                       bigint,
  rating                        integer,
  comment                       varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_order_reviews primary key (id)
);

create table orders (
  id                            bigint auto_increment not null,
  order_id                      varchar(255),
  user_id                       bigint,
  area_id                       bigint,
  address                       varchar(255),
  status                        integer,
  promocode                     varchar(255),
  from_slot_time                datetime(6),
  to_slot_time                  datetime(6),
  payment_id                    varchar(255),
  delivery_fee                  integer,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_orders primary key (id)
);

create table promocodes (
  id                            bigint auto_increment not null,
  promocode                     varchar(255),
  from_time                     datetime(6),
  to_time                       datetime(6),
  is_percentage                 tinyint(1) default 0,
  value                         double,
  reuse_count                   integer,
  max_value                     double,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_promocodes primary key (id)
);

create table promocodes_usage (
  id                            bigint auto_increment not null,
  promocode_id                  bigint,
  user_id                       bigint,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_promocodes_usage primary key (id)
);

create table serviceable_areas (
  id                            bigint auto_increment not null,
  city                          varchar(255),
  area                          varchar(255),
  area_id                       varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_serviceable_areas primary key (id)
);

create table user_addresses (
  id                            bigint auto_increment not null,
  address_id                    varchar(255),
  user_id                       bigint,
  address                       varchar(255),
  area_id                       bigint,
  is_active                     tinyint(1) default 0,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_user_addresses primary key (id)
);

create table user_sessions (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  token                         varchar(255),
  device_type_id                integer,
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_user_sessions primary key (id)
);

create table user_temp_sessions (
  id                            bigint auto_increment not null,
  mobile_number                 varchar(255),
  token                         varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_user_temp_sessions primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  user_id                       varchar(255),
  name                          varchar(255),
  mobile_number                 varchar(255),
  email                         varchar(255),
  created_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lut                           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP,
  constraint pk_users primary key (id)
);


# --- !Downs

drop table if exists administrator;

drop table if exists cart_items;

drop table if exists delivery_associate;

drop table if exists items;

drop table if exists order_items;

drop table if exists order_reviews;

drop table if exists orders;

drop table if exists promocodes;

drop table if exists promocodes_usage;

drop table if exists serviceable_areas;

drop table if exists user_addresses;

drop table if exists user_sessions;

drop table if exists user_temp_sessions;

drop table if exists users;

