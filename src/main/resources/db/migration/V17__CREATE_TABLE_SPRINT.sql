create table if not exists sprints
(
    id           uuid        not null primary key,
    title        varchar(20) not null,
    date_to_use  date        not null,
    user_id      integer     not null,
    subdomain_id uuid        not null
);

alter table sprints
    add constraint uk_sprint_title_subdomain unique (title, subdomain_id);
alter table sprints
    add constraint fk_sprint_user_id foreign key (user_id) references users (id);
alter table sprints
    add constraint fk_sprint_subdomain_id foreign key (subdomain_id) references subdomains (id);