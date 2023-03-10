def buildJar() {
    echo "building the application..."
    sh "mvn clean"
    sh 'mvn package'
} 

def buildImage() {
    echo "what to do laidis..."
    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t adebola07/flaskapp:5.1 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push adebola07/flaskapp:5.1'
    }
} 

def deployApp() {
    echo 'deploying the application...'
    echo 'My name is zamani'
    echo "Yes we made it by God's grace"
} 

return this