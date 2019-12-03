# Spring Cloud Function: SpringAwsCloudIntToWord access through API Gateway

## Synopsis

`IntToWordConverter` is the handler that is referenced by AWS Lambda function named awslambdainttoword. 

`Response and request are JSON formatted.`

`IntToWordRequest` models the request object.

`IntToWordConverter` delegates to component `ConversionDelegate`.

Compliant with Java 8 specification.

### Maven Build 

`mvn package` generates artifact `spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar`

### Lambda Function Creation in AWS


`aws lambda create-function` \
`--function-name "awslambdainttoword"`\
`--runtime "openjdk11"` \
`--role "arn:aws:iam::142532442303:role/myservice-dev-us-east-1-lambdaRole"`\
`--handler "org.desz.serverless.functions.IntToWordConverter"`\
` --timeout 5`\
` --memory-size 256`\
` --zip-file "fileb://target/spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar"`


`role must be created for IAM user with permissions policies`

 `-AWSLambdaFullAccess` \
`-AmazonAPIGatewayInvokeFullAccess`\
 `-AmazonAPIGatewayAdministrator`\
 `-AWSLambdaExecute`
 
### AWS Amplify

A Typescript React application has been deployed.

 `API Gateway resource`
[https://master.d18teuogf78dgr.amplifyapp.com/](https://master.d18teuogf78dgr.amplifyapp.com/)

### API Gateway


It can also be tested in using curl, i.e.,

`curl -d '{"number": 23873636, "lang": "DE"}' -H "Content-Type: application/json" -X POST https://dvmkfdwpb1.execute-api.us-east-2.amazonaws.com/dev/conversion
`
The above command returns response

`dreiundzwanzig Millionen achthundertdreiundsiebzigtausendsechshundertsechsunddreißig`

### Notes

`IntToWordService dependency converts any positive parameter ['number'] in the range [0-Long.MAX] for parameter ['lang' - NL, UK, DE, FR]. It is available on JitPack.io.`


