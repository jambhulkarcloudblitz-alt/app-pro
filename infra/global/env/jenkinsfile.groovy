pipeline {

    agent any

    parameters {

        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'stage', 'prod'],
            description: 'Select environment'
        )

        choice(
            name: 'ACTION',
            choices: ['create', 'delete'],
            description: 'Select Terraform action'
        )
    }


    stages {

        stage('PULL') {
            steps {
                git branch: 'dev', url: 'https://github.com/jambhulkarcloudblitz-alt/app-pro.git'
            }
        }


        stage('TERRAFORM INIT') {
            steps {
                sh '''
                    cd infra/global/env/${ENVIRONMENT}
                    terraform init
                '''
            }
        }


        stage('TERRAFORM PLAN') {
            steps {
                sh '''
                    cd infra/global/env/${ENVIRONMENT}

                    if [ "$ACTION" = "delete" ]; then
                        terraform plan -destroy
                    else
                        terraform plan
                    fi
                '''
            }
        }


        stage('APPROVAL') {
            steps {
                input message: "Do you want to continue with ${ACTION} for ${ENVIRONMENT}?",
                      ok: 'APPROVE'
            }
        }


        stage('TERRAFORM APPLY') {
            steps {
                sh '''
                    cd infra/global/env/${ENVIRONMENT}

                    if [ "$ACTION" = "delete" ]; then
                        terraform destroy -auto-approve
                    else
                        terraform apply -auto-approve
                    fi
                '''
            }
        }

    }


    post {

        success {
            echo "✅ Terraform ${params.ACTION} completed successfully for ${params.ENVIRONMENT}"
        }

        failure {
            echo "❌ Terraform deployment failed"
        }
    }
}

