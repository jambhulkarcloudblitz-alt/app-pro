pipeline {

    agent any

    environment {
        AWS_REGION = 'eu-west-1'
        EKS_CLUSTER_NAME = 'backend-dev-cluster'
        ECR_REGISTRY = '546736804167.dkr.ecr.eu-west-1.amazonaws.com'
        IMAGE_NAME = 'cloudblitz/auth-service'
    }

    stages {

        stage('PULL') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/jambhulkarcloudblitz-alt/app-pro.git'
            }
        }

        stage('BUILD') {
            steps {
                sh '''
                    cd app/backend/auth-service
                    ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('DOCKER BUILD') {
            steps {
                sh '''
                    cd app/backend/auth-service

                    docker build \
                        -t $ECR_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER \
                        -t $ECR_REGISTRY/$IMAGE_NAME:latest .
                '''
            }
        }

        stage('PUSH') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                     credentialsId: 'aws-credentials']
                ]) {
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION |
                        docker login --username AWS --password-stdin $ECR_REGISTRY

                        docker push $ECR_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER
                        docker push $ECR_REGISTRY/$IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('APPLY') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                     credentialsId: 'aws-credentials']
                ]) {
                    sh '''
                        aws eks update-kubeconfig \
                            --region $AWS_REGION \
                            --name $EKS_CLUSTER_NAME

                        kubectl create namespace cloudblitz \
                            --dry-run=client -o yaml | kubectl apply -f -

                        sed -i "s|image: cloudblitz/auth-service:latest|image: $ECR_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER|g" \
                            app/backend/auth-service/k8s/deployment.yaml

                        kubectl apply -f app/backend/auth-service/k8s/
                    '''
                }
            }
        }
    }
}