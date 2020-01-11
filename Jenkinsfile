#!groovy

void printLineSep(String title) {
    echo "=================================="
    if (title != null) {
        echo "${title}"
        echo " "
    }
}

node {
    def scmVars = checkout scm

    stage('Set-PATH') {
        def mavenHome = tool 'M3'
        def dockerHome = tool 'DOCKER'
        def nodeHome = tool 'NODEJS'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${nodeHome}/bin:${env.PATH}"

        printLineSep("PATHS")
        echo "${env.URL_SERVER_CONSUL}"
    }

    stage("Pre-flight") {
        printLineSep("SCM - Branch")
        echo "${env.BRANCH_NAME}"

        printLineSep("SCM - ChangeSets")
        def changeLogSets = currentBuild.changeSets
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int entryIndex = 0; entryIndex < entries.length; entryIndex++) {
                def entry = entries[entryIndex];
                echo "${entry}"
            }
        }
    }

    stage("Validate") {
        sh """
             mvn validate
        """
    }

    stage("Compile") {
        sh """
             mvn clean compile
        """
    }

    stage("QA-UnitTest") {
        sh """
             mvn test
        """
    }

    stage("QA-JaCoCo") {
        jacoco(
                execPattern: 'target/*.exec',
                classPattern: 'target/classes',
                sourcePattern: 'src/main/java',
                exclusionPattern: 'src/test*'
        )
    }

    stage("QA-Sonar") {
        sh """
             mvn sonar:sonar \\
                  -Dsonar.projectKey=sample-app \\
                  -Dsonar.host.url=http://192.168.1.67:9000 \\
                  -Dsonar.login=75b58e250172f40aca6ce03d5c564e2fb82dab22
        """
    }

    //if (env.BRANCH_NAME == "master") {

    stage("Nexus") {
            sh """
                mvn source:jar deploy -DskipTests -Premote,docker
            """
    }

    stage("Docker") {
        printLineSep("Docker - Host")
        echo "${env.DOCKER_HOST}"

        docker.withServer(env.DOCKER_HOST) {
            def pom = readMavenPom file: 'pom.xml'
            sh """
                chmod +x ./docker-build.sh
                bash ./docker-build.sh ${pom.version}
            """
        }
    }

    /*
    stage('Deploy') {
        build job: 'Sample-App-Deploy',
                parameters: [
                        [$class: 'BooleanParameterValue', name: 'runSmokeTest', value: true],
                        [$class: 'BooleanParameterValue', name: 'runLoadTest', value: true]
                ]
    }
    */

    stage('Deploy') {
        ansiblePlaybook playbook: './target/classes/ansible/ansible-deploy-service.yml'
    }

    stage('Test-Newman') {
        try {
            sh """
                node --version
            """

            sh """
                newman -v
            """

            echo 'Waiting 60 seconds for deployment to complete prior starting smoke testing...'
            sleep 60 // seconds

            echo "Executing smoke tests..."
            sh """
                newman run \\
                    ./src/main/resources/postman/Sample-app-test-cases.postman_collection.json \\
                    -e ./src/main/resources/postman/LOCAL.postman_environment.json \\
                    -r cli,junit \\
                    --reporter-junit-export "reports/sample-app-smoke-test.xml"
            """

        } catch (ex) {
            echo "*** SMOKE TEST ERROR ***"
            echo ex.getMessage()

        } finally {
            junit "reports/sample-app-smoke-test.xml"
        }
    }

    //}
}
