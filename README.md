# AWS Lambda Scala Stack

* Write lambda functions in Scala.js
* Scala.js typed facades for AWS SDK and Node.js
* If using pure Scala libraries, no need to bundle NPM dependencies
* Zip CommonJS JavaScript modules
* Deploy AWS stack and zipped code assets with CDK DSL
* Generated type-safe configuration for CDK lambda code assets

### Prerequisites

```shell
npm i -g aws-cdk
cdk init
```

### Deploy

```shell
cdk deploy
```

### TODO:

* Test AWS SDK usage
* Type-safe configuration for lambda handler names (currently hardcoded, only module names are
  detected from code)
