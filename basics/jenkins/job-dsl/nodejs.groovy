job('NodeJS Moshe') {
    scm {
        git('git://github.com/d-alloc/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('1 * * * *')
    }
    wrappers {
        nodejs('NodeJs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install agaim")
    }
}

job('NodeJS Docker') {
    scm {
        git('git://github.com/d-alloc/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('NodeJs') 
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('d-alloc/docker-nodejs-demo') //qa / dev
            buildContext('./basics')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
