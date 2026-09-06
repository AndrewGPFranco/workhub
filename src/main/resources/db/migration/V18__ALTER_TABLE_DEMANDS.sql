alter table demands drop column sprint;

alter table demands add column sprint_id uuid;

alter table demands add constraint fk_demands_sprint foreign key (sprint_id) references sprints (id);