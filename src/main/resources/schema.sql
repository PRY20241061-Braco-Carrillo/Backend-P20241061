-- Table: minim_product_price
CREATE TABLE minim_product_price (
    minim_product_price_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_variant_id uuid  NOT NULL,
    campus_category_id uuid  NOT NULL,
    CONSTRAINT minim_product_price_pk PRIMARY KEY (minim_product_price_id)
);

-- Table: restaurant
CREATE TABLE restaurant (
    restaurant_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    image_url varchar(255)  NULL,
    logo_url varchar(255)  NULL,
    is_available boolean  NOT NULL,
    CONSTRAINT restaurant_pk PRIMARY KEY (restaurant_id)
);

-- Table: campus
CREATE TABLE campus (
    campus_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    address varchar(255)  NOT NULL,
    phone_number varchar(255)  NOT NULL,
    open_hour text  NOT NULL,
    to_take_home boolean  NOT NULL,
    to_delivery boolean  NOT NULL,
    restaurant_id uuid  NOT NULL,
    regex_table_code varchar(255)  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT campus_pk PRIMARY KEY (campus_id),
    CONSTRAINT campus_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (restaurant_id)
);

-- Table: category
CREATE TABLE category (
    category_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    url_image varchar(255),
    restaurant_id uuid  NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (category_id),
    CONSTRAINT category_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (restaurant_id)
);

-- Table: campus_category
CREATE TABLE campus_category (
    campus_category_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    category_id uuid  NOT NULL ,
    campus_id uuid  NOT NULL,
    CONSTRAINT campus_category_pk PRIMARY KEY (campus_category_id),
    CONSTRAINT campus_category_category FOREIGN KEY (category_id) REFERENCES category (category_id),
    CONSTRAINT campus_category_campus FOREIGN KEY (campus_id) REFERENCES campus (campus_id)
);

-- Table: nutritional_information
CREATE TABLE nutritional_information (
    nutritional_information_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    calories decimal(12,2)  NOT NULL,
    proteins decimal(12,2)  NOT NULL,
    total_fat decimal(12,2)  NOT NULL,
    carbohydrates decimal(12,2)  NOT NULL,
    is_vegan bool  NOT NULL,
    is_vegetarian bool  NOT NULL,
    is_low_calories bool  NOT NULL,
    is_high_protein bool  NOT NULL,
    is_without_gluten bool  NOT NULL,
    is_without_nut bool  NOT NULL,
    is_without_lactose bool  NOT NULL,
    is_without_eggs bool  NOT NULL,
    is_without_seafood bool  NOT NULL,
    is_without_pig bool  NOT NULL,
    CONSTRAINT nutritional_information_pk PRIMARY KEY (nutritional_information_id)
);

-- Table: product
CREATE TABLE product (
    product_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    min_cooking_time int  NOT NULL,
    max_cooking_time int  NOT NULL,
    unit_of_time_cooking_time varchar(25)  NOT NULL,
    description text  NOT NULL,
    is_breakfast boolean  NOT NULL,
    is_lunch boolean  NOT NULL,
    is_dinner boolean  NOT NULL,
    url_image varchar(255),
    url_glb varchar(255),
    free_sauce integer  NOT NULL,
    is_available bool  NOT NULL,
    has_variant bool  NOT NULL,
    nutritional_information_id uuid  NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (product_id),
    CONSTRAINT product_nutritional_information FOREIGN KEY (nutritional_information_id) REFERENCES nutritional_information (nutritional_information_id)
);

-- Table: "variant_type"
CREATE TABLE variant_type (
    variant_type_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    variant_type_name varchar(255)  NOT NULL,
    name varchar(255)  NOT NULL,
    CONSTRAINT variant_type_pk PRIMARY KEY (variant_type_id)
);

-- Table: product_variant
CREATE TABLE product_variant (
    product_variant_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    detail text  NOT NULL,
    amount_price decimal(12,2)  NOT NULL,
    currency_price varchar(25)  NOT NULL,
    is_available bool  NOT NULL,
    campus_category_id uuid  NOT NULL,
    product_id uuid  NOT NULL,
    CONSTRAINT product_variant_pk PRIMARY KEY (product_variant_id),
    CONSTRAINT product_variant_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_variant_campus_category FOREIGN KEY (campus_category_id) REFERENCES campus_category (campus_category_id)
);

