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

-- Table: category
CREATE TABLE category (
    category_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    url_image varchar(255),
    CONSTRAINT category_pk PRIMARY KEY (category_id)
);

-- Table: cooking_type
CREATE TABLE cooking_type (
    cooking_type_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT cooking_type_pk PRIMARY KEY (cooking_type_id)
);

-- Table: complement
CREATE TABLE complement (
    complement_id uuid NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    price decimal(12,2)  NOT NULL,
    is_sauce bool  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT complement_pk PRIMARY KEY (complement_id)
);

-- Table: promotion
CREATE TABLE promotion (
    promotion_id uuid NOT NULL DEFAULT gen_random_uuid(),
    price decimal(12,2)  NOT NULL,
    cooking_time varchar(255)  NOT NULL,
    discount decimal(12,2)  NOT NULL,
    discount_type varchar(255)  NOT NULL,
    detail text  NOT NULL,
    free_sauce int  NOT NULL,
    free_complements int  NOT NULL,
    url_image varchar(255),
    CONSTRAINT promotion_pk PRIMARY KEY (promotion_id)
);

-- Table: size
CREATE TABLE size (
    size_id uuid NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT size_pk PRIMARY KEY (size_id)
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

-- Table: restaurant
CREATE TABLE restaurant (
    restaurant_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    image_url varchar(255)  NULL,
    is_available bool  NOT NULL,
    CONSTRAINT restaurant_pk PRIMARY KEY (restaurant_id)
);

-- Table: menu
CREATE TABLE menu (
    menu_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    price decimal(12,2)  NOT NULL,
    cooking_time varchar(255)  NOT NULL,
    url_image varchar(255),
    CONSTRAINT menu_pk PRIMARY KEY (menu_id)
);

-- Table: order_request
CREATE TABLE order_request (
    order_request_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    order_request_date date  NOT NULL,
    confirmation_token int  NOT NULL,
    price decimal(12,2)  NOT NULL,
    CONSTRAINT order_request_pk PRIMARY KEY (order_request_id)
);

-- Table: campus
CREATE TABLE campus (
    campus_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    address varchar(255)  NOT NULL,
    phone_number varchar(255)  NOT NULL,
    open_hour text  NOT NULL, -- Cambiado a jsonb para compatibilidad con PostgreSQL
    to_take_home boolean  NOT NULL, -- Cambiado a boolean para compatibilidad con PostgreSQL
    to_delivery boolean  NOT NULL, -- Cambiado a boolean para compatibilidad con PostgreSQL
    restaurant_id uuid  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT campus_pk PRIMARY KEY (campus_id),
    CONSTRAINT campus_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (restaurant_id)
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

-- Table: product
CREATE TABLE product (
    product_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    name varchar(255)  NOT NULL,
    cooking_time varchar(255)  NOT NULL,
    description text  NOT NULL,
    is_breakfast boolean  NOT NULL, -- Cambiado a boolean para compatibilidad con PostgreSQL
    is_lunch boolean  NOT NULL, -- Cambiado a boolean para compatibilidad con PostgreSQL
    is_dinner boolean  NOT NULL, -- Cambiado a boolean para compatibilidad con PostgreSQL
    url_image varchar(255), -- Cambiado a varchar(255) para compatibilidad con PostgreSQL
    url_glb varchar(255), -- Cambiado a varchar(255) para compatibilidad con PostgreSQL
    free_sauce integer  NOT NULL, -- Cambiado a integer para compatibilidad con PostgreSQL
    nutritional_information_id uuid  NOT NULL,
    category_id uuid  NOT NULL,
    is_available bool  NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (product_id),
    CONSTRAINT product_nutritional_information FOREIGN KEY (nutritional_information_id) REFERENCES nutritional_information (nutritional_information_id),
    CONSTRAINT product_category FOREIGN KEY (category_id) REFERENCES category (category_id)
);

-- Table: complement_promotion
CREATE TABLE complement_promotion (
    complement_promotion_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    complement_id uuid  NOT NULL,
    promotion_id uuid  NOT NULL,
    CONSTRAINT complement_promotion_pk PRIMARY KEY (complement_promotion_id),
    CONSTRAINT complement_promotion_complement FOREIGN KEY (complement_id) REFERENCES complement (complement_id),
    CONSTRAINT complement_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion (promotion_id)
);

-- Table: product_complement
CREATE TABLE product_complement (
    product_complement_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid  NOT NULL,
    complement_id uuid  NOT NULL,
    CONSTRAINT product_complement_pk PRIMARY KEY (product_complement_id),
    CONSTRAINT product_complement_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_complement_complement FOREIGN KEY (complement_id) REFERENCES complement (complement_id)
);

-- Table: product_menu
CREATE TABLE product_menu (
    product_menu_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid  NOT NULL,
    menu_id uuid  NOT NULL,
    CONSTRAINT product_menu_pk PRIMARY KEY (product_menu_id),
    CONSTRAINT product_menu_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_menu_menu FOREIGN KEY (menu_id) REFERENCES menu (menu_id)
);

-- Table: product_promotion
CREATE TABLE product_promotion (
    product_promotion_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid  NOT NULL,
    promotion_id uuid  NOT NULL,
    CONSTRAINT product_promotion_pk PRIMARY KEY (product_promotion_id),
    CONSTRAINT product_promotion_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion (promotion_id)
);

-- Table: product_variant
CREATE TABLE product_variant (
    product_variant_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    price decimal(12,2)  NOT NULL,
    product_id uuid  NOT NULL,
    cooking_type_id uuid  NOT NULL,
    size_id uuid  NOT NULL,
    CONSTRAINT product_variant_pk PRIMARY KEY (product_variant_id),
    CONSTRAINT product_variant_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT product_variant_cooking_type FOREIGN KEY (cooking_type_id) REFERENCES cooking_type (cooking_type_id),
    CONSTRAINT product_variant_size FOREIGN KEY (size_id) REFERENCES size (size_id)
);

-- Table: "order"
CREATE TABLE "order" (
    order_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    order_request_id uuid  NOT NULL,
    order_status varchar(25)  NOT NULL,
    table_number varchar(25)  NOT NULL,
    user_id uuid, -- Cambiado a varchar(255) para compatibilidad con PostgreSQL
    CONSTRAINT order_pk PRIMARY KEY (order_id),
    CONSTRAINT order_request_order FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id),
    CONSTRAINT order_user FOREIGN KEY (user_id) REFERENCES "user" (user_id) -- Cambiado a "user" para compatibilidad con PostgreSQL
);

-- Table: order_complement
CREATE TABLE order_complement (
    order_complement_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_complement_id uuid  NOT NULL,
    order_request_id uuid  NOT NULL,
    CONSTRAINT order_complement_pk PRIMARY KEY (order_complement_id),
    CONSTRAINT order_complement_product_complement FOREIGN KEY (product_complement_id) REFERENCES product_complement (product_complement_id),
    CONSTRAINT order_complement_order_request FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id)
);

