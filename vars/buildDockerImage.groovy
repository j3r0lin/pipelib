def call(String type = 'java', List<String> services = [], String project = 'elements') {
    for (int i = 0; i < services.size(); i++) {
        def name = services[i]

        def app;
        if (type == 'java') {
            sh "cp Dockerfile ${name}/build/libs/"
            app = docker.build("${project}/${name}", "--no-cache --build-arg app=${name} ${name}/build/libs")
        } else if (type == 'erlang') {
            app = docker.build("${project}/${name}", "--no-cache _build/prod/rel/${name}")
        }

        docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com', 'han-aliyun-registry') {
            if (env.BRANCH_NAME == 'master') {
                app.push(env.version)
            } else {
                app.push('latest')
            }
        }
    }
}