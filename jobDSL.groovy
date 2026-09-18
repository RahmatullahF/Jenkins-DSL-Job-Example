// =======================================
// 1. Freestyle Job - Simple Shell Command
// =======================================

job('1st-freestyle-job') {

    description('This is a simple freestyle job created using Job DSL.')

    triggers {
        cron('* * * * *')   // Run every 5 minutes
    }

    steps {
        shell('''
            echo "Hello from Jenkins Freestyle Job"
            echo "This job was created using Job DSL"
        ''')
    }
}


// =======================================
// 2. Freestyle Job - Git Repository + Python
// =======================================

job('2nd-python-job') {

    description('Runs a Python script from the repository.')

    scm {
        git(
            'https://github.com/RahmatullahF/Jenkins-DSL-Job-Example.git',
            'main'
        )
    }

    triggers {
        scm('* * * * *')   // Every minute
    }

    steps {
        shell('''
            echo "Running Python Script..."
            python3 dsl.py
        ''')
    }
}


// =======================================
// 3. Pipeline Job Creation
// =======================================

pipelineJob('3rd-pipeline-job') {

    description('Pipeline job created using Job DSL.')

    definition {

        cps {

            script('''
                pipeline {

                    agent any

                    stages {

                        stage('Checkout') {
                            steps {
                                echo 'Checking out source code'
                            }
                        }


                        stage('Build') {
                            steps {
                                echo 'Building application'
                            }
                        }


                        stage('Test') {
                            steps {
                                echo 'Running tests'
                            }
                        }


                        stage('Deploy') {
                            steps {
                                echo 'Deploying application'
                            }
                        }
                    }
                }
            ''')

            sandbox()
        }
    }
}


// =======================================
// 4. Pipeline Job with Git Jenkinsfile
// =======================================

pipelineJob('4th-pipeline-from-git') {

    description('Pipeline which loads Jenkinsfile from GitHub.')

    definition {

        cpsScm {

            scm {

                git {

                    remote {
                        url(
                          'https://github.com/RahmatullahF/Jenkins-DSL-Job-Example.git'
                        )
                    }

                    branch('*/main')
                }
            }

            scriptPath('Jenkinsfile')
        }
    }
}


// =======================================
// 5. Create Multiple Similar Jobs Dynamically
// =======================================

def environments = [
    'dev',
    'test',
    'prod'
]


environments.each { env ->


    job("${env}-deployment-job") {


        description(
            "Deployment job for ${env} environment"
        )


        steps {

            shell("""
                echo "Deploying to ${env} environment"
            """)

        }

    }

}
