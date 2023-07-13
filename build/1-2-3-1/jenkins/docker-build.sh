cp /var/lib/jenkins/workspace/1-2-3-1-gradle-build/build/libs/app-0.0.1-SNAPSHOT.jar ./build/1-2-3-1/docker/app-0.0.1-SNAPSHOT.jar
docker build -t 1pro/api-tester:v2.0.0 ./build/1-2-3-1/docker
docker push 1pro/api-tester:v2.0.0