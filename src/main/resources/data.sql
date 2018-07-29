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
  (ID_USER, NAME, SURNAME, USERNAME, PASSWORD)
  VALUES
    (1, 'Tony', 'Stark', 'Stark', '1234');

Insert into CRM_USERS
  (ID_USER, NAME, SURNAME, USERNAME, PASSWORD)
  VALUES
    (2, 'Steve', 'Rogers', 'Captain', '1234');

Insert into CRM_USERS
  (ID_USER, NAME, SURNAME, USERNAME, PASSWORD)
  VALUES
    (3, 'Peter', 'Parker', 'Spidey', '1234');



Insert into CRM_USER_ROLES
   (ID_USER,ID_ROLE)
Values
   (1,1);

Insert into CRM_USER_ROLES
   (ID_USER,ID_ROLE)
Values
   (2,2);

Insert into CRM_USER_ROLES
   (ID_USER,ID_ROLE)
Values
   (3,3);


