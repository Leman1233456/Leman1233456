#!/bin/bash

echo "=== Testing To-Do List API ==="
echo

# Base URL
BASE_URL="http://localhost:8080"

echo "1. Testing Authentication (Login)"
echo "--------------------------------"
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}')

echo "Response: $LOGIN_RESPONSE"
echo

# Extract token from response
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "Failed to get token. Exiting."
    exit 1
fi

echo "2. Testing Task Creation"
echo "------------------------"
CREATE_RESPONSE=$(curl -s -X POST $BASE_URL/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"Complete project","description":"Finish the Spring Boot project","completed":false}')

echo "Create Response: $CREATE_RESPONSE"
echo

# Extract task ID from response
TASK_ID=$(echo $CREATE_RESPONSE | grep -o '"id":[0-9]*' | cut -d':' -f2)

echo "3. Testing Get All Tasks"
echo "------------------------"
curl -s -X GET $BASE_URL/api/tasks \
  -H "Authorization: Bearer $TOKEN" | jq .
echo

echo "4. Testing Get Single Task"
echo "--------------------------"
curl -s -X GET $BASE_URL/api/tasks/$TASK_ID \
  -H "Authorization: Bearer $TOKEN" | jq .
echo

echo "5. Testing Update Task"
echo "----------------------"
curl -s -X PUT $BASE_URL/api/tasks/$TASK_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"Updated task","description":"Updated description","completed":true}' | jq .
echo

echo "6. Testing Get Updated Task"
echo "---------------------------"
curl -s -X GET $BASE_URL/api/tasks/$TASK_ID \
  -H "Authorization: Bearer $TOKEN" | jq .
echo

echo "7. Testing Get Completed Tasks"
echo "------------------------------"
curl -s -X GET $BASE_URL/api/tasks/completed \
  -H "Authorization: Bearer $TOKEN" | jq .
echo

echo "8. Testing Delete Task"
echo "----------------------"
curl -s -X DELETE $BASE_URL/api/tasks/$TASK_ID \
  -H "Authorization: Bearer $TOKEN"
echo "Task deleted successfully"
echo

echo "9. Testing Get All Tasks (After Delete)"
echo "---------------------------------------"
curl -s -X GET $BASE_URL/api/tasks \
  -H "Authorization: Bearer $TOKEN" | jq .
echo

echo "=== API Testing Complete ==="