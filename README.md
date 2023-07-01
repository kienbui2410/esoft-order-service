"# esoft-order-service" 

POST /api/v1/order-service/orders -> create
PUT  /api/v1/order-service/orders -> update
DELETE /api/v1/order-service/orders/{id} -> delete
GET /api/v1/order-service/orders  -> get all orders

GET api/v1/order-service/reports/users/{id}/order-number
GET /api/v1/order-service/reports/users/{id}/revenue 
GET /api/v1/order-service/reports/order-revenue-summary/year/{year}
GET /api/v1/order-service/reports/order-revenue-summary/year/{year}/month/{month}
