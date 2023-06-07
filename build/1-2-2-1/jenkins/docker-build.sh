
cp /var/lib/jenkins/workspace/1-2-2-1-gradle-build/build/libs/app-0.0.1-SNAPSHOT.jar ./build/docker/1-2-2-1/app-0.0.1-SNAPSHOT.jar
docker build -t 1pro/app ./build/docker
docker push 1pro/app