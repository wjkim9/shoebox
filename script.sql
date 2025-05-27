DROP TABLE Members CASCADE CONSTRAINTS;
DROP TABLE MemberGrade CASCADE CONSTRAINTS;
DROP TABLE ChatRoom CASCADE CONSTRAINTS;
DROP TABLE ChatMessage CASCADE CONSTRAINTS;
DROP TABLE VisitorCount CASCADE CONSTRAINTS;
DROP TABLE Notice CASCADE CONSTRAINTS;
DROP TABLE MainBanner CASCADE CONSTRAINTS;
DROP TABLE Admins CASCADE CONSTRAINTS;
DROP TABLE PopupNotice CASCADE CONSTRAINTS;
DROP TABLE EventPost CASCADE CONSTRAINTS;
DROP TABLE CustomerService CASCADE CONSTRAINTS;
DROP TABLE Coupon CASCADE CONSTRAINTS;
DROP TABLE IssuedCoupon CASCADE CONSTRAINTS;
DROP TABLE PointTransaction CASCADE CONSTRAINTS;
DROP TABLE MemberAddress CASCADE CONSTRAINTS;
DROP TABLE Brand CASCADE CONSTRAINTS;
DROP TABLE FavoriteBrand CASCADE CONSTRAINTS;
DROP TABLE ProductGroup CASCADE CONSTRAINTS;
DROP TABLE Categories CASCADE CONSTRAINTS;
DROP TABLE Product CASCADE CONSTRAINTS;
DROP TABLE ProductImage CASCADE CONSTRAINTS;
DROP TABLE ProductStock CASCADE CONSTRAINTS;
DROP TABLE CartItem CASCADE CONSTRAINTS;
DROP TABLE ProductPost CASCADE CONSTRAINTS;
DROP TABLE ProductPostImage CASCADE CONSTRAINTS;
DROP TABLE ProductPostQna CASCADE CONSTRAINTS;
DROP TABLE Orders CASCADE CONSTRAINTS;
DROP TABLE ProductStockOrder CASCADE CONSTRAINTS;
DROP TABLE DeliveryProgress CASCADE CONSTRAINTS;
DROP TABLE DeliveryScenario CASCADE CONSTRAINTS;
DROP TABLE OrderReview CASCADE CONSTRAINTS;
DROP TABLE OrderReviewReport CASCADE CONSTRAINTS;
DROP TABLE OrderReviewImage CASCADE CONSTRAINTS;

/* 방문자누적 */
CREATE TABLE VisitorCount (
	visit_date DATE NOT NULL /* 방문일자 */
);

/* 관리자 */
CREATE TABLE Admins (
	admins_id NUMBER NOT NULL, /* 관리자id */
	password VARCHAR2(200) NOT NULL /* 패스워드 */
);

ALTER TABLE Admins
	ADD
		CONSTRAINT PK_Admins
		PRIMARY KEY (
			admins_id
		);


/* 회원 */
CREATE TABLE Members (
	members_id NUMBER NOT NULL, /* 회원id */
	account_name VARCHAR2(100) NOT NULL, /* 계정명 */
	password VARCHAR2(200) NOT NULL, /* 패스워드 */
	nickname VARCHAR2(20) NOT NULL, /* 닉네임 */
	name VARCHAR2(50) NOT NULL, /* 이름 */
	email VARCHAR2(40) NOT NULL, /* 이메일 */
	contact VARCHAR2(30) NOT NULL, /* 연락처 */
	foot_size NUMBER, /* 발사이즈 */
	join_datetime DATE NOT NULL, /* 가입일시 */
	modified_datetime DATE, /* 수정일시 */
	point NUMBER NOT NULL, /* 보유포인트 */
	is_deleted NUMBER NOT NULL /* 탈퇴유무 */
);

ALTER TABLE Members
	ADD
		CONSTRAINT PK_Members
		PRIMARY KEY (
			members_id
		);


