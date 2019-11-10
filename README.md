# Spring Cloud Function: SpringAwsCloudIntToWord using AWS Adapter

## Exposed Function as API Endpoint using AWS API Gateway
`IntToWordConverter.java` is exposed as function to the AWS Request Handler using `SpringBootRequestHandler`. The response object is a String. 

`IntToWordRequest` models the request object.

Response and request are JSON.

`InttoWordConverter parameterisation: RequestHandler<IntToWordRequest, String>`


### Maven Build 

`mvn package` generates artifact `spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar`

### Create the Lambda

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

### API Gateway

 `API Gateway resource `
[https://master.d18teuogf78dgr.amplifyapp.com/](https://master.d18teuogf78dgr.amplifyapp.com/)

`curl -d '{"number": 23873636, "lang": "DE"}' -H "Content-Type: application/json" -X POST https://dvmkfdwpb1.execute-api.us-east-2.amazonaws.com/dev/conversion
`
returns response

`dreiundzwanzig Millionen achthundertdreiundsiebzigtausendsechshundertsechsunddreißig`

### Notes

`IntToWordService dependency converts any positive parameter ['number'] in the range [0-Integer.MAX] for parameter ['lang'] in set [NL, UK, DE, FR]. It is available on JitPack.io.`


