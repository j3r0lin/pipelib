#!/usr/bin/groovy

String call(String sha = 'HEAD') {
    gitCommit = sh(returnStdout: true, script: 'git rev-parse ' + sha).trim()
    echo "git version $gitCommit"
    new String(gitCommit.take(6))
}