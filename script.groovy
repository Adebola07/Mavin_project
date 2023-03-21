def buildJar() {
    echo "building the application..."
    sh "mvn clean"
    sh 'mvn package'
} 

def buildImage() {
    echo "what to do for you laidis..."
    withCredentials([usernamePassword(credentialsId: 'nexus-cred', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t 3.87.55.131:8080/nexus_image:${imageId} ."
        sh 'echo $PASS | docker login -u $USER --password-stdin 3.87.55.131:8080'
        sh "docker push 3.87.55.131:8080/nexus-image:${imageId}"
    }
} 

def deployApp() {
    echo 'deploying the application.'
    echo 'My name is 07zamani'
    echo "Yes we made it by God's grace"
}

def incrementingVersion() {
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit'
    def version = readFile ('pom.xml') =~ '<version>(.+)</version>'
    def image = version[0][1]
    env.imageId = "$image-$BUILD_NUMBER"
}

return this