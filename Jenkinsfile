@Library('shared_library@main') _  // Correct syntax

pipeline {
    agent { label 'slave' }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
              script {
                pipeline.checkout()
              }
            }
        }

        stage('Set up Java 17') {
            steps {
              script {
                pipeline.java()
              }
            }
        }

        stage('Set up Maven') {
            steps {
              script {
                pipeline.maven()
              }
            }
        }

        stage('Build with Maven') {
            steps {
              script {
                pipeline.build()
              }
            }
        }

        stage('Tagging theBuild') {
            steps {
              script {
                pipeline.tag()
              }
            }
        }
      
        stage('Upload Artifact') {
            steps {
              script {
                pipeline.artifact('target/bus-booking-app-1.0-SNAPSHOT.jar')
              }
            }
        }

        stage('Run Application') {
            steps {
              script {
                pipeline.run()
              }
            }
        }

        stage('Validate App is Running') {
            steps {
              script {
                pipeline.validate()
              }
            }
        }

        stage('Gracefully Stop Spring Boot App') {
            steps {
              script {
                pipeline.stop()
              }
            }
        }
    }

    post {
        always {
            pipeline.cleanup()
        }
    }
}
