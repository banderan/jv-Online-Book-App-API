INSERT INTO users (id, email, password, first_name, last_name, shipping_address, is_deleted)
VALUES (2, 'okok@email.com', '$2a$10$EhBFr.PagMjT0P0EYqRL/.KjPUA2vRSutGZo92Xr9Hh/JwwAJq/vi'
       ,'First Name', 'Last Name', 'address', false);

INSERT INTO user_roles (user_id, role_id)
VALUES (2, 1);

INSERT INTO shopping_carts (id, user_id, is_deleted)
VALUES (2, 2, false);
