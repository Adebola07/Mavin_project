def buildJar() {
    echo "building the application..."
    sh "mvn clean"
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'nexus-cred', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t 44.212.69.70:8080/flaskapp:5.0 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin 44.212.69.70:8080'
        sh 'docker push 44.212.69.70:8080/flaskapp:5.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
    echo 'My name is zamani'
    echo "Yes we made it by God's grace"
} 

return this