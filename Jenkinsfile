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
                echo "Build project"
                sh 'mvn clean package'
            }
        }

        stage('Static Analysis') {
            steps {
                echo "Static analysis"
                sh 'mvn checkstyle:check'
            }
        }

        stage('Upload Artifact') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploy to Nexus"
                sh 'mvn deploy'
            }
        }
    }
}
