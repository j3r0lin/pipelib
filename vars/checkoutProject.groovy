def call() {
    def gitPreCommit = ''
    retry(3) {
        // checkout scm and submodules
        def checkoutVars = checkout scm
        gitPreCommit = checkoutVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT
        if (gitPreCommit == null) {
            gitPreCommit = ''
        }
    }
    return gitPreCommit
}