alter table book
add column isbn varchar(50),
add column numPages int;

update book
set isbn = '',
    numPages = 0;

alter table book
    add constraint isbn_not_null CHECK (isbn is not null),
    add constraint numPages_not_null CHECK (numPages is not null);
