def call() {
    def result = currentBuild.currentResult.toLowerCase()
    def color = currentBuild.resultIsWorseOrEqualTo('UNSTABLE') ? 'danger': 'good'
    slackSend color: color, message: messageFormat("${result} after ${durationString()}")
}

def durationString() {
    def dur = currentBuild.build().getDurationString()

    // the build hasn't technically ended while the pipeline script is still
    // executing, so jenkins is appending "and counting" to the duration string
    def m = dur =~ /(.*) and counting/
    m[0][1]
}


def messageFormat(String detail) {
    "${JOB_NAME}-${currentBuild.displayName} - ${detail} (<${env.BUILD_URL}|Open>) (<${env.BUILD_URL}/console|Console>)"
}