-- Table: product_variant_type
CREATE TABLE product_variant_type (
    product_variant_type_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_variant_id uuid  NOT NULL,
    variant_type_id uuid  NOT NULL,
    CONSTRAINT product_variant_type_pk PRIMARY KEY (product_variant_type_id),
    CONSTRAINT product_variant_product_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant (product_variant_id),
    CONSTRAINT product_variant_variant_type FOREIGN KEY (variant_type_id) REFERENCES variant_type (variant_type_id)
);

-- Table: menu
CREATE TABLE menu (
    menu_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    amount_price decimal(12,2)  NOT NULL,
    currency_price varchar(25)  NOT NULL,
    min_cooking_time int  NOT NULL,
    max_cooking_time int  NOT NULL,
    unit_of_time_cooking_time varchar(25)  NOT NULL,
    url_image varchar(255),
    is_available bool  NOT NULL,
    campus_id uuid  NOT NULL,
    CONSTRAINT menu_pk PRIMARY KEY (menu_id),
    CONSTRAINT menu_campus FOREIGN KEY (campus_id) REFERENCES campus (campus_id)
);

-- Table: product_menu
CREATE TABLE product_menu (
    product_menu_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    is_principal_dish bool NOT NULL,
    is_initial_dish bool NOT NULL,
    is_dessert bool NOT NULL,
    is_drink bool  NOT NULL,
    is_available bool  NOT NULL,
    product_id uuid  NOT NULL,
    menu_id uuid NOT NULL,
    CONSTRAINT product_menu_pk PRIMARY KEY (product_menu_id),
    CONSTRAINT product_menu_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_menu_menu FOREIGN KEY (menu_id) REFERENCES menu (menu_id)
);

-- Table: combo
CREATE TABLE combo (
    combo_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL,
    amount_price decimal(12,2) NOT NULL,
    free_sauce integer  NOT NULL,
    currency_price varchar(25) NOT NULL,
    complement_amount int  NOT NULL,
    CONSTRAINT combo_pk PRIMARY KEY (combo_id)
);

-- Table: combo_product
CREATE TABLE combo_product (
    combo_product_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_amount int NOT NULL,
    product_id uuid NOT NULL,
    combo_id uuid NOT NULL,
    CONSTRAINT combo_product_pk PRIMARY KEY (combo_product_id)
);

-- Table: combo_product_variant
CREATE TABLE combo_product_variant (
    combo_product_variant_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    combo_product_id uuid NOT NULL,
    product_variant_id uuid NOT NULL,
    CONSTRAINT combo_product_variant_pk PRIMARY KEY (combo_product_variant_id),
    CONSTRAINT combo_product_variant_combo_product FOREIGN KEY (combo_product_id) REFERENCES combo_product (combo_product_id),
    CONSTRAINT combo_product_variant_product_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant (product_variant_id)
);

-- Table: complement
CREATE TABLE complement (
    complement_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL,
    amount_price decimal(12,2)  NOT NULL,
    currency_price varchar(25)  NOT NULL,
    is_sauce bool NOT NULL,
    is_available bool NOT NULL,
    CONSTRAINT complement_pk PRIMARY KEY (complement_id)
);

-- Table: combo_complement
CREATE TABLE  combo_complement (
    combo_complement_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    complement_id uuid NOT NULL,
    combo_id uuid NOT NULL,
    CONSTRAINT combo_complement_pk PRIMARY KEY (combo_complement_id),
    CONSTRAINT combo_complement_complement FOREIGN KEY (complement_id) REFERENCES complement (complement_id),
    CONSTRAINT combo_complement_combo FOREIGN KEY (combo_id) REFERENCES combo (combo_id)
);

-- Table: product_complement
CREATE TABLE product_complement (
    product_complement_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid NOT NULL,
    complement_id uuid NOT NULL,
    CONSTRAINT product_complement_pk PRIMARY KEY (product_complement_id),
    CONSTRAINT product_complement_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_complement_complement FOREIGN KEY (complement_id) REFERENCES complement (complement_id)
);

