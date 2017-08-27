def call(String ver1 = '', String ver2 = '', String prefix = '', String exclude = '') {
    echo "Fetching change sets between $ver1, $ver2, prefix: $prefix, exclude: $exclude"
    paths = changeSets(ver1, ver2)

    modules = []
    for (int i = 0; i < paths.size(); i++) {
        def path = paths[i]
        if (prefix == '' || path.startsWith(prefix)) {
            path = path - prefix
            if (exclude == '' || !path.startsWith(exclude)) {
                if (path.split('/').size() > 1) {
                    modules.add path.split('/')[0]
                }
            }
        }
    }
    modules.toSet()

}

def changeSets(String ver1, String ver2) {
    def multiline = sh returnStdout: true, script: "git diff --name-only $ver1 $ver2"
    echo "Changed files:\n$multiline"
    multiline.readLines()
}