-- Table: order_menu
CREATE TABLE order_menu (
    order_menu_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    menu_id uuid  NOT NULL,
    order_request_id uuid  NOT NULL,
    CONSTRAINT order_menu_pk PRIMARY KEY (order_menu_id),
    CONSTRAINT order_menu_menu FOREIGN KEY (menu_id) REFERENCES menu (menu_id),
    CONSTRAINT order_menu_order_request FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id)
);

-- Table: order_product
CREATE TABLE order_product (
    order_product_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid  NOT NULL,
    order_request_id uuid  NOT NULL,
    CONSTRAINT order_product_pk PRIMARY KEY (order_product_id),
    CONSTRAINT order_product_product FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT order_product_order_request FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id)
);

-- Table: order_promotion
CREATE TABLE order_promotion (
    order_promotion_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    promotion_id uuid  NOT NULL,
    order_request_id uuid  NOT NULL,
    CONSTRAINT order_promotion_pk PRIMARY KEY (order_promotion_id),
    CONSTRAINT order_promotion_order_request FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id),
    CONSTRAINT order_promotion_promotion FOREIGN KEY (promotion_id) REFERENCES promotion (promotion_id)
);

-- Table: reservation_request
CREATE TABLE reservation_request (
    reservation_request_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    reservation_request_status varchar(25)  NOT NULL,
    reservation_request_date date  NOT NULL,
    order_request_id uuid  NOT NULL,
    CONSTRAINT reservation_request_pk PRIMARY KEY (reservation_request_id),
    CONSTRAINT reservation_request_order_request FOREIGN KEY (order_request_id) REFERENCES order_request (order_request_id)
);

-- Table: reservation
CREATE TABLE reservation (
    reservation_id uuid  NOT NULL DEFAULT gen_random_uuid(),
    reservation_status varchar(25)  NOT NULL,
    reservation_request_id uuid  NOT NULL,
    user_id uuid  NOT NULL,
    CONSTRAINT reservation_pk PRIMARY KEY (reservation_id),
    CONSTRAINT reservation_reservation_request FOREIGN KEY (reservation_request_id) REFERENCES reservation_request (reservation_request_id),
    CONSTRAINT reservation_user FOREIGN KEY (user_id) REFERENCES "user" (user_id) -- Cambiado a "user" para compatibilidad con PostgreSQL
);


