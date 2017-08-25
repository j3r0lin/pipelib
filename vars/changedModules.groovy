def call(String prefix = '', String exclude = '') {
    paths = filesFromLastSuccessfulBuild()
    getAffectedJavaMoudles(paths)

    modules = []
    for (int i = 0; i < paths.size(); i++) {
        def splits = paths[i].split('/')
        if (splits.size() > 1 && splits[0] != 'flux') {
            modules.add(splits[0])
        }
    }
    modules.toSet().findAll{it ->
        (prefix != '' && it.startsWith(prefix)) && (exclude != '' && !it.startsWith(exclude))
    }
}

def filesFromLastSuccessfulBuild() {
    passedBuilds = []
    build = currentBuild

    while ((build != null) && (build.result != 'SUCCESS')) {
        passedBuilds.add(build)
        build = build.getPreviousBuild()
    }

    paths = []
    for (int x = 0; x < passedBuilds.size(); x++) {
        def currentBuild = passedBuilds[x];
        def changeLogSets = currentBuild.rawBuild.changeSets
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                paths.addAll(entry.getAffectedPaths())
            }
        }
    }
    paths;
}