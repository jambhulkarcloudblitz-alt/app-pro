```groovy
pipeline {

    agent any

    environment {
        AWS_REGION = 'eu-west-1'
        S3_BUCKET = 'classproject.shop'
    }

    stages {

        stage('PULL') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/jambhulkarcloudblitz-alt/app-pro.git'
            }
        }

        stage('INSTALL') {
            steps {
                sh '''
                    cd app/frontend
                    npm install
                '''
            }
        }

        stage('BUILD') {
            steps {
                sh '''
                    cd app/frontend

                    // export VITE_AUTH_API="https://api.junioraicoders.com/api/auth"
                    // export VITE_COURSE_API="https://api.junioraicoders.com/api/courses"
                    // export VITE_ENROLL_API="https://api.junioraicoders.com/api/enroll"

                    npm run build
                '''
            }
        }

        stage('UPLOAD') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                     credentialsId: 'aws-credentials']
                ]) {
                    sh '''
                        cd app/frontend

                        aws s3 sync dist/ s3://$S3_BUCKET/ --delete
                    '''
                }
            }
        }

    }
}
```
