DELETE * FROM CRM_USERS;
DELETE * FROM CRM_CUSTOMERS;

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
    (3, 'Peter', 'Parker', 'USER', 'Spidey', '1234');


Insert into CRM_CUSTOMERS
  (ID_CUSTOMER, NAME, SURNAME, CREATIONDATE,PHOTOFIELD,CREATEDBY)
  values
    (1, 'Mary Jane', 'Parker', CURRENT_DATE,'https://storage.cloud.google.com/crm-rest-api-images/maryjane.jpg?_ga=2.72422747.-977466062.1534120210',3);


Insert into CRM_CUSTOMERS
  (ID_CUSTOMER, NAME, SURNAME, CREATIONDATE,PHOTOFIELD,CREATEDBY)
  values
    (2, 'May', 'Parker', CURRENT_DATE,'https://storage.cloud.google.com/crm-rest-api-images/MayParker.jpg?_ga=2.203931352.-977466062.1534120210',3);
