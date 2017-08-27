def call(String commit = '', String path = '', String exclude = '') {
    echo "Fetching change sets from $commit, path: $path, exclude: $exclude"
    paths = changeSets commit, path

    modules = []
    for (int i = 0; i < paths.size(); i++) {
        path = paths[i]
        if (exclude == '' || !path.startsWith(exclude)) {
            if (path.split('/').size() > 1) {
                modules.add path.split('/')[0]
            }
        }
    }
    modules.toSet()

}

def changeSets(String commit, String path = '') {
    def multiline = sh returnStdout: true, script: "git diff --name-only $commit -- $path"
    echo "Changed files:\n$multiline"
    multiline.readLines()
}