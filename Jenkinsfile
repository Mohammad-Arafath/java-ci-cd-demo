pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://192.168.229.128:9000'
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
                sh 'mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=squ_ce888b9aee12fc77551f8f985fce18b5e7b390fc'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build --no-cache -t "$DOCKER_IMAGE" .'
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh 'docker push "$DOCKER_IMAGE"'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh 'export KUBECONFIG=/var/jenkins_home/kubeconfig && kubectl apply -f deployment.yaml --validate=false'
                }
            }
        }
    }
}
