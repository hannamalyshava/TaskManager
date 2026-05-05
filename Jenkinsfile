pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/hannamalyshava/TaskManager.git',
                    branch: "${env.BRANCH_NAME}"
            }
        }
 stage('Info') {
            steps {
                bat 'gradlew.bat build --info'
            }
        }
        stage('Build') {
            steps {
                bat 'gradlew.bat build'
            }
        }

        stage('Test') {
            steps {
                bat 'gradlew.bat test'
            }
        }

        stage('Static Analysis') {
            steps {
                bat 'gradlew.bat check'
            }
        }
            

        stage('Upload Artifact') {
            when {
                branch 'main'
            }
            steps {
                echo "Build artifact ready (can upload to Nexus later)"
            }
        }
    }
}
