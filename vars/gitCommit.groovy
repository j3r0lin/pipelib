#!/usr/bin/groovy

String call(String sha = 'HEAD') {
    gitCommit = sh(returnStdout: true, script: 'git rev-parse ' + sha).trim()
    return gitCommit.take(6)
}