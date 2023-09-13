pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize') {
            steps {
                sh '''
                    docker stop ssgpointapp_event || true
                    docker rm ssgpointapp_event || true
                    docker rmi ssgpoint_event || true
                    docker build -t ssgpoint_event .
                    echo ${MASTER_DB_URL}
                    echo $MASTER_DB_URL
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker run -e MASTER_DB_URL="jdbc:mysql://15.164.17.12:3310/ssgevent?serverTimezone=Asia/Seoul&characterEncoding=UTF-8" -e MASTER_DB_USERNAME="${MASTER_DB_USERNAME}" -e MASTER_DB_PASSWORD="${MASTER_DB_PASSWORD}" -e SLAVE_DB_URL="jdbc:mysql://15.164.17.12:3311/ssgevent?serverTimezone=Asia/Seoul&characterEncoding=UTF-8" -e SLAVE_DB_USERNAME="${SLAVE_DB_USERNAME}" -e SLAVE_DB_PASSWORD="${SLAVE_DB_PASSWORD}" -e JWT_SECRET="${JWT_SECRET}" -d --network root_net-ssg-mysql --name ssgpointapp_event -p 8003:8003 ssgpoint_event'
            }
        }
    }
}