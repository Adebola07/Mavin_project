def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t adebola07/flaskapp:5.0 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push adebola07/flaskapp:5.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
    echo 'My name is zamani'
} 

return this