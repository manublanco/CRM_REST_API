# CRM REST API

The objective is to create a REST API to manage customer data for a small shop. It
will work as the backend side for a CRM interface.

## SETUP

This application is made with Spring-boot so it is very easy to install and run. Also, I used an H2 Database and there is no need to configure nothing else. The only thing you need to do is run this command:
> `mvn spring-boot:run`

## AUTHENTICATION AND AUTHORIZATION

All the endpoints needs basic authentication with username and password to run. There are two different roles in this app, user role and admin role. An user can create customers, update customers, get info of customers and delete customers. An admin can do all the operations with customers and also create users, delete users, update users, get users info and also can grant admin role to another user. 

At init 3 different users are created(1 ADMIN and 2 USERS). 

|  USERNAME|PASSWORD  |ROLE|  
|--|--|--|
|  Stark| 1234  |ADMIN
|  Captain   |  1234     |USER
|Spidey    | 1234|USER


## ENDPOINTS

To do all the tests I use POSTMAN. Now I am going to explain how to test every endpoint and how to use postman to do it in cases that I think is needed.


### User endpoints

This endpoints are only accesible by an ADMIN. 

#### List all users
#### GET http://localhost:8080/user

If you send a GET request in POSTMAN to this endpoint, you will receive a list of all users in JSON format. 

```javascript
[
    {
        "id": 1,
        "name": "Tony",
        "surname": "Stark",
        "role": "ADMIN",
        "username": "Stark",
        "password": "1234"
    },
    {
        "id": 2,
        "name": "Steve",
        "surname": "Rogers",
        "role": "USER",
        "username": "Captain",
        "password": "1234"
    },
    {
        "id": 3,
        "name": "Peter",
        "surname": "Parker",
        "role": "USER",
        "username": "Spidey",
        "password": "1234"
    }
]
```


#### Get user info by id
#### GET http://localhost:8080/user/{id}

If you send a GET request with a specific id, for instance: http://localhost:8080/user/1

You will receive a JSON with the information of that particular user.
```javascript
{
    "id": 1,
    "name": "Tony",
    "surname": "Stark",
    "role": "ADMIN",
    "username": "Stark",
    "password": "1234"
}
```

If the user with that id doesn' exist you will receive a 404 Error with the message "User not found for ID: 1"

#### Create User
#### POST http://localhost:8080/user/

To create an user you have to send a POST request with a JSON in the body. So, in POSTMAN you have to select POST request and then in the body part, select raw and application/json and insert a JSON like this: 
```javascript
{
    "name": "Hank",
    "surname": "Pym",
    "username": "Pym",
    "password": "1234"
}
```

The username has to be unique, so if the username is already taken the endpoint will show a 400 Bad Request with the message "Username already taken"


#### Update User
#### PUT http://localhost:8080/user/{id}

#### Delete User
#### DELETE http://localhost:8080/user/{id}

#### Change role
#### PUT http://localhost:8080/user/{id}/isAdmin/{isAdmin}

## Customer endpoints

This endpoints are accesible by ADMINS and USERS.

#### List all customers
#### GET http://localhost:8080/customer

If you send a GET request in POSTMAN to this endpoint, you will receive a list of all customers in JSON format. 

```javascript
[
    {
        "id": 1,
        "name": "Mary Jane",
        "surname": "Parker",
        "photoField": "https://www.googleapis.com/download/storage/v1/b/crm-theam-rest-api-images/o/maryjane.jpeg?generation=1534242512296495&alt=media",
        "createdBy": {
            "id": 3,
            "name": "Peter",
            "surname": "Parker",
            "role": "USER",
            "username": "Spidey",
            "password": "1234"
        },
        "creationDate": "2018-08-14",
        "updatedBy": null,
        "lastUpdate": null
    },
    {
        "id": 2,
        "name": "May",
        "surname": "Parker",
        "photoField": "https://www.googleapis.com/download/storage/v1/b/crm-theam-rest-api-images/o/MayParker.jpg?generation=1534242512421260&alt=media",
        "createdBy": {
            "id": 3,
            "name": "Peter",
            "surname": "Parker",
            "role": "USER",
            "username": "Spidey",
            "password": "1234"
        },
        "creationDate": "2018-08-14",
        "updatedBy": null,
        "lastUpdate": null
    }
]
```


#### Get customer info by id
#### GET http://localhost:8080/customer/{id}

If you send a GET request with a specific id, for instance: http://localhost:8080/customer/1

You will receive a JSON with the information of that particular customer.
{
    "id": 1,
    "name": "Mary Jane",
    "surname": "Parker",
    "photoField": "https://www.googleapis.com/download/storage/v1/b/crm-theam-rest-api-images/o/maryjane.jpeg?generation=1534242512296495&alt=media",
    "createdBy": {
        "id": 3,
        "name": "Peter",
        "surname": "Parker",
        "role": "USER",
        "username": "Spidey",
        "password": "1234"
    },
    "creationDate": "2018-08-14",
    "updatedBy": null,
    "lastUpdate": null
}

If the user with that id doesn' exist you will receive a 404 Error with the message "Customer not found for ID: 1"


#### Create Customer
#### POST http://localhost:8080/customer/

#### Update Customer
#### PUT http://localhost:8080/customer/{id}

#### Delete Customer
#### DELETE http://localhost:8080/customer/{id}


