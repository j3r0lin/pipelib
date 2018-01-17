def call(String type = 'java', Collection<String> services = [], String project = 'elements') {
    for (int i = 0; i < services.size(); i++) {
        def name = services[i]

        def app;
        if (type == 'java') {
            sh "cp Dockerfile ${name}/build/libs/"
            app = docker.build("${project}/${name}", "--build-arg app=${name} ${name}/build/libs")
        } else if (type == 'erlang') {
            app = docker.build("${project}/${name}", "_build/prod/rel/${name}")
        }

        docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com', 'han-aliyun-registry') {
            if (env.BRANCH_NAME ==~ /^(master|v)/) {
                app.push(env.version)
            } else if (env.BRANCH_NAME == 'develop') {
                app.push('latest')
            }
        }
    }
}
