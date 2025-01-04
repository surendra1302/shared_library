@Library('shared_library@main') _  // Correct syntax

pipeline {
    agent { label 'slave1' }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
              script {
                shared.checkout()
              }
            }
        }

        stage('Set up Java 17') {
            steps {
              script {
               shared.java()
              }
            }
        }

        stage('Set up Maven') {
            steps {
              script {
               shared.maven()
              }
            }
        }

        stage('Build with Maven') {
            steps {
              script {
                shared.build()
              }
            }
        }

        stage('Tagging theBuild') {
            steps {
              script {
                shared.tag()
              }
            }
        }
      
        stage('Upload Artifact') {
            steps {
              script {
                shared.artifact('target/bus-booking-app-1.0-SNAPSHOT.jar')
              }
            }
        }

        stage('Run Application') {
            steps {
              script {
                shared.run()
              }
            }
        }

        stage('Validate App is Running') {
            steps {
              script {
                shared.validate()
              }
            }
        }

        stage('Gracefully Stop Spring Boot App') {
            steps {
              script {
                shared.stop()
              }
            }
        }
    }

    post {
        always {
            script {
            shared.cleanup()
            }
        }
    }
}
