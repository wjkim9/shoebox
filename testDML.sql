-- INSERT
INSERT INTO Members (
    members_id, account_name, password, nickname, name,
    email, contact, foot_size, join_datetime,
    modified_datetime, point, is_deleted
) VALUES (
             1, 'user1', 'hashedPassword123', '닉네임1', '홍길동',
             'user1@email.com', '010-1234-5678', 265, SYSDATE,
             NULL, 0, 0
         );

-- UPDATE
UPDATE Members
SET nickname = '새닉네임',
    modified_datetime = SYSDATE,
    point = point + 1000
WHERE members_id = 1;

-- DELETE (소프트 삭제)
UPDATE Members
SET is_deleted = 1
WHERE members_id = 1;

-- DELETE (하드 삭제)
DELETE FROM Members WHERE members_id = 1;

-- product
-- INSERT
INSERT INTO Product (
    product_id, product_name, product_price,
    discount_rate, target_customer_type,
    product_register_date, brand_id,
    categories_id, productgroup_id
) VALUES (
             1, '나이키 에어맥스', 129000,
             10, 'UNISEX',
             SYSDATE, 1,
             1, 1
         );

-- UPDATE
UPDATE Product
SET product_price = 139000,
    discount_rate = 15
WHERE product_id = 1;

-- DELETE
DELETE FROM Product WHERE product_id = 1;


-- Brand 데이터 삽입
INSERT INTO Brand (brand_id, brand_name) VALUES (1, 'Nike');
INSERT INTO Brand (brand_id, brand_name) VALUES (2, 'Adidas');
INSERT INTO Brand (brand_id, brand_name) VALUES (3, 'New Balance');
INSERT INTO Brand (brand_id, brand_name) VALUES (4, 'Puma');

-- Categories 데이터 삽입
INSERT INTO Categories (categories_id, categories_name, pic_name) VALUES (1, '러닝화', '23732_1712905511772.jpg');
INSERT INTO Categories (categories_id, categories_name, pic_name) VALUES (2, '농구화', '23732_1712905511772.jpg');
INSERT INTO Categories (categories_id, categories_name, pic_name) VALUES (3, '캐주얼', '23732_1712905511772.jpg');
INSERT INTO Categories (categories_id, categories_name, pic_name) VALUES (4, '축구화', '23732_1712905511772.jpg');

-- ProductGroup 데이터 삽입
INSERT INTO ProductGroup (productgroup_id, product_group_name) VALUES (1, '신상품');
INSERT INTO ProductGroup (productgroup_id, product_group_name) VALUES (2, '베스트셀러');
INSERT INTO ProductGroup (productgroup_id, product_group_name) VALUES (3, '세일상품');
INSERT INTO ProductGroup (productgroup_id, product_group_name) VALUES (4, '한정판');

commit;

INSERT INTO Product (
    product_id, product_name, product_price,
    discount_rate, target_customer_type,
    product_register_date, brand_id,
    categories_id, productgroup_id
) VALUES (
             1, '나이키 에어맥스 2024', 189000,
             0, 'UNISEX',
             SYSDATE, 1, 1, 1
         );

INSERT INTO Product VALUES (
                               2, '아디다스 울트라부스트', 229000,
                               15, 'UNISEX',
                               SYSDATE, 2, 1, 2
                           );

INSERT INTO Product VALUES (
                               3, '뉴발란스 993', 339000,
                               0, 'UNISEX',
                               SYSDATE, 3, 3, 2
                           );

INSERT INTO Product VALUES (
                               4, '나이키 덩크 로우', 139000,
                               10, 'UNISEX',
                               SYSDATE, 1, 3, 2
                           );

INSERT INTO Product VALUES (
                               5, '푸마 슈퍼리가', 129000,
                               20, 'MEN',
                               SYSDATE, 4, 4, 3
                           );

INSERT INTO Product VALUES (
                               6, '나이키 조던 1', 209000,
                               0, 'UNISEX',
                               SYSDATE, 1, 2, 4
                           );

INSERT INTO Product VALUES (
                               7, '아디다스 삼바', 159000,
                               0, 'UNISEX',
                               SYSDATE, 2, 3, 2
                           );

INSERT INTO Product VALUES (
                               8, '뉴발란스 530', 129000,
                               25, 'UNISEX',
                               SYSDATE, 3, 3, 3
                           );

INSERT INTO Product VALUES (
                               9, '나이키 페가수스 40', 159000,
                               10, 'WOMEN',
                               SYSDATE, 1, 1, 1
                           );

INSERT INTO Product VALUES (
                               10, '아디다스 프레데터', 219000,
                               15, 'MEN',
                               SYSDATE, 2, 4, 1
                           );

