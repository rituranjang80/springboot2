pipeline {
    agent any
    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build and Test') {
            steps { sh 'mvn -B clean verify' }
        }
        stage('Build Docker Image') {
            steps { sh 'docker build -t employee-api:${BUILD_NUMBER} .' }
        }
    }
    post {
        always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' }
    }
}
