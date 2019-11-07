# Spring Cloud Function: SpringAwsCloudIntToWord using AWS Adapter

## Exposed Function as API Endpoint using AWS API Gateway
`IntToWordConverter.java` is exposed as function to the AWS Request Handler using `SpringBootRequestHandler`. The response object is a String.

`IntToWordRequest` the input object has number and language fields

`InttoWordConverter parameterisation: RequestHandler<IntToWordRequest, String>`


### Maven Build 
Using command
`mvn package`
 to build `spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar`

### Create the Lambda

`aws lambda create-function --function-name "awslambdainttoword" --runtime "openjdk11" --role "arn:aws:iam::142532442303:role/myservice-dev-us-east-1-lambdaRole" --handler "org.desz.serverless.functions.IntToWordConverter" --timeout 5 --memory-size 256 --zip-file "fileb://target/spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar"`




### Test API Gateway

'awslambdainttoword' function is now deployed and an API has been deployed at URL:-
[Api Gateway](https://ilrl508i9l.execute-api.eu-west-2.amazonaws.com/dev)

`curl -d '{"number": 23873636, "lang": "DE"}' -H "Content-Type: application/json" -X POST https://ilrl508i9l.execute-api.eu-west-2.amazonaws.com/dev`

returns response

`dreiundzwanzig Millionen achthundertdreiundsiebzigtausendsechshundertsechsunddreißig`

### Notes:

`IntToWordService dependency converts any positive parameter ['number'] in the range [0-Integer.MAX] for parameter ['lang'] in set [NL, UK, DE, FR]. It is available on JitPack.io.`


