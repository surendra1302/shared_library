def checkout() {
    echo 'Checking out code...'
    checkout scm
}

def java() {
    echo 'Setting up Java 17...'
    sh 'sudo apt update'
    sh 'sudo apt install -y openjdk-17-jdk'
}

def maven() {
    echo 'Setting up Maven...'
    sh 'sudo apt install -y maven'
}

def build() {
    echo 'Building project with Maven...'
    sh 'mvn clean package'
}

def tag(String tagName, String message = 'Build tagging') {
    if (!tagName?.trim()) {
        error "Tag name cannot be null or empty"
    }

    echo "Tagging the build with tag: ${tagName}"
    
    sh """
        git tag -a "${tagName}" -m "${message}"
        git push origin "${tagName}"
    """
}


def artifact(String artifactPath) {
    echo 'Uploading artifact...'
    archiveArtifacts artifacts: artifactPath, allowEmptyArchive: true
}

def run() {
    echo 'Running Spring Boot application...'
    sh 'nohup mvn spring-boot:run &'
    sleep(time: 15, unit: 'SECONDS')

    def publicIp = sh(script: "curl -s https://checkip.amazonaws.com", returnStdout: true).trim()
    echo "The application is running and accessible at: http://${publicIp}:8080"
}

def validate() {
    echo 'Validating that the app is running...'
    def response = sh(script: 'curl --write-out "%{http_code}" --silent --output /dev/null http://localhost:8080', returnStdout: true).trim()
    if (response == "200") {
        echo 'The app is running successfully!'
    } else {
        echo "The app failed to start. HTTP response code: ${response}"
        error("The app did not start correctly!")
    }
}

def stop() {
    echo 'Gracefully stopping the Spring Boot application...'
    sh 'mvn spring-boot:stop'
}

def cleanup() {
    echo 'Cleaning up...'
    sh 'pkill -f "mvn spring-boot:run" || true'
}