-- ProductStock 예시 데이터 (각 상품별로 몇 가지 사이즈)
INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (1, '250', 50, 1);
INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (2, '260', 50, 1);
INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (3, '270', 50, 1);

INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (4, '250', 30, 2);
INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (5, '260', 30, 2);
INSERT INTO ProductStock (productstock_id, shoe_size, stock_quantity, product_id)
VALUES (6, '270', 30, 2);


-- Product 1: 나이키 에어맥스 2024 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (1, '23732_1712905511772.jpg', 1, 1);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (2, '23732_1712905511772.jpg', 2, 1);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (3, '23732_1712905511772.jpg', 3, 1);

-- Product 2: 아디다스 울트라부스트 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (4, '23732_1712905511772.jpg', 1, 2);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (5, '23732_1712905511772.jpg', 2, 2);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (6, '23732_1712905511772.jpg', 3, 2);

-- Product 3: 뉴발란스 993 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (7, '23732_1712905511772.jpg', 1, 3);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (8, '23732_1712905511772.jpg', 2, 3);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (9, '23732_1712905511772.jpg', 3, 3);

-- Product 4: 나이키 덩크 로우 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (10, '23732_1712905511772.jpg', 1, 4);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (11, '23732_1712905511772.jpg', 2, 4);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (12, '23732_1712905511772.jpg', 3, 4);

-- Product 5: 푸마 슈퍼리가 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (13, '23732_1712905511772.jpg', 1, 5);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (14, '23732_1712905511772.jpg', 2, 5);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (15, '23732_1712905511772.jpg', 3, 5);

-- Product 6: 나이키 조던 1 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (16, '23732_1712905511772.jpg', 1, 6);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (17, '23732_1712905511772.jpg', 2, 6);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (18, '23732_1712905511772.jpg', 3, 6);

-- Product 7: 아디다스 삼바 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (19, '23732_1712905511772.jpg', 1, 7);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (20, '23732_1712905511772.jpg', 2, 7);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (21, '23732_1712905511772.jpg', 3, 7);

-- Product 8: 뉴발란스 530 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (22, '23732_1712905511772.jpg', 1, 8);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (23, '23732_1712905511772.jpg', 2, 8);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (24, '23732_1712905511772.jpg', 3, 8);

-- Product 9: 나이키 페가수스 40 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (25, '23732_1712905511772.jpg', 1, 9);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (26, '23732_1712905511772.jpg', 2, 9);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (27, '23732_1712905511772.jpg', 3, 9);

-- Product 10: 아디다스 프레데터 이미지
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (28, '23732_1712905511772.jpg', 1, 10);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (29, '23732_1712905511772.jpg', 2, 10);
INSERT INTO ProductImage (productimage_id, file_name, sort_order, product_id)
VALUES (30, '23732_1712905511772.jpg', 3, 10);


commit ;

-- Members 데이터 생성 (IssuedCoupon의 부모 테이블)
INSERT INTO Members (
    members_id, account_name, password, nickname,
    name, email, contact, foot_size,
    join_datetime, modified_datetime, point, is_deleted
) VALUES (
             2, 'user2', 'hashedPW123', '슈즈마스터',
             '김신발', 'shoes1@email.com', '010-1111-2222', 270,
             SYSDATE, NULL, 1000, 0
         );

INSERT INTO Members VALUES (
                               3, 'user3', 'hashedPW456', '운동화킹',
                               '이런닝', 'runner@email.com', '010-2222-3333', 265,
                               SYSDATE, NULL, 2000, 0
                           );

INSERT INTO Members VALUES (
                               4, 'user4', 'hashedPW789', '워커장인',
                               '박부츠', 'boots@email.com', '010-3333-4444', 275,
                               SYSDATE, NULL, 3000, 0
                           );

-- Coupon 데이터 생성
INSERT INTO Coupon (
    coupon_id, coupon_name, discount_rate,
    min_price, max_discount_price
) VALUES (
             1, '신규가입 웰컴 쿠폰', 15,
             50000, 30000
         );

INSERT INTO Coupon VALUES (
                              2, '여름맞이 시즌 쿠폰', 10,
                              100000, 50000
                          );

INSERT INTO Coupon VALUES (
                              3, 'VIP 전용 특별 쿠폰', 20,
                              200000, 100000
                          );

INSERT INTO Coupon VALUES (
                              4, '생일 축하 쿠폰', 25,
                              30000, 20000
                          );

INSERT INTO Coupon VALUES (
                              5, '첫 구매 감사 쿠폰', 30,
                              70000, 40000
                          );

