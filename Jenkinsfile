pipeline {
    agent any

    tools {
        jdk 'jdk'
        maven 'Maven'    // fixed: was misspelled 'mavaen'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn -B clean verify'
            }
        }

        stage('Build Docker Image') {
            steps {
                // fixed: Windows batch uses %VAR% not ${VAR}
                bat 'docker build -t employee-api:%BUILD_NUMBER% .'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
}