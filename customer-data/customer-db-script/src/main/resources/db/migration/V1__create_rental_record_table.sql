create table if not exists rental_record
(
	id_ int not null auto_increment primary key,
	customer_id int not null,
	car_id  int not null,
	start_date datetime not null,
	end_date datetime not null,
	translate_date datetime not null,
	status int not null
);