-- Table: promotion
CREATE TABLE promotion (
    promotion_id uuid NOT NULL DEFAULT gen_random_uuid(),
    discount decimal(12,2)  NOT NULL,
    discount_type varchar(255)  NOT NULL,
    detail text  NOT NULL,
    url_image varchar(255),
    free_sauce int  NOT NULL,
    free_complements int NOT NULL,
    is_available bool NOT NULL,
    product_variant_id uuid,
    combo_id uuid,
    CONSTRAINT promotion_pk PRIMARY KEY (promotion_id),
    CONSTRAINT promotion_product_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant (product_variant_id),
    CONSTRAINT promotion_combo FOREIGN KEY (combo_id) REFERENCES combo (combo_id)
);

-- Table: complement_promotion
CREATE TABLE complement_promotion (
    complement_promotion_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    complement_id uuid NOT NULL,
    promotion_id uuid NOT NULL,
    CONSTRAINT complement_promotion_p PRIMARY KEY (complement_promotion_id),
    CONSTRAINT complement_promotion_complement FOREIGN KEY (complement_id) REFERENCES complement (complement_id),
    CONSTRAINT complement_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion (promotion_id)
);

-- Table: campus_promotion
CREATE TABLE campus_promotion (
    campus_promotion_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    amount_price decimal(12,2)  NOT NULL,
    currency_price varchar(25)  NOT NULL,
    campus_id uuid NOT NULL,
    promotion_id uuid NOT NULL,
    CONSTRAINT campus_promotion_pk PRIMARY KEY (campus_promotion_id),
    CONSTRAINT campus_promotion_campus FOREIGN KEY (campus_id) REFERENCES campus (campus_id),
    CONSTRAINT campus_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion (promotion_id)
);

-- Table: "user"
CREATE TABLE "user" (
    user_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    names varchar(255)  NOT NULL,
    last_names varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    password varchar(255)  NOT NULL,
    roles varchar(255)  NOT NULL,
    cancel_reservation int,
    accept_reservation int,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);


INSERT INTO restaurant (restaurant_id, name, is_available)
VALUES
    ('1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01', 'Rustica', true);

INSERT INTO campus (campus_id, name, address, phone_number, open_hour, to_take_home, to_delivery, restaurant_id, regex_table_code, is_available)
    VALUES
        ('8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2', 'Campus Central', '123 Main St', '+1234567890', '{"Monday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Tuesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Wednesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Thrusday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Friday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Saturday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"17:00"},"dinner":{"opening":"19:00","closing":"20:00"}},"Sunday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}}}', true, true, '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01', '^[A-Z0-9]{4}$', true),
        ('a08c52b4-fd41-4b56-9cf4-88f217e94a2a', 'Campus Norte', '456 Elm St', '+0987654321', '{"Monday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Tuesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Wednesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Thrusday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Friday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Saturday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"17:00"},"dinner":{"opening":"19:00","closing":"20:00"}},"Sunday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}}}', true, true, '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01', '^[A-Z0-9]{4}$', true),
        ('c0bb3d45-9779-4ebf-8a30-77f0b8ecb7c9', 'Campus Sur', '321 Pine St', '+5544332211', '{"Monday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Tuesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Wednesday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Thrusday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Friday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}},"Saturday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"17:00"},"dinner":{"opening":"19:00","closing":"20:00"}},"Sunday":{"breakfast":{"opening":"12:00","closing":"14:00"},"lunch":{"opening":"12:00","closing":"14:00"},"dinner":{"opening":"19:00","closing":"22:00"}}}', true, true, '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01', '^[A-Z0-9]{4}$', true);

INSERT INTO category (category_id, name, restaurant_id)
VALUES
    ('8b06a6d7-7a7c-4e9c-8da3-2d5fcfe3ccdf', 'Pizzas', '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01'),
    ('a92e13c9-f1cd-47c7-b12d-8eb7b86e1f10', 'Pollo', '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01'),
    ('e329d937-3b8e-40ad-a4fb-3ef7e1216a1f', 'Burgers', '1f8a7d11-7d88-4a68-b98f-2a5a42d8aa01');

INSERT INTO campus_category (campus_category_id, category_id, campus_id)
VALUES
    ('3c39460f-f8c9-4922-8b6b-77e55db4baf3', '8b06a6d7-7a7c-4e9c-8da3-2d5fcfe3ccdf', '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2'),
    ('4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', 'a92e13c9-f1cd-47c7-b12d-8eb7b86e1f10', '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2'),
    ('9e45d48e-9187-4b5d-996e-486eaf9f00a0', 'e329d937-3b8e-40ad-a4fb-3ef7e1216a1f', '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2');

