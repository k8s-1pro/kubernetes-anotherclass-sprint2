cp /var/lib/jenkins/workspace/1-2-2-1-gradle-build/build/libs/app-0.0.1-SNAPSHOT.jar ./build/1-2-2-1/docker/app-0.0.1-SNAPSHOT.jar
docker build -t 1pro/app-update ./build/1-2-2-1/docker
docker push 1pro/app-update