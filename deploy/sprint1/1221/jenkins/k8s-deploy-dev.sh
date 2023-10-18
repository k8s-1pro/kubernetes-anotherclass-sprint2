kubectl delete -f ./deploy/1-2-2-1/k8s/dev-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/dev-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/dev-service.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/dev-hpa.yaml
