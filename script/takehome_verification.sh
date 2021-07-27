printf "\n START \n"
printf "Get all active users- returns: 10 records\n"
curl http://localhost:3000/v1/users/

printf "\n Get all worked_hours for user 1- returns: 6 records\n"
curl http://localhost:3000/v1/users/1/worked_hours

printf "\n Post a new worked_hour record - returns: if valid, worked_hour record inserted\n"
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"date": "2020-10-13","hours":5.24}' \
  http://localhost:3000/v1/users/1/worked_hours

printf "\n Get hours for a user with no hours  - returns: User or Hours not found\n"
curl http://localhost:3000/v1/users/10/worked_hours

printf "\n Get hours for an invalid user - returns: User or hours not found\n"
curl http://localhost:3000/v1/users/1009/worked_hours

printf "\n END \n"