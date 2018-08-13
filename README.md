# CRM REST API

The objective is to create a REST API to manage customer data for a small shop. It
will work as the backend side for a CRM interface.

## SETUP

This application is made with Spring-boot so it is very easy to install and run. Also, I used an H2 Database and there is no need to configure nothing else. The only thing you need to do is run this command:
> `mvn spring-boot:run`

## AUTHENTICATION AND AUTHORIZATION

All the endpoints needs basic authentication with username and password to run. There are two different roles in this app, user role and admin role. An user can create customers, update customers, get info of customers and delete customers. An admin can do all the operations with customers and also create users, delete, users, update users, get users info and also can grant admin permission to another user. 

At init 3 different users are created(1 ADMIN and 2 USERS). 

|  USERNAME|PASSWORD  |ROLE|  
|--|--|--|
|  Stark| 1234  |ADMIN
|  Captain   |  1234     |USER
|Spidey    | 1234|USER

To do all the tests I use POSTMAN. Now I am going to explain how to test every endpoint and how to use postman to do it. 

## ENDPOINTS


### User endpoints

This endpoints are only accesible by an ADMIN. 

#### List all users
#### GET http://localhost:8080/user

#### Get user info by id
#### GET http://localhost:8080/user/{id}

#### Create User
#### POST http://localhost:8080/user/

#### Update User
#### PUT http://localhost:8080/user/{id}

#### Delete User
#### DELETE http://localhost:8080/user/{id}

#### Change role
#### PUT http://localhost:8080/user/{id}/isAdmin/{isAdmin}


