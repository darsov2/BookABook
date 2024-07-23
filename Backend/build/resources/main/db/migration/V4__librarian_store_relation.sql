alter table librarian
    add column library_store_id bigint references library_store(id);
