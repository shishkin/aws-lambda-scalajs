# AWS Lambda in Scala.JS

## Build

```shell
sbt lambda/fastOptJS::webpack
```

## Run Locally

```shell
docker run --rm \
    -v $(pwd)/lambda/target/scala-2.13/scalajs-bundler/main:/var/task:ro,delegated \
    mlupin/docker-lambda:nodejs14.x \
    lambda-fastopt-bundle.handler \
    '{"body":"world"}'
```