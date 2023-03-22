def buildJar() {
    echo "building the application..."
    sh "mvn clean"
    sh 'mvn package'
} 

def buildImage() {
    echo "what to do for you laidis..."
    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t adebola07/flaskapp:${imageId} ."
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh "docker push adebola07/flaskapp:${imageId}"
    }
} 

def deployApp() {
    sshagent(['gitjen']) {
        sh 'git config remote.origin.url git@github.com:Adebola07/Mavin_project.git'
        sh 'git config --global user.email oyedejiadebola07@gmail.com'
        sh 'git config --global user.name jenkins'
        sh 'git pull origin HEAD:main'
        sh 'git add .'
        sh 'git commit -m "commit to git"'
        sh 'git push origin HEAD:main'
    }
    
}

def incrementingVersion() {
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.nextMinorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit'
    def version = readFile ('pom.xml') =~ '<version>(.+)</version>'
    def image = version[0][1]
    env.imageId = "$image-$BUILD_NUMBER"
}

return this