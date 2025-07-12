# Definindo o nome do namespace
$Namespace = 'ingress-basic'

Write-Host "Adicionando o repositório ingress-nginx..."
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo add jetstack https://charts.jetstack.io

Write-Host "Atualizando os repositórios..."
helm repo update

Write-Host "Instalando o ingress-nginx no namespace '$Namespace'..."
helm install ingress-nginx ingress-nginx/ingress-nginx --create-namespace --namespace $Namespace --set controller.service.annotations."service\.beta\.kubernetes\.io/azure-load-balancer-health-probe-request-path"=/healthz --set controller.service.externalTrafficPolicy=Local

kubectl label namespace ingress-basic cert-manager.io/disable-validation=true
helm install cert-manager jetstack/cert-manager --namespace ingress-basic --version v1.12.1 --set installCRDs=true

Write-Host "Aguarde enquanto o Helm instala o ingress-nginx. Pressione Enter para fechar o PowerShell quando terminar."
Read-Host -Prompt "Pressione Enter para fechar"
