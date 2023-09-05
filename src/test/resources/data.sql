INSERT INTO user (id, email, password)
VALUES (1, "user_email_1", "user_password_1"),
       (2, "user_email_2", "user_password_2");

INSERT INTO auth (auth_id, user_id, role)
VALUES (1,1,"USER"),
VALUES (2,2,"USER");