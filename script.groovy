def buildJar() {
    echo "building the application..."
    sh "mvn clean"
    sh 'mvn package'
} 

def buildImage() {
    echo "what to do for you laidis..."
    withCredentials([usernamePassword(credentialsId: 'nexus-cred', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t 34.207.191.12:8080/myapp:${imageId} ."
        sh 'echo $PASS | docker login -u $USER --password-stdin 34.207.191.12:8080'
        sh "docker push 34.207.191.12:8080/myapp:${imageId}"
    }
} 

def deployApp() {
    sshagent(['gitjen']) {
        sh 'ssh-keyscan -t ed25519 github.com >> ~/.ssh/known_hosts'
        sh 'git config remote.origin.url git@github.com:Adebola07/Mavin_project.git'
        sh 'git config --global user.email 07zamani@gmail.com'
        sh 'git config --global user.name jenkins'
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