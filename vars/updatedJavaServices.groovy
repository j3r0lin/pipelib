def call(gitPreCommit = '', services = []) {
    def dirs = changedModules(gitPreCommit).intersect(services)
    if (dirs.contains('common') || params.buildJava || env.BRANCH_NAME ==~ /(master)|(release)|(PR).*/) {
        dirs = services
    }
    echo "java services to build: ${dirs.join(',')}"
    return dirs
}