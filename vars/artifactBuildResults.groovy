def call(Collection<String> java = [], Collection<String> erlang = []) {
    dir('target') {
        script {
            // stash and archive build result
            if (java.size() > 0) {
                sh 'mv ../*/build/libs/*.jar .'
                archiveArtifacts "*.jar"
            }
        }
        // clear workspace
        deleteDir()
    }

    dir('flux') {
        script {
            if (erlang.size() > 0) {
                sh 'mv _build/prod/rel/*/*.tar.gz .'
                script {
                    // stash release packages
                    for (int i = 0; i < erlang.size(); i++) {
                        archiveArtifacts "${erlang[i]}-*.tar.gz"
                    }
                    fileOperations([fileDeleteOperation(excludes: '', includes: "*.tar.gz")])
                }
            }
        }
    }
}
