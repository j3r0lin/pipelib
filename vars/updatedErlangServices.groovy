def call(gitPreCommit = '', services = []) {
    def dirs = changedModules(gitPreCommit, 'flux/apps').intersect(services)
    if (params.buildErlang || env.BRANCH_NAME ==~ /(master)|(release)|(PR).*/) {
        dirs = services
    }
    echo "erlang services to build: ${dirs.join(',')}"
    return dirs
}