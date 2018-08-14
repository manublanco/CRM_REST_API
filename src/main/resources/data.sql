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
    (1, 'Mary Jane', 'Parker', CURRENT_DATE,'https://www.googleapis.com/download/storage/v1/b/crm-theam-rest-api-images/o/maryjane.jpeg?generation=1534242512296495&alt=media',3);


Insert into CRM_CUSTOMERS
  (ID_CUSTOMER, NAME, SURNAME, CREATIONDATE,PHOTOFIELD,CREATEDBY)
  values
    (2, 'May', 'Parker', CURRENT_DATE,'https://www.googleapis.com/download/storage/v1/b/crm-theam-rest-api-images/o/MayParker.jpg?generation=1534242512421260&alt=media',3);
