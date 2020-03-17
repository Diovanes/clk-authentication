#!/bin/sh

echo "================= Mem Java ================="
ps -o rss -p 24502 
echo "" 

echo "================= Threads =================" 
./test.sh 10 2 > test_ab_n1000_c30.txt &

echo "================= Test AB =================" 
ab -n $1 -c $2 -A admin:admin http://3.19.217.251:8080/api/auth 
echo "" 

echo "================= Threads =================" 
curl -s -u admin:admin http://3.19.217.251:8080/api/auth

echo "================= Mem Java ================="
ps -o rss -p 24502 
echo "" 