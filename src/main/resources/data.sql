DELETE * FROM CRM_ROLES;
DELETE * FROM CRM_USERS;
DELETE * FROM CRM_USER_ROLES;
DELETE * FROM CRM_CUSTOMERS;

Insert into CRM_ROLES
   (ID_ROLE, NAME)
 Values
   (1, 'ADMIN');

Insert into CRM_ROLES
   (ID_ROLE,NAME)
 Values
   (2, 'USER');

Insert into CRM_ROLES
    (ID_ROLE, NAME)
 Values
   (3, 'CUSTOMER');

Insert into CRM_USERS
  (ID_USER, NAME, SURNAME, ROLE, USERNAME, PASSWORD)
  VALUES
    (1, 'Tony', 'Stark', 'ADMIN','Stark', '1234');

Insert into CRM_USERS
  (ID_USER, NAME, SURNAME, ROLE, USERNAME, PASSWORD)
  VALUES
    (2, 'Steve', 'Rogers', 'USER', 'Captain', '1234');

Insert into CRM_USERS
  (ID_USER, NAME, SURNAME, ROLE, USERNAME, PASSWORD)
  VALUES
    (3, 'Peter', 'Parker', 'CUSTOMER', 'Spidey', '1234');


Insert into CRM_CUSTOMERS
  (CREATIONDATE,PHOTOFIELD,ID_USER,CREATEDBY)
  values
    (CURRENT_DATE,'https://vignette.wikia.nocookie.net/avengers-assemble/images/7/77/Ultimate_Spiderman_1.jpg/revision/latest?cb=20160522155142',3,1);


