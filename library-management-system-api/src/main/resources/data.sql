insert into books
    (author,isbn,publication_year,title,created_at)
values
    ('author-1', 'isbn-1', 2020, 'title-1', current_timestamp),
    ('author-2', 'isbn-2', 2021, 'title-2', current_timestamp),
    ('author-3', 'isbn-3', 2022, 'title-3', current_timestamp),
    ('author-4', 'isbn-4', 2023, 'title-4', current_timestamp),
    ('author-5', 'isbn-5', 2024, 'title-5', current_timestamp);

insert into patrons
    (name,contact_info,created_at)
values
    ('name-1', 'contact-1', current_timestamp),
    ('name-2', 'contact-2', current_timestamp),
    ('name-3', 'contact-3', current_timestamp),
    ('name-4', 'contact-4', current_timestamp),
    ('name-5', 'contact-5', current_timestamp);

insert into borrowing_records
    (borrowing_date,created_at,book_id,patron_id)
values
    (current_timestamp, current_timestamp, 1, 1),
    (current_timestamp, current_timestamp, 2, 1);

UPDATE books set available=false , updated_at=CURRENT_TIMESTAMP where id=1 or id=2; 