-- IssuedCoupon 데이터 생성
-- 현재 시점 기준으로 발급일과 만료일 설정
INSERT INTO IssuedCoupon (
    issuedcoupon_id, issue_datetime, expire_datetime,
    members_id, coupon_id
) VALUES (
             1,
             SYSDATE,
             SYSDATE + 30,
             1, 1
         );

INSERT INTO IssuedCoupon VALUES (
                                    2,
                                    SYSDATE,
                                    SYSDATE + 30,
                                    1, 2
                                );

INSERT INTO IssuedCoupon VALUES (
                                    3,
                                    SYSDATE,
                                    SYSDATE + 60,
                                    2, 3
                                );

INSERT INTO IssuedCoupon VALUES (
                                    4,
                                    SYSDATE,
                                    SYSDATE + 30,
                                    2, 4
                                );

INSERT INTO IssuedCoupon VALUES (
                                    5,
                                    SYSDATE,
                                    SYSDATE + 90,
                                    3, 5
                                );

INSERT INTO IssuedCoupon VALUES (
                                    6,
                                    SYSDATE,
                                    SYSDATE + 30,
                                    3, 1
                                );

-- 만료된 쿠폰 예시
INSERT INTO IssuedCoupon VALUES (
                                    7,
                                    SYSDATE - 40,
                                    SYSDATE - 10,
                                    1, 3
                                );

-- 곧 만료될 쿠폰 예시
INSERT INTO IssuedCoupon VALUES (
                                    8,
                                    SYSDATE - 25,
                                    SYSDATE + 5,
                                    2, 5
                                );



-- CartItem 데이터 추가
-- CartItem 데이터 추가
INSERT INTO CartItem (
    cartitem_id, quantity,
    productstock_id, members_id
) VALUES (
             cartitem_seq.NEXTVAL, 2,
             1, 1  -- member1이 productstock1을 2개 담음
         );

INSERT INTO CartItem VALUES (
                                cartitem_seq.NEXTVAL, 1,
                                2, 1  -- member1이 productstock2를 1개 담음
                            );

INSERT INTO CartItem VALUES (
                                cartitem_seq.NEXTVAL, 1,
                                3, 2  -- member2가 productstock3을 1개 담음
                            );

INSERT INTO CartItem VALUES (
                                cartitem_seq.NEXTVAL, 2,
                                4, 2  -- member2가 productstock4를 2개 담음
                            );

INSERT INTO CartItem VALUES (
                                cartitem_seq.NEXTVAL, 1,
                                5, 1  -- member1이 productstock5를 1개 담음
                            );

INSERT INTO CartItem VALUES (
                                cartitem_seq.NEXTVAL, 2,
                                6, 2  -- member2가 productstock6을 2개 담음
                            );

commit;



-- MEMBERGRADE 테이블에 데이터 삽입 (회원 ID와 연결)
INSERT INTO MEMBERGRADE (MEMBERGRADE_ID, GRADE_NAME, DISCOUNT_RATE, MEMBERS_ID)
VALUES (1, '일반회원', 1, 1);

INSERT INTO MEMBERGRADE (MEMBERGRADE_ID, GRADE_NAME, DISCOUNT_RATE, MEMBERS_ID)
VALUES (2, '실버회원', 3, 2);

INSERT INTO MEMBERGRADE (MEMBERGRADE_ID, GRADE_NAME, DISCOUNT_RATE, MEMBERS_ID)
VALUES (3, '골드회원', 5, 3);

INSERT INTO MEMBERGRADE (MEMBERGRADE_ID, GRADE_NAME, DISCOUNT_RATE, MEMBERS_ID)
VALUES (4, '플래티넘회원', 7, 4);

-- 추가 회원 등급 (기존 회원과 연결)
INSERT INTO MEMBERGRADE (MEMBERGRADE_ID, GRADE_NAME, DISCOUNT_RATE, MEMBERS_ID)
VALUES (5, 'VIP회원', 10, 1);

-- MEMBERGRADE 데이터 수정 예시
UPDATE MEMBERGRADE
SET DISCOUNT_RATE = 8, MEMBERS_ID = 2
WHERE MEMBERGRADE_ID = 4;

-- 특정 회원의 등급 변경
UPDATE MEMBERGRADE
SET GRADE_NAME = 'VIP회원', DISCOUNT_RATE = 10
WHERE MEMBERS_ID = 3;

-- MEMBERGRADE 데이터

commit;


select * from coupon;
select * from members;
select * from ISSUEDCOUPON;
select * from product;
select * from PRODUCTSTOCK;
select * from CARTITEM;
select * from MEMBERGRADE;


delete from MEMBERGRADE where MEMBERGRADE_ID = 1;

commit;



