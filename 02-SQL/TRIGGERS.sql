-------TRIGGERS
create table colors (
	id serial primary key,
	color text
);


insert into colors (color) values ('red'),('blue'),('green'),('gray');

select * from colors;

--tg_op = trigger operation, holds information about what is happening
create or replace function no_more_blues()
	returns trigger as $$
		begin
			if(tg_op='insert') then
			--new is the new value
				if(upper(new.color)='BLUE') then
					return null;
				end if;
			end if;
		if(upper(new.color)='BLUE') then
			return old;
		end if;
		return new;
		end
	$$ language plpgsql;

create trigger no_blues_trigger
	before insert or update on colors
	for each row
	execute function no_more_blues();

--create trigger [name]
--[before, after, instead of] [insert, update, delete] on [table]
--for each [row, statement]
--execute [function or procedure] [name]
	
	
select * from colors;

update colors set color = 'blue' where id=1;
insert into colors (color) values ('blue');


drop trigger no_blues_trigger on colors;
	
	
	
	
	