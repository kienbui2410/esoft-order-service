"# esoft-order-service" 
########################List API#######################
POST /api/v1/order-service/users/token 
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


POST /api/v1/order-service/orders -> create
PUT  /api/v1/order-service/orders -> update
DELETE /api/v1/order-service/orders/{id} -> delete
GET /api/v1/order-service/orders  -> get all orders

GET api/v1/order-service/reports/users/{id}/order-number
GET /api/v1/order-service/reports/users/{id}/revenue 
GET /api/v1/order-service/reports/order-revenue-summary/year/{year}
GET /api/v1/order-service/reports/order-revenue-summary/year/{year}/month/{month}

########################Deployment####################
checkout source code from repo 