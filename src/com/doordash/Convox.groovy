package com.doordash

import com.doordash.convox.*


def build(app) {
    app.releaseId = sh(
        label: "sake convox.build",
        returnStdout: true,
        script: \
            """|#! /bin/bash
               |
               |set -euo pipefail
               |echo >/dev/null sake convox.build -D RACK=${app.rack} -D APP=${app.name}
               |>&2 echo "WARNING: Building docker image..."
               |echo "RBCDEFGHIJ"
               |""".stripMargin()
    ).trim()
}

def runAndTest(app, args=[:]) {
    return sh(
        label: "sake convox.run",
        returnStdout: true,
        script: \
            """|#! /bin/bash
               |
               |set -euo pipefail
               |echo sake convox.run -D RACK=${app.rack} -D APP=${app.name} -D RELEASE=${app.releaseId} -D CMD='bash -c "repl && warmup && shpec"'
               |""".stripMargin()
    ).trim()
}

def promote(app) {
    return sh(
        label: "sake convox.deploy",
        returnStdout: true,
        script: \
            """|#! /bin/bash
               |
               |set -euo pipefail
               |echo sake convox.releases.promote -D RACK=${app.rack} -D APP=${app.name} -D RELEASE=${app.releaseId}
               |""".stripMargin()
    ).trim()
}

return this