/* 회원등급 */
CREATE TABLE MemberGrade (
	membergrade_id NUMBER NOT NULL, /* 회원등급id */
	grade_name VARCHAR2(20) NOT NULL, /* 회원등급명 */
	discount_rate NUMBER NOT NULL, /* 할인율 */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE MemberGrade
	ADD
		CONSTRAINT PK_MemberGrade
		PRIMARY KEY (
			membergrade_id
		);

ALTER TABLE MemberGrade
	ADD
		CONSTRAINT FK_Members_TO_MemberGrade
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 쿠폰 */
CREATE TABLE Coupon (
	coupon_id NUMBER NOT NULL, /* 쿠폰id */
	coupon_name VARCHAR2(150) NOT NULL, /* 쿠폰이름 */
	discount_rate NUMBER NOT NULL, /* 할인율 */
	min_price NUMBER NOT NULL, /* 최소금액 */
	max_discount_price NUMBER /* 최대할인금액 */
);

ALTER TABLE Coupon
	ADD
		CONSTRAINT PK_Coupon
		PRIMARY KEY (
			coupon_id
		);


/* 발급쿠폰 */
CREATE TABLE IssuedCoupon (
	issuedcoupon_id NUMBER NOT NULL, /* 발급쿠폰id */
	issue_datetime DATE NOT NULL, /* 발급일시 */
	expire_datetime DATE NOT NULL, /* 만료일시 */
	members_id NUMBER NOT NULL, /* 회원id */
	coupon_id NUMBER NOT NULL /* 쿠폰id */
);

ALTER TABLE IssuedCoupon
	ADD
		CONSTRAINT PK_IssuedCoupon
		PRIMARY KEY (
			issuedcoupon_id
		);

ALTER TABLE IssuedCoupon
	ADD
		CONSTRAINT FK_Members_TO_IssuedCoupon
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

ALTER TABLE IssuedCoupon
	ADD
		CONSTRAINT FK_Coupon_TO_IssuedCoupon
		FOREIGN KEY (
			coupon_id
		)
		REFERENCES Coupon (
			coupon_id
		);

/* 브랜드 */
CREATE TABLE Brand (
	brand_id NUMBER NOT NULL, /* 브랜드id */
	brand_name VARCHAR2(200) NOT NULL /* 브랜드명 */
);

ALTER TABLE Brand
	ADD
		CONSTRAINT PK_Brand
		PRIMARY KEY (
			brand_id
		);


/* 관심브랜드 */
CREATE TABLE FavoriteBrand (
	favoritebrand_id NUMBER NOT NULL, /* 관심브랜드id */
	brand_id NUMBER NOT NULL, /* 브랜드id */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE FavoriteBrand
	ADD
		CONSTRAINT PK_FavoriteBrand
		PRIMARY KEY (
			favoritebrand_id
		);

ALTER TABLE FavoriteBrand
	ADD
		CONSTRAINT FK_Brand_TO_FavoriteBrand
		FOREIGN KEY (
			brand_id
		)
		REFERENCES Brand (
			brand_id
		);

ALTER TABLE FavoriteBrand
	ADD
		CONSTRAINT FK_Members_TO_FavoriteBrand
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 이벤트게시글 */
CREATE TABLE EventPost (
	eventpost_id NUMBER NOT NULL, /* 이벤트게시글id */
	title VARCHAR2(100) NOT NULL, /* 제목 */
	content CLOB NOT NULL, /* 내용 */
	write_date DATE NOT NULL /* 작성일 */
);

ALTER TABLE EventPost
	ADD
		CONSTRAINT PK_EventPost
		PRIMARY KEY (
			eventpost_id
		);

/* 팝업공지 */
CREATE TABLE PopupNotice (
	popupnotice_id NUMBER NOT NULL, /* 팝업공지id */
	file_name VARCHAR2(200) NOT NULL /* 첨부파일명 */
);

ALTER TABLE PopupNotice
	ADD
		CONSTRAINT PK_PopupNotice
		PRIMARY KEY (
			popupnotice_id
		);


/* 메인배너 */
CREATE TABLE MainBanner (
	mainbanner_id NUMBER NOT NULL, /* 메인배너id */
	file_name VARCHAR2(200) NOT NULL, /* 첨부파일명 */
	sort_order NUMBER NOT NULL /* 배치순서 */
);

ALTER TABLE MainBanner
	ADD
		CONSTRAINT PK_MainBanner
		PRIMARY KEY (
			mainbanner_id
		);

/* 공지사항 */
CREATE TABLE Notice (
	notice_id NUMBER NOT NULL, /* 공지사항id */
	title VARCHAR2(100) NOT NULL, /* 제목 */
	content CLOB NOT NULL, /* 내용 */
	write_date DATE NOT NULL /* 작성일 */
);

ALTER TABLE Notice
	ADD
		CONSTRAINT PK_Notice
		PRIMARY KEY (
			notice_id
		);

/* 고객센터문의 */
CREATE TABLE CustomerService (
	customerservice_id NUMBER NOT NULL, /* 고객센터문의id */
	title VARCHAR2(100) NOT NULL, /* 제목 */
	content CLOB NOT NULL, /* 내용 */
	write_date DATE NOT NULL, /* 작성일 */
	answer_content CLOB, /* 답변내용 */
	answer_date DATE, /* 답변작성일 */
	category VARCHAR2(100) NOT NULL, /* 카테고리 */
	members_id NUMBER NOT NULL, /* 회원id */
	parents_customerservice_id NUMBER NOT NULL /* 부모고객센터문의id */
);

ALTER TABLE CustomerService
	ADD
		CONSTRAINT PK_CustomerService
		PRIMARY KEY (
			customerservice_id
		);

ALTER TABLE CustomerService
	ADD
		CONSTRAINT FK_Members_TO_CustomerService
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

ALTER TABLE CustomerService
	ADD
		CONSTRAINT FK_CstmrSrvc_TO_CstmrSrvc
		FOREIGN KEY (
			parents_customerservice_id
		)
		REFERENCES CustomerService (
			customerservice_id
		);



/* 상담채팅방 */
CREATE TABLE ChatRoom (
	chatroom_id NUMBER NOT NULL, /* 상담채팅방id */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE ChatRoom
	ADD
		CONSTRAINT PK_ChatRoom
		PRIMARY KEY (
			chatroom_id
		);

ALTER TABLE ChatRoom
	ADD
		CONSTRAINT FK_Members_TO_ChatRoom
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 채팅내용 */
CREATE TABLE ChatMessage (
	chatmessage_id NUMBER NOT NULL, /* 채팅내용id */
	content CLOB NOT NULL, /* 내용 */
	writer_classify NUMBER NOT NULL, /* 작성자구분 */
	send_datetime DATE NOT NULL, /* 발신일시 */
	chatroom_id NUMBER NOT NULL /* 상담채팅방id */
);

ALTER TABLE ChatMessage
	ADD
		CONSTRAINT PK_ChatMessage
		PRIMARY KEY (
			chatmessage_id
		);

ALTER TABLE ChatMessage
	ADD
		CONSTRAINT FK_ChatRoom_TO_ChatMessage
		FOREIGN KEY (
			chatroom_id
		)
		REFERENCES ChatRoom (
			chatroom_id
		);

/* 카테고리 */
CREATE TABLE Categories (
	categories_id NUMBER NOT NULL, /* 카테고리id */
	categories_name VARCHAR2(100) NOT NULL, /* 카테고리명 */
	pic_name VARCHAR2(200) /* 사진파일명 */
);

ALTER TABLE Categories
	ADD
		CONSTRAINT PK_Categories
		PRIMARY KEY (
			categories_id
		);

/* 상품그룹 */
CREATE TABLE ProductGroup (
	productgroup_id NUMBER NOT NULL, /* 상품그룹id */
	product_group_name VARCHAR2(200) NOT NULL /* 상품그룹명 */
);

ALTER TABLE ProductGroup
	ADD
		CONSTRAINT PK_ProductGroup
		PRIMARY KEY (
			productgroup_id
		);

/* 상품 */
CREATE TABLE Product (
	product_id NUMBER NOT NULL, /* 상품id */
	product_name VARCHAR2(200) NOT NULL, /* 상품명 */
	product_price NUMBER NOT NULL, /* 상품가격 */
	discount_rate NUMBER NOT NULL, /* 할인율 */
	target_customer_type VARCHAR2(50) NOT NULL, /* 대상고객구분 */
	product_register_date DATE NOT NULL, /* 상품등록일 */
	brand_id NUMBER NOT NULL, /* 브랜드id */
	categories_id NUMBER NOT NULL, /* 카테고리id */
	productgroup_id NUMBER NOT NULL /* 상품그룹id */
);

ALTER TABLE Product
	ADD
		CONSTRAINT PK_Product
		PRIMARY KEY (
			product_id
		);

ALTER TABLE Product
	ADD
		CONSTRAINT FK_Brand_TO_Product
		FOREIGN KEY (
			brand_id
		)
		REFERENCES Brand (
			brand_id
		);

ALTER TABLE Product
	ADD
		CONSTRAINT FK_Categories_TO_Product
		FOREIGN KEY (
			categories_id
		)
		REFERENCES Categories (
			categories_id
		);

ALTER TABLE Product
	ADD
		CONSTRAINT FK_ProductGroup_TO_Product
		FOREIGN KEY (
			productgroup_id
		)
		REFERENCES ProductGroup (
			productgroup_id
		);

/* 상품사진 */
CREATE TABLE ProductImage (
	productimage_id NUMBER NOT NULL, /* 상품사진id */
	file_name VARCHAR2(200) NOT NULL, /* 파일명 */
	sort_order NUMBER NOT NULL, /* 배치순서 */
	product_id NUMBER NOT NULL /* 상품id */
);

ALTER TABLE ProductImage
	ADD
		CONSTRAINT PK_ProductImage
		PRIMARY KEY (
			productimage_id
		);

ALTER TABLE ProductImage
	ADD
		CONSTRAINT FK_Product_TO_ProductImage
		FOREIGN KEY (
			product_id
		)
		REFERENCES Product (
			product_id
		);

/* 포인트내역 */
CREATE TABLE PointTransaction (
	pointtransaction_id NUMBER NOT NULL, /* 포인트내역_id */
	transaction_type VARCHAR2(20) NOT NULL, /* 거래유형 */
	transaction_point NUMBER NOT NULL, /* 거래포인트 */
	reason VARCHAR2(100) NOT NULL, /* 사유 */
	transaction_datetime DATE NOT NULL, /* 거래일시 */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE PointTransaction
	ADD
		CONSTRAINT PK_PointTransaction
		PRIMARY KEY (
			pointtransaction_id
		);

ALTER TABLE PointTransaction
	ADD
		CONSTRAINT FK_Members_TO_PointTransaction
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 회원배송지 */
CREATE TABLE MemberAddress (
	memberaddress_id NUMBER NOT NULL, /* 회원배송지id */
	zip_code NUMBER(5) NOT NULL, /* 우편번호 */
	road_address VARCHAR2(255) NOT NULL, /* 도로명주소 */
	jibun_address VARCHAR2(255) NOT NULL, /* 지번주소 */
	detail_address VARCHAR2(255) NOT NULL, /* 상세주소 */
	address_reference VARCHAR2(255), /* 참고항목 */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE MemberAddress
	ADD
		CONSTRAINT PK_MemberAddress
		PRIMARY KEY (
			memberaddress_id
		);

ALTER TABLE MemberAddress
	ADD
		CONSTRAINT FK_Members_TO_MemberAddress
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 상품게시글 */
CREATE TABLE ProductPost (
	productpost_id NUMBER NOT NULL, /* 상품게시글id */
	content CLOB NOT NULL, /* 내용 */
	post_date DATE NOT NULL, /* 게시일 */
	view_count NUMBER NOT NULL, /* 조회수 */
	product_id NUMBER NOT NULL /* 상품id */
);

ALTER TABLE ProductPost
	ADD
		CONSTRAINT PK_ProductPost
		PRIMARY KEY (
			productpost_id
		);

ALTER TABLE ProductPost
	ADD
		CONSTRAINT FK_Product_TO_ProductPost
		FOREIGN KEY (
			product_id
		)
		REFERENCES Product (
			product_id
		);

/* 상품게시글사진 */
CREATE TABLE ProductPostImage (
	productpostimage_id NUMBER NOT NULL, /* 상품게시글사진id */
	file_name VARCHAR2(200) NOT NULL, /* 파일명 */
	productpost_id NUMBER NOT NULL /* 상품게시글id */
);

ALTER TABLE ProductPostImage
	ADD
		CONSTRAINT PK_ProductPostImage
		PRIMARY KEY (
			productpostimage_id
		);

ALTER TABLE ProductPostImage
	ADD
		CONSTRAINT FK_PrdctPst_TO_PrdctPstmg
		FOREIGN KEY (
			productpost_id
		)
		REFERENCES ProductPost (
			productpost_id
		);

/* 상품게시글Q&A */
CREATE TABLE ProductPostQna (
	productpostqna_id NUMBER NOT NULL, /* 상품게시글Q&Aid */
	title VARCHAR2(100) NOT NULL, /* 제목 */
	content CLOB NOT NULL, /* 내용 */
	write_date DATE NOT NULL, /* 작성일 */
	answer_title VARCHAR2(100), /* 답변제목 */
	answer_content CLOB, /* 답변내용 */
	answer_date DATE, /* 답변작성일 */
	productpost_id NUMBER NOT NULL, /* 상품게시글id */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE ProductPostQna
	ADD
		CONSTRAINT PK_ProductPostQna
		PRIMARY KEY (
			productpostqna_id
		);

ALTER TABLE ProductPostQna
	ADD
		CONSTRAINT FK_PrdctPst_TO_PrdctPstQn
		FOREIGN KEY (
			productpost_id
		)
		REFERENCES ProductPost (
			productpost_id
		);

ALTER TABLE ProductPostQna
	ADD
		CONSTRAINT FK_Members_TO_ProductPostQna
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 상품재고 */
CREATE TABLE ProductStock (
	productstock_id NUMBER NOT NULL, /* 상품재고id */
	shoe_size VARCHAR2(20) NOT NULL, /* 사이즈 */
	stock_quantity NUMBER NOT NULL, /* 재고량 */
	product_id NUMBER NOT NULL /* 상품id */
);

ALTER TABLE ProductStock
	ADD
		CONSTRAINT PK_ProductStock
		PRIMARY KEY (
			productstock_id
		);

ALTER TABLE ProductStock
	ADD
		CONSTRAINT FK_Product_TO_ProductStock
		FOREIGN KEY (
			product_id
		)
		REFERENCES Product (
			product_id
		);

/* 장바구니목록 */
CREATE TABLE CartItem (
	cartitem_id NUMBER NOT NULL, /* 장바구니목록id */
	quantity NUMBER NOT NULL, /* 수량 */
	productstock_id NUMBER NOT NULL, /* 상품재고id */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE CartItem
	ADD
		CONSTRAINT PK_CartItem
		PRIMARY KEY (
			cartitem_id
		);

ALTER TABLE CartItem
	ADD
		CONSTRAINT FK_Members_TO_CartItem
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

ALTER TABLE CartItem
	ADD
		CONSTRAINT FK_ProductStock_TO_CartItem
		FOREIGN KEY (
			productstock_id
		)
		REFERENCES ProductStock (
			productstock_id
		);

/* 주문 */
CREATE TABLE Orders (
	orders_id NUMBER NOT NULL, /* 주문id */
	orders_status NUMBER NOT NULL, /* 주문상태 */
	payment_amount NUMBER NOT NULL, /* 결제금액 */
	payment_point NUMBER NOT NULL, /* 결제포인트 */
	shipping_fee NUMBER NOT NULL, /* 배송비 */
	receiver_name VARCHAR2(50) NOT NULL, /* 수령인명 */
	receiver_email VARCHAR2(40) NOT NULL, /* 수령인이메일 */
	receiver_contact VARCHAR2(30) NOT NULL, /* 수령인연락처 */
	payment_method VARCHAR2(30) NOT NULL, /* 결제방식 */
	payment_info VARCHAR2(100) NOT NULL, /* 결제정보 */
	destination_zip_code NUMBER(5) NOT NULL, /* 배송지우편번호 */
	destination_road_address VARCHAR2(255) NOT NULL, /* 배송지도로명주소 */
	destination_jibun_address VARCHAR2(255) NOT NULL, /* 배송지지번주소 */
	destination_detail_address VARCHAR2(255) NOT NULL, /* 배송지상세주소 */
	destination_reference VARCHAR2(255), /* 배송지참고항목 */
	orders_date DATE NOT NULL, /* 주문일 */
	issuedcoupon_id NUMBER NULL, /* 발급쿠폰id */
	members_id NUMBER NOT NULL /* 회원id */
);

ALTER TABLE Orders
	ADD
		CONSTRAINT PK_Orders
		PRIMARY KEY (
			orders_id
		);

ALTER TABLE Orders
	ADD
		CONSTRAINT FK_IssuedCoupon_TO_Orders
		FOREIGN KEY (
			issuedcoupon_id
		)
		REFERENCES IssuedCoupon (
			issuedcoupon_id
		);

ALTER TABLE Orders
	ADD
		CONSTRAINT FK_Members_TO_Orders
		FOREIGN KEY (
			members_id
		)
		REFERENCES Members (
			members_id
		);

/* 배송진행 */
CREATE TABLE DeliveryProgress (
	deliveryprogress_id NUMBER NOT NULL, /* 배송진행id */
	current_delivery_step VARCHAR2(30) NOT NULL, /* 현재배송단계 */
	current_step_datetime DATE NOT NULL, /* 현재단계일시 */
	next_step_datetime DATE NOT NULL, /* 다음단계시작일시 */
	orders_id NUMBER NOT NULL /* 주문id */
);

ALTER TABLE DeliveryProgress
	ADD
		CONSTRAINT PK_DeliveryProgress
		PRIMARY KEY (
			deliveryprogress_id
		);

ALTER TABLE DeliveryProgress
	ADD
		CONSTRAINT FK_Orders_TO_DeliveryProgress
		FOREIGN KEY (
			orders_id
		)
		REFERENCES Orders (
			orders_id
		);

/* 배송시나리오 */
CREATE TABLE DeliveryScenario (
	deliveryscenario_id NUMBER NOT NULL, /* 배송시나리오id */
	step VARCHAR2(30) NOT NULL, /* 단계 */
	location VARCHAR2(30) NOT NULL, /* 장소 */
	status VARCHAR2(30) NOT NULL, /* 상태 */
	wait_time NUMBER NOT NULL /* 대기시간 */
);

ALTER TABLE DeliveryScenario
	ADD
		CONSTRAINT PK_DeliveryScenario
		PRIMARY KEY (
			deliveryscenario_id
		);

/* 상품재고_주문 */
CREATE TABLE ProductStockOrder (
	productstockorder_id NUMBER NOT NULL, /* 상품재고_주문id */
	quantity NUMBER NOT NULL, /* 수량 */
	order_price NUMBER NOT NULL, /* 주문가격 */
	orders_id NUMBER NOT NULL, /* 주문id */
	productstock_id NUMBER NOT NULL /* 상품재고id */
);

ALTER TABLE ProductStockOrder
	ADD
		CONSTRAINT PK_ProductStockOrder
		PRIMARY KEY (
			productstockorder_id
		);

ALTER TABLE ProductStockOrder
	ADD
		CONSTRAINT FK_PrdctStck_PrdctStckrdr
		FOREIGN KEY (
			productstock_id
		)
		REFERENCES ProductStock (
			productstock_id
		);

ALTER TABLE ProductStockOrder
	ADD
		CONSTRAINT FK_Orders_TO_ProductStockOrder
		FOREIGN KEY (
			orders_id
		)
		REFERENCES Orders (
			orders_id
		);

/* 주문후기 */
CREATE TABLE OrderReview (
	orderreview_id NUMBER NOT NULL, /* 주문후기id */
	content CLOB NOT NULL, /* 내용 */
	rating NUMBER(2,1) NOT NULL, /* 별점 */
	like_count NUMBER NOT NULL, /* 추천수 */
	write_date DATE NOT NULL, /* 작성일 */
	is_blind NUMBER, /* 블라인드여부 */
	productstockorder_id NUMBER NOT NULL /* 상품재고_주문id */
);

ALTER TABLE OrderReview
	ADD
		CONSTRAINT PK_OrderReview
		PRIMARY KEY (
			orderreview_id
		);

ALTER TABLE OrderReview
	ADD
		CONSTRAINT FK_PrdctStckrdr_TO_OrdrRvw
		FOREIGN KEY (
			productstockorder_id
		)
		REFERENCES ProductStockOrder (
			productstockorder_id
		);

/* 주문후기신고 */
CREATE TABLE OrderReviewReport (
	orderreviewreport_id NUMBER NOT NULL, /* 주문후기신고id */
	report_reason VARCHAR2(100) NOT NULL, /* 신고사유 */
	register_date DATE NOT NULL, /* 등록일 */
	orderreview_id NUMBER NOT NULL /* 주문후기id */
);

ALTER TABLE OrderReviewReport
	ADD
		CONSTRAINT PK_OrderReviewReport
		PRIMARY KEY (
			orderreviewreport_id
		);

ALTER TABLE OrderReviewReport
	ADD
		CONSTRAINT FK_OrdrRvw_TO_OrdrRvwRprt
		FOREIGN KEY (
			orderreview_id
		)
		REFERENCES OrderReview (
			orderreview_id
		);

/* 주문후기사진 */
CREATE TABLE OrderReviewImage (
	orderreviewimage_id NUMBER NOT NULL, /* 주문후기사진id */
	file_name VARCHAR2(200) NOT NULL, /* 파일명 */
	orderreview_id NUMBER NOT NULL /* 주문후기id */
);

ALTER TABLE OrderReviewImage
	ADD
		CONSTRAINT PK_OrderReviewImage
		PRIMARY KEY (
			orderreviewimage_id
		);

ALTER TABLE OrderReviewImage
	ADD
		CONSTRAINT FK_OrderReview_TO_OrdrRvwmg
		FOREIGN KEY (
			orderreview_id
		)
		REFERENCES OrderReview (
			orderreview_id
		);
