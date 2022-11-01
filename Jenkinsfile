node("ci-node") {
    stage("checkout") {
        checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/mchekini-check-consulting/nounou-api.git']]])
    }
    stage("build") {
        sh "chmod 777 mvnw"
        sh "./mvnw clean package"
    }
    stage("build docker image") {
        sh "sudo docker build -t nounou-api ."
    }
    stage("push docker image") {
        sh "sudo docker login -u mchekini -p jskabyliE"
        sh "sudo docker tag nounou-api mchekini/nounou-api:1.0"
        sh "sudo docker push mchekini/nounou-api:1.0"
    }
    node("integration-node") {
        stage("deploy nounou api") {
            try {
                sh "sudo docker stop nounou-api"
                sh "sudo docker rm nounou-api"
                sh "sudo docker rmi mchekini/nounou-api:1.0"
            }catch(Exception e){
                println "No container nounou-api running"
            }
            sh "sudo docker run -p 8080:8080 --name nounou-api -d mchekini/nounou-api:1.0"
        }
    }

}
