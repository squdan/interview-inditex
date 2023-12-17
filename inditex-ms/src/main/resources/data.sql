-- ############# Users Initialization ############# --
-- Password 'test' hashed with secret 'securityTest': $2a$10$1iHDk1ojpuD4XJrWYVkHou/1WgqODcPHatjd/A0K74azNgrY7VS0K
---- This sql script executes the insert without execute @Convert(converter = PasswordDatabaseConverter.class)
---- configured in UserEntity, so we have to insert already hashed password

-- [ADMIN USER]
INSERT INTO users (id, username, password, role, name, last_name, email, phone_number, created_on, last_updated_on)
VALUES (
    '24930b8e-ff6e-476a-8758-4bd3ff5ebd6b',
    'admin',
    '$2a$10$1iHDk1ojpuD4XJrWYVkHou/1WgqODcPHatjd/A0K74azNgrY7VS0K',
    'ADMIN',
    'Daniel',
    'Torres Rojano',
    'test.admin@gmail.com',
    '+3466777888',
    now(),
    now()
);

-- [NORMAL USER]
INSERT INTO users (id, username, password, role, name, last_name, email, phone_number, created_on, last_updated_on)
VALUES (
    '26ad7565-ba11-4914-bf91-84557b8b8764',
    'user',
    '$2a$10$1iHDk1ojpuD4XJrWYVkHou/1WgqODcPHatjd/A0K74azNgrY7VS0K',
    'USER',
    'Daniel',
    'Torres Rojano',
    'test.user@gmail.com',
    '+34664888999',
    now(),
    now()
);

-- ############# Domain Data Initialization ############# --
-- [BRAND: ZARA]
INSERT INTO brands (id, name, created_on, last_updated_on)
VALUES (
    1,
    'ZARA',
    now(),
    now()
);

ALTER SEQUENCE BRAND_SEQUENCE_ID RESTART WITH ((SELECT count(*) FROM brands) + 50);

-- [PRODUCT: TShirt]
INSERT INTO products (id, name, created_on, last_updated_on)
VALUES (
    35455,
    'Camiseta manga corta Iron Maiden',
    now(),
    now()
);

-- [PRICES: ZARA-TShirt]
INSERT INTO prices (price_list, brand_id, product_id, start_date, end_date, priority, price, currency, created_on, last_updated_on)
VALUES (
    1,
    1,
    35455,
    PARSEDATETIME('2020-06-14 00:00:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    PARSEDATETIME('2020-12-31 23:59:59Z', 'yyyy-MM-dd HH:mm:ssX'),
    0,
    35.50,
    'EUR',
    now(),
    now()
);

INSERT INTO prices (price_list, brand_id, product_id, start_date, end_date, priority, price, currency, created_on, last_updated_on)
VALUES (
    2,
    1,
    35455,
    PARSEDATETIME('2020-06-14 15:00:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    PARSEDATETIME('2020-06-14 18:30:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    1,
    25.45,
    'EUR',
    now(),
    now()
);

INSERT INTO prices (price_list, brand_id, product_id, start_date, end_date, priority, price, currency, created_on, last_updated_on)
VALUES (
    3,
    1,
    35455,
    PARSEDATETIME('2020-06-15 00:00:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    PARSEDATETIME('2020-06-15 11:00:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    1,
    30.50,
    'EUR',
    now(),
    now()
);

INSERT INTO prices (price_list, brand_id, product_id, start_date, end_date, priority, price, currency, created_on, last_updated_on)
VALUES (
    4,
    1,
    35455,
    PARSEDATETIME('2020-06-15 16:00:00Z', 'yyyy-MM-dd HH:mm:ssX'),
    PARSEDATETIME('2020-12-31 23:59:59Z', 'yyyy-MM-dd HH:mm:ssX'),
    1,
    38.95,
    'EUR',
    now(),
    now()
);

ALTER SEQUENCE PRICE_SEQUENCE_ID RESTART WITH ((SELECT count(*) FROM prices) + 50);