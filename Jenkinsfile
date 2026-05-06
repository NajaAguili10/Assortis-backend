// ============================================================
// Jenkinsfile – Spring Boot CI/CD Pipeline
// App port  : 6969
// Container : spring-backend-6969
// Image     : spring-backend:latest
// ============================================================

pipeline {
    agent any

    environment {
        IMAGE_NAME     = 'spring-backend:latest'
        CONTAINER_NAME = 'spring-backend-6969'
        APP_PORT       = '6969'
        POSTGRES_HOST=postgres-server
        POSTGRES_PORT=5432
        POSTGRES_DB=appdb
        POSTGRES_USER=postgres
        POSTGRES_PASSWORD=postgres
    }

    stages {

        // ────────────────────────────────────────────────────
        stage('Checkout') {
        // ────────────────────────────────────────────────────
            steps {
                checkout scm
                echo "Checked out branch: ${env.BRANCH_NAME ?: 'unknown'}"
            }
        }

        // ────────────────────────────────────────────────────
        stage('Detect Build Tool') {
        // ────────────────────────────────────────────────────
            steps {
                script {
                    if (fileExists('mvnw')) {
                        sh 'chmod +x mvnw'
                        env.BUILD_CMD = './mvnw clean package -DskipTests'
                    } else if (fileExists('pom.xml')) {
                        env.BUILD_CMD = 'mvn clean package -DskipTests'
                    } else if (fileExists('gradlew')) {
                        sh 'chmod +x gradlew'
                        env.BUILD_CMD = './gradlew clean build -x test'
                    } else if (fileExists('build.gradle') || fileExists('build.gradle.kts')) {
                        env.BUILD_CMD = 'gradle clean build -x test'
                    } else {
                        error 'No supported build tool detected (expected Maven or Gradle).'
                    }
                    echo "Build command: ${env.BUILD_CMD}"
                }
            }
        }

        // ────────────────────────────────────────────────────
        stage('Build Backend') {
        // ────────────────────────────────────────────────────
            steps {
                sh "${env.BUILD_CMD}"
            }
        }

        // ────────────────────────────────────────────────────
        stage('Build Docker Image') {
        // ────────────────────────────────────────────────────
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
                echo "Docker image built: ${IMAGE_NAME}"
            }
        }

        // ────────────────────────────────────────────────────
        stage('Stop Old Container') {
        // ────────────────────────────────────────────────────
            steps {
                // '|| true' prevents failure when no container is running
                sh "docker stop ${CONTAINER_NAME} || true"
            }
        }

        // ────────────────────────────────────────────────────
        stage('Remove Old Container') {
        // ────────────────────────────────────────────────────
            steps {
                sh "docker rm ${CONTAINER_NAME} || true"
            }
        }

        // ────────────────────────────────────────────────────
        stage('Run New Container') {
        // ────────────────────────────────────────────────────
            steps {
                // Note: POSTGRES_PASSWORD uses shell variable ($POSTGRES_PASSWORD)
                // so its value is never printed in the build log (Jenkins masks it).
                sh """
                    docker run -d \\
                        --name ${CONTAINER_NAME} \\
                        --restart unless-stopped \\
                        -p ${APP_PORT}:${APP_PORT} \\
                        -e SERVER_PORT=${APP_PORT} \\
                        -e POSTGRES_HOST=${POSTGRES_HOST} \\
                        -e POSTGRES_PORT=${POSTGRES_PORT} \\
                        -e POSTGRES_DB=${POSTGRES_DB} \\
                        -e POSTGRES_USER=${POSTGRES_USER} \\
                        -e POSTGRES_PASSWORD="\$POSTGRES_PASSWORD" \\
                        ${IMAGE_NAME}
                """
                echo "Container ${CONTAINER_NAME} started."
            }
        }

        // ────────────────────────────────────────────────────
        stage('Verify Container Running') {
        // ────────────────────────────────────────────────────
            steps {
                script {
                    def running = sh(
                        script: "docker inspect -f '{{.State.Running}}' ${CONTAINER_NAME}",
                        returnStdout: true
                    ).trim()

                    if (running != 'true') {
                        echo "Container is not running. Dumping logs for diagnosis:"
                        sh "docker logs ${CONTAINER_NAME} || true"
                        error "Container ${CONTAINER_NAME} failed to start."
                    }
                    echo "Container ${CONTAINER_NAME} is running."
                }
            }
        }

        // ────────────────────────────────────────────────────
        stage('Health Check') {
        // ────────────────────────────────────────────────────
            // No Spring Boot Actuator detected in this project.
            // We test the root endpoint instead. A 401 response from
            // Spring Security still confirms the app is alive.
            steps {
                script {
                    def maxRetries  = 5
                    def retryDelay  = 10  // seconds
                    def healthy     = false

                    for (int attempt = 1; attempt <= maxRetries; attempt++) {
                        echo "Health check – attempt ${attempt}/${maxRetries}..."

                        def httpCode = sh(
                            script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:${APP_PORT}/ || echo '000'",
                            returnStdout: true
                        ).trim()

                        // Any real HTTP response (including 401 Unauthorized from Spring Security)
                        // means the application is up.
                        if (httpCode != '000') {
                            echo "Health check passed (HTTP ${httpCode})."
                            healthy = true
                            break
                        }

                        if (attempt < maxRetries) {
                            echo "App not ready yet (HTTP ${httpCode}). Retrying in ${retryDelay}s..."
                            sleep(retryDelay)
                        }
                    }

                    if (!healthy) {
                        echo "Health check failed after ${maxRetries} attempts. Container logs:"
                        sh "docker logs ${CONTAINER_NAME} || true"
                        unstable("Health check failed – container is running but app is not responding. See logs above.")
                    }
                }
            }
        }

    } // end stages

    post {
        success {
            echo """
============================================================
  DEPLOYMENT SUCCESSFUL
------------------------------------------------------------
  Image     : ${IMAGE_NAME}
  Container : ${CONTAINER_NAME}
  Port      : http://<your-server-ip>:${APP_PORT}
------------------------------------------------------------
  To view live logs:
    docker logs -f ${CONTAINER_NAME}
  To open a shell inside the container:
    docker exec -it ${CONTAINER_NAME} sh
============================================================
"""
        }
        failure {
            echo "Deployment failed. Running diagnostics..."
            sh "docker ps -a --filter name=${CONTAINER_NAME} || true"
            sh "docker logs ${CONTAINER_NAME} || true"
        }
        always {
            echo "Final container status:"
            sh "docker ps --filter name=${CONTAINER_NAME} --format 'table {{.Names}}\\t{{.Status}}\\t{{.Ports}}' || true"
        }
    }

}
