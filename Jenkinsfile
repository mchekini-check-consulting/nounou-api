node("ci-node") {
    stage("checkout") {
        checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/mchekini-check-consulting/nounou-api.git']]])
    }
    stage("build") {
        sh "chmod 777 mvnw"
        sh "./mvnw clean package -DskipTests=true"
    }

    stage("Quality Analyses"){
        sh "chmod 777 mvnw"
        sh "./mvnw clean verify sonar:sonar \\\n" +
                "  -Dsonar.projectKey=nounou-api \\\n" +
                "  -Dsonar.host.url=http://3.87.90.191:11001 \\\n" +
                "  -Dsonar.login=sqp_434eefc0e835129fe9b68c2a7829d048250172ef"
    }

    stage("build docker image") {
        sh "sudo docker build -t nounou-api ."
    }
    stage("push docker image") {
        sh "sudo docker login -u mchekini -p jskabyliE"
        sh "sudo docker tag nounou-api mchekini/nounou-api:1.0"
        sh "sudo docker push mchekini/nounou-api:1.0"
        stash includes: 'docker-compose.yml', name: 'utils'
    }
    node("integration-node") {
        stage("deploy nounou api") {
            unstash 'utils'
            try {
                sh "sudo docker stop nounou-api"
                sh "sudo docker rm nounou-api"
                sh "sudo docker-compose down"
                sh "sudo docker rmi mchekini/nounou-api:1.0"
            }catch(Exception e){
                println "No container nounou-api running"
            }
            sh "sudo docker-compose up -d"
        }
    }

}
