kubectl delete -f ./deploy/1-2-2-1/k8s/prod-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/prod-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/prod-service.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/prod-hpa.yaml
