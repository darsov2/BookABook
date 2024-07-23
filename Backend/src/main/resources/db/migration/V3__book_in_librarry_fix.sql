alter table book_in_library
    add column is_lent boolean,
    add column is_reserved boolean;

update book_in_library
set is_lent = false,
    is_reserved = false;

alter table book_in_library
    add constraint lent_not_null CHECK (is_lent is not null),
    add constraint reserved_not_null CHECK (is_reserved is not null);
