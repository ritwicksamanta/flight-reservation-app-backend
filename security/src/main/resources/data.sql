INSERT INTO USER_TBL 
	(
		customer_id,
		email, password,
		first_name,last_name,
		phone_number,address,roles)
		VALUES 
				(
					0,
					'admin@domain.com',
					'$2a$10$0av8uZWCwXXpXW2iFiAmE..EZL4BnnGT5mARsY9iNYNMES6Cnkjh.',
					'admin',
					'.',
					'1234567890',
					'Admin Address',
					'ROLE_ADMIN')