update budget
set type = 'Расход'
where type = 'Комиссия'

alter table budget
add column if not exists
author_id integer references author(id);