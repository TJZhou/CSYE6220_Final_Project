**DuckBill Backend Endpoints**

Path: `/user`
         
- `/user` - POST: create/register a user
        
- `/user/{id}` - GET: get a user by id

- `/user/login` - POST: check user login credential

- `/users/{id}` - PUT: update user info by id

- `/users/pswd/{id}` - PUT: update user password by id

Path: `/income`

- `/{userId}` - POST: create an income related to certain user

- `/{incomeId}` - PUT: update an income

- `/{incomeId}` - DELETE: delete an income

- `/{userId}?date=XXX` - GET: get all incomes by userId and income date

- `/overview/{userId}?date=XXX` - GET: get all incomes first and then group by category

Path: `/expense`

- `/{userId}` - POST: create an expense related to certain user

- `/{expenseId}` - PUT: update an expense

- `/{expenseId}` - DELETE: delete an expense

- `/{userId}?date=XXX` - GET: get all expense by userId and income date

- `/overview/{userId}?date=XXX` - GET: get all expense first and then group by category

Path: `/group`

- `/{userId}` - GET: get all groups a user joined in

- `/one/{groupId}` - GET: get a certain group by groupId

- `/{userId}` - POST: create a group related to certain user

- `/{userId}/{groupId}` - POST: join a group by group id

- `/{userId}/{groupId}` - PUT: update a group by group id

- `/{userId}/{groupId}` - DELETE: delete the whole group, together with all bills within this group


Path: `/bill`

- `/{groupId}` - GET: get all bills within a group

- `/{billContributorId}/{groupId}` - POST: create a bill within a group

- `/{billId}` - PUT: update a certain bill by billId

- `/{groupId}/{billId}}` - PUT: update bill participants

- `/{groupId}/{billId}` - DELETE: delete a bill within certain group

- `/calc/{groupId}` - GET: calculate and split bills


## [Status Code Related Information](src/main/java/edu/neu/csye6220/models/enums/Status.java)
