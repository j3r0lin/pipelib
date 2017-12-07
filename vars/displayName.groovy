def call() {
    "${version}-build-${currentBuild.number}-${gitCommit()}"
}