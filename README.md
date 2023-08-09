# Employee API
The task is to implement an application that handles employee data for a company through a REST API. Follow the guidelines below to complete the challenge successfully.

# API Endpoints Done
The following API endpoints are available:

# 1. Create an Employee ✅ 
Endpoint: POST /employees

Request body:

json: {
    "email": "alex@google.com",
    "fullName": "alex smith",
    "birthday": "1991-05-12",
    "hobbies": ["hockey", "music"]}

    here Uuid is generated automatically 

# 2. Get a list of all employees ✅ 
Endpoint: GET/employees

[
    {
        "uuid": 2,
        "email": "jane@google.com",
        "fullName": "Jane smith",
        "birthday": "1991-05-12",
        "hobbies": null
    },
    {
        "uuid": 3,
        "email": "alex@google.com",
        "fullName": "alex smith",
        "birthday": "1991-05-12",
        "hobbies": [
            "hockey",
            "music"
        ]
    }
]

# 3. Get a specific employee by uuid ✅ 
Endpoint: GET /employees/{uuid}

# 4. Update an employee ✅ 
Endpoint: PUT /employees/{uuid}

# 5. Delete an employee ✅ 
Endpoint: DELETE /employees/{uuid}

# Restriction
The email field is unique, meaning that two employees cannot share the same email.

# Bonus Features(Done) ✅ 
I have implemented the following bonus features:

1. Swagger Documentation (+):

2. Database Integration with Postgres to store employee records.(+):

3. I have added Unit and Integration Tests

# In Progress
1. I have added the basic authentication but it was giving error while running tests, so I have commented it out. Also need to implement it using OAUTH or JWT.
2. I have created the docker image but was facing error while running the cluster in GCP. And so it is not exposed to external IP. This runs fine in my local setup So, this is again in progress. I have included the config files.
3. I am still working on using Kafka as message broker. Tried some implementations but it was failing. So this is again in progress.

