create table jpa_context_application (
  id  BIGINT generated by default as identity,
  context varchar(255) not null,
  hashed_token varchar(255) not null,
  name varchar(255) not null,
  token_prefix varchar(255)
);

create table jpa_context_application_roles (
  jpa_context_application_id BIGINT references jpa_context_application(id),
  roles varchar(255) not null,
  primary key (jpa_context_application_id,roles)
);

