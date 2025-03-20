pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://192.168.1.173:9000'
        DOCKER_IMAGE = 'mzamankhan1/java-ci-cd-demo:latest'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Mohammad-Arafath/java-ci-cd-demo.git'
            }
        }

        stage('Build Java Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Analyze Code with SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Ensure Docker permissions
                    sh 'mkdir -p ~/.docker && chmod 777 ~/.docker'

                    // Verify Docker access
                    sh 'docker ps'

                    // Build the Docker image
                    sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh 'docker push ${DOCKER_IMAGE}'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment.yaml --validate=false'
            }
        }
    }
}
