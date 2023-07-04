# List API

1. API to get token ( allow all access )

    *POST /api/v1/order-service/users/token* 

    Request body:
    
        {
            "username":"esoft_admin",
            "password":"password1"    
        }

    Response Body
    
        {
            "accessToken": "{JWT Key}",
            "tokenType": "Bearer"
        }

    Other APIs must have header Authorization:
    
    Bearer {JWT Key}

2. API for user ( only access for role user. Example: esoft_user/password1 )

    *POST /api/v1/order-service/orders* -> create order

    *PUT  /api/v1/order-service/orders* -> update order

    *DELETE /api/v1/order-service/orders/{id}* -> delete order

    *GET /api/v1/order-service/orders*  -> get all orders
    
    *GET /api/v1/order-service/orders?pageSize=5&pageNo=0&sortBy=id&sortDir=asc*  -> get all orders with pagination

3. API for admin ( only access for role admin. Example: esoft_admin/password1 )

    *GET /api/v1/order-service/reports/users/{id}/order-number* -> get number of order by user

    *GET /api/v1/order-service/reports/users/{id}/revenue* -> get revenue of order by user

    *GET /api/v1/order-service/reports/order-revenue-summary/year/{year}* -> get summary by year

    *GET /api/v1/order-service/reports/order-revenue-summary/year/{year}/month/{month}* -> get summary by month

# Prerequisite

Before you continue, ensure you meet the following requirements:

1. Git client

2. Docker environment ( Example: Docker Desktop on Windows )

# Deployment

1. Checkout source code from repo to new folder ( Example: esoft-order-service)

    `git clone https://github.com/kienbui2410/esoft-order-service.git`

2. From the folder ( esoft-order-service ), run docker compose to deploy services

    `docker-compose up -d`

    After docker container is started successfully, there are 2 services up:
    * order service: port 6868
    * mysql service: port 3307

3. Connect to mysql server: *jdbc:mysql://root:123456@localhost:3307/e_order_db*

    run file *./scripts/sql/InitialSetup/000_createSchema.sql* to setup database

# Use

1. Get jwt token by the API ( expired in 7 days )

    *localhost:6868/api/v1/order-service/users/token*

2. Use the token for other APIs 

