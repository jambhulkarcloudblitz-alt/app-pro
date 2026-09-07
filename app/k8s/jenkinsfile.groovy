pipeline {

    agent any

    environment {
        AWS_REGION = 'us-west-2'
        EKS_CLUSTER_NAME = 'backend-dev-cluster'
    }

    stages {

        stage('PULL') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/jambhulkarcloudblitz-alt/app-pro.git'
            }
        }

        stage('CONNECT EKS') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                     credentialsId: 'aws-credentials']
                ]) {
                    sh '''
                        aws eks update-kubeconfig \
                            --region $AWS_REGION \
                            --name $EKS_CLUSTER_NAME
                    '''
                }
            }
        }

        stage('APPLY K8S') {
            steps {
                sh '''
                    kubectl apply -f app/k8s/
                '''
            }
        }

        stage('CHECK') {
            steps {
                sh '''
                    kubectl get pods -A
                    kubectl get svc -A
                    kubectl get ingress -A
                '''
            }
        }

    }
}