INSERT INTO nutritional_information (nutritional_information_id, calories, proteins, total_fat, carbohydrates, is_vegan, is_vegetarian, is_low_calories, is_high_protein, is_without_gluten, is_without_nut, is_without_lactose, is_without_eggs, is_without_seafood, is_without_pig)
VALUES
    ('85c9e308-27d0-41a3-85c5-76af48c8e441', 500, 20, 25, 50, false, false, false, true, false, true, true, true, true, true),
    ('b5df9dd8-25d1-4ee8-a3f5-1e3e4715b001', 400, 15, 20, 40, true, true, true, false, true, true, false, true, false, true),
    ('de5a77de-1909-4ac9-8c3a-07cf29a30128', 600, 30, 30, 60, false, false, false, true, true, false, false, true, true, true);

INSERT INTO product (product_id, name, min_cooking_time, max_cooking_time, unit_of_time_cooking_time, description, is_breakfast, is_lunch, is_dinner, url_image, url_glb, free_sauce, is_available, has_variant, nutritional_information_id)
VALUES
    ('7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2', 'Pepperoni Pizza', 15, 20, 'MIN', 'Classic pizza topped with pepperoni slices and mozzarella cheese', false, true, true, 'pepperoni_pizza.jpg', 'pepperoni_pizza.glb', 1, true, false, '85c9e308-27d0-41a3-85c5-76af48c8e441'),
    ('86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', 'Pollo a la Horneado', 30, 35, 'MIN', 'A grill peruvian chicken with extra fries potatoes', false, false, true, 'pollo_brasa.jpg', 'pollo_brasa.glb', 2, true, true, 'b5df9dd8-25d1-4ee8-a3f5-1e3e4715b001'),
    ('6a7f852e-537d-4a5f-bb6c-0578cc72bc7d', 'Cheeseburger', 10, 15, 'MIN', 'Juicy beef patty topped with cheddar cheese, lettuce, tomato, and mayo', true, true, true, 'cheeseburger.jpg', 'cheeseburger.glb', 2, true, false, 'de5a77de-1909-4ac9-8c3a-07cf29a30128');

INSERT INTO variant_type (variant_type_id, variant_type_name, name)
VALUES
    ('94d1748a-d6dc-4822-9f2b-d7c2795d0c2b', 'Size', 'Small'),
    ('34fbb9d7-9d33-4b6f-a004-d9b40b2a8c70', 'Size', 'Medium'),
    ('9a632da6-c7dc-4fd4-8918-d6020c847d84', 'Size', 'Large'),
    ('cb13a087-3477-4da5-93f7-c5d92e1bba6e', 'Size', '1/4'),
    ('fd01db11-86b3-4d65-a37a-93eb663695b7', 'Size', '1/8'),
    ('02dc732a-5681-4ba1-af46-8b134c4e1950', 'Size', '1'),
    ('ecf7ff67-5e12-4f14-9cb8-f377e1c9f79c', 'Cooking Type', 'Leña'),
    ('f3b3b3b3-1b3b-4b3b-8b3b-3b3b3b3b3b3b', 'Cooking Type', 'Brasa');

