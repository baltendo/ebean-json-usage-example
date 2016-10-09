create table example_model (
  id                            bigint auto_increment not null,
  miscdata                      clob,
  misctype                      varchar(255),
  constraint pk_example_model primary key (id)
);

