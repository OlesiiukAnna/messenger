pipeline {
    agent any
    parameters {
        choice(name: 'build_tool', choices: ['Maven', 'Gradle'], description: 'Choose the build tool to use')
    }
    stages {
        stage('Build') {
            steps {
                script {
                    if (params.build_tool == 'Maven') {
                        sh 'mvn clean install'
                    } else if (params.build_tool == 'Gradle') {
                        sh 'gradle clean build'
                    } else {
                        error "Invalid build tool selected"
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    if (params.build_tool == 'Maven') {
                        sh 'mvn test'
                    } else if (params.build_tool == 'Gradle') {
                        sh 'gradle test'
                    } else {
                        error "Invalid build tool selected"
                    }
                }
            }
        }
    }
}
