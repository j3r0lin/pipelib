#!/usr/bin/groovy
String call() {
    def version = sh( script: 'git rev-parse --short HEAD', returnStdout: true).toString().trim()
    return version
}