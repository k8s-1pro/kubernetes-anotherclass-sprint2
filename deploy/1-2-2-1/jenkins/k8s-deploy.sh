kubectl delete -f ./deploy/k8s/1-2-2-1/k8s/deployment.yaml
kubectl apply -f ./deploy/k8s/1-2-2-1/k8s/deployment.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/service.yaml
kubectl apply -f ./deploy/1-2-2-1/k8s/hpa.yaml