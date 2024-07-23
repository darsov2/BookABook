create table app_user
(
    id bigserial primary key,
    address  varchar(255),
    email  varchar(255),
    name   varchar(255),
    number varchar(255),
    password varchar(255),
    img_user varchar(255),
    constraint emailCheck check ( email like '%@%.%' )
);

create table author
(
    id bigserial primary key,
    last_name varchar(255),
    name varchar(255)
);

create table category
(
    id bigserial primary key,
    name varchar(255)
);

create table customer
(
    id bigint not null primary key
        constraint pk_customer_to_user references app_user
);

create table librarian
(
    id bigint not null primary key
        constraint pk_librarian_to_user references app_user
);

create table library
(
    id bigserial primary key,
    name   varchar(255),
    number varchar(255)
);

create table library_store
(
    id bigserial primary key,
    library_id bigint
        constraint fk_library_store_library
            references library,
    address varchar(255),
    name varchar(255),
    img_url varchar(255)

);

create table publishing_house
(
    id bigserial primary key,
    address varchar(255),
    name    varchar(255)
);

create table book
(
    id bigserial primary key,
    published_year date,
    publishing_house_id bigint
        constraint fk_book_publishing_house references publishing_house,
    name varchar(255),
    description varchar(255),
    img_url varchar(255)
);

create table belongs
(
    book_id bigint not null
        constraint fk_belongs_book references book,
    category_id bigint not null
        constraint fk_belongs_category references category
);

create table book_has
(
    author_id bigint not null
        constraint fk_book_has_author references author,
    book_id bigint not null
        constraint fk_book_has_book references book
);

create table book_in_library
(
    id bigserial primary key,
    condition_book varchar(255),
    book_id bigint
        constraint fk_book_in_library_book references book,
    library_store_id bigint
        constraint fk_book_in_library_library_store references library_store
);

create table borrow_book
(
    id bigserial primary key,
    date_from date,
    date_to date,
    book_in_library_id bigint
        constraint fk_borrow_book_book_in_library references book_in_library,
    customer_id bigint
        constraint fk_borrow_book_customer references customer,
    librarian_id bigint
        constraint fk_borrow_book_librarian references librarian
);

create table customer_book
(
    id bigserial primary key,
    book_id bigint
        constraint fk_customer_book_book references book,
    customer_id bigint
        constraint fk_customer_book_customer references customer
);


create table reserve_book
(
    id bigserial primary key,
    date_from date,
    date_to date,
    book_in_library_id bigint
        constraint fk_reserve_book_book_in_library references book_in_library,
    customer_id bigint
        constraint fk_reserve_book_customer references customer
);

create table review
(
    id bigserial primary key,
    date_reviewed date,
    rate real not null,
    book_id bigint
        constraint fk_review_book references book,
    customer_id bigint
        constraint fk_review_customer references customer,
    description varchar(255)
);

create table subscription
(
    id bigserial primary key,
    date_subscribed date,
    customer_id bigint
        constraint fk_subscription_customer references customer,
    library_id bigint
        constraint fk_subscription_library references library
);

create table trade
(
    id bigserial primary key,
    customer_book_id bigint
        constraint fk_trade_customer_book references customer_book,
    customer_id bigint
        constraint fk_trade_customer references customer
);

create table wish_list
(
    id bigserial primary key,
    date_added  date,
    customer_id bigint unique
        constraint fk_wish_list_customer references customer
);

create table in_wish_list
(
    book_id bigint not null
        constraint fk_in_wish_list_book references book,
    wish_list_id bigint not null
        constraint fk_in_wish_list_wish_list references wish_list
);


