def call(String type = 'java', Collection<String> services = [], String project = 'elements') {
    for (int i = 0; i < services.size(); i++) {
        def name = services[i]

        def app;
        if (type == 'java') {
            if (fileExists "${name}/Dockerfile") {
                sh "cp ${name}/Dockerfile ${name}/build/libs/"
            } else {
                sh "cp Dockerfile ${name}/build/libs/"
            }
            app = docker.build("${project}/${name}:onbuild", "--build-arg app=${name} ${name}/build/libs")
        } else if (type == 'erlang') {
            app = docker.build("${project}/${name}:onbuild", "_build/prod/rel/${name}")
        }

        docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com', 'han-aliyun-registry') {
            if (env.BRANCH_NAME ==~ /^(master|v.*|release.*)/) {
                echo "push image to registry with tag '${env.version}'"
                app.push(env.version)
            } else if (env.BRANCH_NAME == 'develop') {
                echo "push image to registry with tag 'latest'"
                app.push('latest')
            }
        }
    }
}