INSERT INTO product_variant (product_variant_id, detail, amount_price, currency_price, is_available, campus_category_id, product_id)
VALUES
    ('5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', '1/4 Leña', 10.99, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('a482c179-12c0-4a58-bdc2-4d4f8eef64cf', '1/8 Leña', 8.99, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('1b2463eb-cd36-41cb-a67d-3e5a475b6ec1', '1 Leña', 12.99, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('d5f8c758-76f4-49e7-a979-7a479926a72a', '1/4 Brasa', 10.00, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('8015ed4a-1d05-4c8e-8481-582d669f2e18', '1/8 Brasa', 8.00, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a9', '1 Brasa', 15.99, 'USD', true, '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b'),
    ('e5682c21-1fbf-4e78-893e-14636e2c3f9a', 'Small', 10.99, 'USD', true, '3c39460f-f8c9-4922-8b6b-77e55db4baf3', '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2'),
    ('b00b545a-7a38-47e0-83b5-f66504cf2d9c', 'Medium', 15.99, 'USD', true, '3c39460f-f8c9-4922-8b6b-77e55db4baf3', '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2'),
    ('4c8d8ab5-d13e-4824-9d7c-d9f7b3a30c13', 'Large', 20.99, 'USD', true, '3c39460f-f8c9-4922-8b6b-77e55db4baf3', '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2'),
    ('fca4c4a1-5f6b-478d-92bb-f3f9184c1e9a', 'Small', 10.99, 'USD', true, '9e45d48e-9187-4b5d-996e-486eaf9f00a0', '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d'),
    ('892f1d91-3636-46c7-8ff0-cc4a6c3c4ea5', 'Medium', 15.99, 'USD', true, '9e45d48e-9187-4b5d-996e-486eaf9f00a0', '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d');

INSERT INTO product_variant_type (product_variant_type_id, product_variant_id, variant_type_id)
VALUES
    ('f4265c6b-86e9-4c82-92ec-96bc5ec52b68', '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', 'cb13a087-3477-4da5-93f7-c5d92e1bba6e'),
    ('865ac6e9-aa2c-4ef2-ba86-5a2e8f3616f0', '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', 'ecf7ff67-5e12-4f14-9cb8-f377e1c9f79c'),
    ('0f3e70a6-80cc-4823-8a53-5ab6a3de4813', 'a482c179-12c0-4a58-bdc2-4d4f8eef64cf', 'fd01db11-86b3-4d65-a37a-93eb663695b7'),
    ('651110d9-ba32-49e1-b920-4b78b1a5e7e3', 'a482c179-12c0-4a58-bdc2-4d4f8eef64cf', 'ecf7ff67-5e12-4f14-9cb8-f377e1c9f79c'),
    ('e6a5266d-d4eb-4ee8-8f5b-d3eac1390a28', '1b2463eb-cd36-41cb-a67d-3e5a475b6ec1', '02dc732a-5681-4ba1-af46-8b134c4e1950'),
    ('3b241101-e2bb-4255-8caf-4136c566a964', '1b2463eb-cd36-41cb-a67d-3e5a475b6ec1', 'ecf7ff67-5e12-4f14-9cb8-f377e1c9f79c'),
    ('6ecd8c99-4036-403d-bf84-cf8400f67836', 'd5f8c758-76f4-49e7-a979-7a479926a72a', 'cb13a087-3477-4da5-93f7-c5d92e1bba6e'),
    ('3f333df6-90a4-4fda-8dd3-9485d27cee8a', 'd5f8c758-76f4-49e7-a979-7a479926a72a', 'f3b3b3b3-1b3b-4b3b-8b3b-3b3b3b3b3b3b'),
    ('7e57d004-2b97-0e7a-b45f-5387367791cd', '8015ed4a-1d05-4c8e-8481-582d669f2e18', 'fd01db11-86b3-4d65-a37a-93eb663695b7'),
    ('5c60ef1f-3a7d-4bdb-9c2c-36bd1f68a5a1', '8015ed4a-1d05-4c8e-8481-582d669f2e18', 'f3b3b3b3-1b3b-4b3b-8b3b-3b3b3b3b3b3b'),
    ('4a7c7f58-4c5a-4c8f-9746-ee4f0fe53820', '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', '02dc732a-5681-4ba1-af46-8b134c4e1950'),
    ('6fa459ea-ee8a-3ca4-894e-db77e160355e', '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', 'f3b3b3b3-1b3b-4b3b-8b3b-3b3b3b3b3b3b'),
    ('cd9fca4a-6a77-47d3-b46b-5b0e92f1e5d5', 'e5682c21-1fbf-4e78-893e-14636e2c3f9a', '94d1748a-d6dc-4822-9f2b-d7c2795d0c2b'),
    ('e6f67460-7d76-45d5-97de-62bb1e231c94', 'b00b545a-7a38-47e0-83b5-f66504cf2d9c', '34fbb9d7-9d33-4b6f-a004-d9b40b2a8c70'),
    ('ab142c3d-29e7-459d-916f-6d0c0cfbe20a', '4c8d8ab5-d13e-4824-9d7c-d9f7b3a30c13', '9a632da6-c7dc-4fd4-8918-d6020c847d84'),
    ('8c3cf22e-f33b-4989-bb5c-6cf0a5c25ab9', 'fca4c4a1-5f6b-478d-92bb-f3f9184c1e9a', '94d1748a-d6dc-4822-9f2b-d7c2795d0c2b'),
    ('28c7b71e-15d1-49e5-a91a-5d2aa0e94f80', '892f1d91-3636-46c7-8ff0-cc4a6c3c4ea5', '34fbb9d7-9d33-4b6f-a004-d9b40b2a8c70');


INSERT INTO minim_product_price (minim_product_price_id, product_variant_id, campus_category_id)
VALUES
    ('d4dbbb9b-6b90-4b5c-88e1-34a0abdf6371', '8015ed4a-1d05-4c8e-8481-582d669f2e18', '4c1ef9d9-bded-4e0e-83c2-09c5bbed62f0'),
    ('eeb1b7cd-19da-4b1b-af79-6a7e30127dc8', 'e5682c21-1fbf-4e78-893e-14636e2c3f9a', '3c39460f-f8c9-4922-8b6b-77e55db4baf3'),
    ('b1cb4606-4e70-49d7-9812-34491d21d488', 'fca4c4a1-5f6b-478d-92bb-f3f9184c1e9a', '9e45d48e-9187-4b5d-996e-486eaf9f00a0');



INSERT INTO menu (menu_id, name, amount_price, currency_price, min_cooking_time, max_cooking_time, unit_of_time_cooking_time, url_image, is_available, campus_id)
VALUES
    ('2a0c2f9a-187a-4f10-8268-bf3c7017f79a', 'Menu Economico', 10.00, 'PEN', 15, 20, 'MIN', 'economico.jpg', true, '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2'),
    ('5d1e125a-3a12-4c3a-8610-365bb4f11895', 'Menu Ejecutivo', 15.00, 'PEN', 20, 25, 'MIN', 'ejecutivo.jpg', true, '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2');


INSERT INTO product_menu (product_menu_id, is_principal_dish, is_initial_dish, is_dessert, is_drink, is_available, product_id, menu_id)
VALUES
    ('8f98373a-cb7d-43ad-99ec-30f5baf3db7a', false, false, false, false, true, '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2', '2a0c2f9a-187a-4f10-8268-bf3c7017f79a'),
    ('1fc5157c-f2d0-4e1e-9f47-97a0aeed19a7', true, true, false, false, true, '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', '2a0c2f9a-187a-4f10-8268-bf3c7017f79a'),
    ('ab1f987a-08f5-4f92-ae14-7f6a5f291eb1', false, true, false, false, true, '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d', '5d1e125a-3a12-4c3a-8610-365bb4f11895'),
    ('1aefc067-5f57-482d-aa5f-0490abbe41e9', true, false, false, false, true, '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2', '5d1e125a-3a12-4c3a-8610-365bb4f11895');


INSERT INTO combo (combo_id, name, amount_price, currency_price, free_sauce, complement_amount)
VALUES
    ('2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39', 'Combo Mega Pollo Burguer', 25.99, 'PEN', 2, 2);


INSERT INTO combo_product (combo_product_id, product_amount, product_id, combo_id)
VALUES
    ('69cf4a78-12a3-4d1d-a4f4-6ed9e51c8aa6', 1, '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39'),
    ('7b0e92f4-90e4-4a8d-b2a9-91e4b7babb2a', 2, '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d', '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39');


INSERT INTO combo_product_variant (combo_product_variant_id, combo_product_id, product_variant_id)
VALUES
    ('b05d83f4-f6a6-4484-8fd9-dfcf7b084f6d', '69cf4a78-12a3-4d1d-a4f4-6ed9e51c8aa6', 'd5f8c758-76f4-49e7-a979-7a479926a72a'),
    ('92b24c5b-7a11-42b6-842e-68a428c7318d', '69cf4a78-12a3-4d1d-a4f4-6ed9e51c8aa6', '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7'),
    ('0b16248c-029d-4a27-b8b0-c8f6d5d847e5', '7b0e92f4-90e4-4a8d-b2a9-91e4b7babb2a', '892f1d91-3636-46c7-8ff0-cc4a6c3c4ea5');

INSERT INTO complement (complement_id, name, amount_price, currency_price, is_sauce, is_available)
VALUES
    ('22ed25d2-6178-49a2-8a82-3908c38e8c5a', 'Mayonesa', 0.2, 'PEN', true, true),
    ('c8b7f6cb-4b78-4973-9d6f-4741d0072cb9', 'Mostaza', 0.2, 'PEN', true, true),
    ('20f56f6a-9c4f-4455-843b-5b29acccff77', 'Salsa de Ajo', 0.4, 'PEN', true, true),
    ('f5b7f6cb-4b78-4973-9d6f-4741d0072cb9', 'Porcion de Papas ', 7, 'PEN', false, true);

INSERT INTO product_complement (product_complement_id, product_id, complement_id)
VALUES
    ('3a7ec1f4-3b9d-41d3-8f80-d6f4ef54c800', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', '22ed25d2-6178-49a2-8a82-3908c38e8c5a'),
    ('e99e1ef7-2ef5-4e57-8270-cda08527fb1d', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', 'c8b7f6cb-4b78-4973-9d6f-4741d0072cb9'),
    ('d2a41d7e-9d3b-47b4-a24c-f5a88d45ad55', '86a1e63a-95a3-4540-95c8-dfe7b3b48a4b', 'f5b7f6cb-4b78-4973-9d6f-4741d0072cb9'),
    ('f2a41d7e-9d3b-47b4-a24c-f5a88d45ad55', '7c4a43f1-1077-4cb2-b6e1-8f2b100c1cf2', '20f56f6a-9c4f-4455-843b-5b29acccff77'),
    ('3b241101-e2bb-4255-8caf-4136c566a964', '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d', '22ed25d2-6178-49a2-8a82-3908c38e8c5a'),
    ('6ecd8c99-4036-403d-bf84-cf8400f67836', '6a7f852e-537d-4a5f-bb6c-0578cc72bc7d', 'c8b7f6cb-4b78-4973-9d6f-4741d0072cb9');

INSERT INTO combo_complement (combo_complement_id, complement_id, combo_id)
VALUES
    ('66f6f95b-63dc-4ed3-9648-03a623e4bda4', '22ed25d2-6178-49a2-8a82-3908c38e8c5a', '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39'),
    ('6db9f5e0-7ee2-4ab3-a409-3a3dbb4bea38', 'c8b7f6cb-4b78-4973-9d6f-4741d0072cb9', '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39'),
    ('e3d78b94-66a0-4c1c-9030-67133e75ae3a', 'f5b7f6cb-4b78-4973-9d6f-4741d0072cb9', '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39');


INSERT INTO promotion (promotion_id, discount, discount_type, detail, free_sauce, free_complements, is_available, product_variant_id, combo_id)
VALUES
    ('2b883221-2e51-47d7-a047-8b15b6b30fa7', 10, 'percentage', '10% de descuento en el producto', 1, 0, true, '5c63e9e9-ebc6-4e15-b77d-5a0fb2d8a1a7', NULL),
    ('b9f19657-67ab-48ef-90a7-6fd0f0bf6b38', 5, 'currency', 'Descuento de $5 en el combo', 2, 2, true, NULL, '2f5d3d62-12d9-45c3-8c6f-4f415e1b3d39');

INSERT INTO complement_promotion (complement_promotion_id, complement_id, promotion_id)
VALUES
    ('b9a3e5c7-28a3-4728-9ec3-c994077ba053', '22ed25d2-6178-49a2-8a82-3908c38e8c5a', 'b9f19657-67ab-48ef-90a7-6fd0f0bf6b38'),
    ('f0a846fc-274f-44f7-9e27-32772c7b83fc', 'c8b7f6cb-4b78-4973-9d6f-4741d0072cb9', 'b9f19657-67ab-48ef-90a7-6fd0f0bf6b38');

INSERT INTO campus_promotion (campus_promotion_id, amount_price, currency_price, campus_id, promotion_id)
VALUES
    ('c2800a4a-03b3-4a38-8168-fdf6b6b9dc38', 50.00, 'USD', '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2', '2b883221-2e51-47d7-a047-8b15b6b30fa7'),
    ('1f74fbbf-58fb-46ed-8dab-86c3f97d356f', 30.00, 'USD', '8c81aabb-dc05-4cf1-b9b3-1e3d3fd64ee2', 'b9f19657-67ab-48ef-90a7-6fd0f0bf6b38');



