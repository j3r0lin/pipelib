def call(gitPreCommit = '', services = []) {
    def dirs = changedModules(gitPreCommit)
    if (dirs.contains('common') || params.buildJava || env.BRANCH_NAME ==~ /(master)|(release)|(PR).*/) {
        dirs = services
    } else {
        dirs = dirs.intersect(services)
    }

    echo "java services to build: ${dirs.join(',')}"
    return dirs
}