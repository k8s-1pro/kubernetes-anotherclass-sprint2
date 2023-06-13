kubectl delete -f ./deploy/1-2-2-1/k8s/qa-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/qa-deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/qa-service.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/qa-hpa.yaml
