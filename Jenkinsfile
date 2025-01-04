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
                shared.checkoutCode()
              }
            }
        }

        stage('Set up Java 17') {
            steps {
              script {
               shared.setupJava()
              }
            }
        }

        stage('Set up Maven') {
            steps {
              script {
               shared.setupMaven()
              }
            }
        }

        stage('Build with Maven') {
            steps {
              script {
                shared.buildProject()
              }
            }
        }

        stage('Tagging theBuild') {
            steps {
              script {
                shared.tagBuild()
              }
            }
        }
      
        stage('Upload Artifact') {
            steps {
              script {
                shared.uploadArtifact('target/bus-booking-app-1.0-SNAPSHOT.jar')
              }
            }
        }

        stage('Run Application') {
            steps {
              script {
                shared.runApplication()
              }
            }
        }

        stage('Validate App is Running') {
            steps {
              script {
                shared.validateApp()
              }
            }
        }

        stage('Gracefully Stop Spring Boot App') {
            steps {
              script {
                shared.stopApplication()
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
