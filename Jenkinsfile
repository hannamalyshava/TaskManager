pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/hannamalyshava/TaskManager.git',
                    branch: "${env.BRANCH_NAME}"
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Static Analysis') {
            steps {
                bat 'mvn checkstyle:check'
            }
        }

        stage('Upload Artifact') {
            when {
                branch 'main'
            }
            steps {
                bat 'mvn deploy'
            }
        }
    }
}
