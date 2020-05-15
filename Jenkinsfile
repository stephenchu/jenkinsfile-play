// vim: ft=groovy

import groovy.transform.Field
import com.doordash.Convox
import com.doordash.convox.App


pipeline {
    agent any
    parameters {
        string(name: "SHA", defaultValue: "master")
        string(name: "APP", defaultValue: "pgbouncer-pig")
        string(name: "RACK", defaultValue: "prod-storage")
    }
    stages {
        stage('git') {
            steps {
                git branch: params.SHA, url: "https://github.com/stephenchu/jenkinsfile-play.git"
                script {
                    app = new App(rack: params.RACK, name: params.APP)
                }
            }
        }

        stage('convox.build') {
            steps {
                script {
                    new Convox().build(app)
                    echo "A RELEASE_ID is built: ${app.releaseId}"
                }
            }
        }

        stage('convox.run-and-test') {
            steps {
                script {
                    new Convox().runAndTest(app)
                }
            }
        }

        stage ('convox.deploy') {
            steps {
                script {
                    new Convox().promote(app)
                }
            }
        }
    }
    // post {
        // failure {
            // mail to: 'team@example.com', subject: 'Pipeline failed', body: "${env.BUILD_URL}"
        // }
    // }
}
