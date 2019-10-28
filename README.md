# Spring Cloud Function: SpringAwsCloudIntToWord using AWS Adapter

## Exposed Function as API Endpoint using AWS API Gateway
`IntToWordConverter.java` is exposed as function to the AWS Request Handler using `SpringBootRequestHandler`. The response object is a String.

`IntToWordRequest` the input object has number and language fields

`InttoWordConverter parameterisation: RequestHandler<IntToWordRequest, String>`


### Maven Build 
Using command
`mvn package`

### Create the Lambda

Create the Lambda Function <function_name> using AWS Lambda Management console. 

###  Deployment
Upload the jar named 
`spring-cloud-function-inttoword-0.0.1-SNAPSHOT-aws.jar`

### Test with curl

```curl -d '{"number": 23873636, "lang": "DE"}' -H "Content-Type: application/json" -X POST https://8jy0qkpypj.execute-api.us-east-1.amazonaws.com/prod/spring-cloud-function-lambda```

### Test with aws lambda

Create a test file, <test_file> with exemplary JSON format contents

```{"number": 23873636, "lang": "DE"} ```

### Send the request 
```
aws lambda invoke --function-name <function_name> --payload fileb://<test_file> response.txt
```
response.txt will have the result.

E.g., for the exemplary case the computation result equals:-

`dreiundzwanzig millionen undsiebzigundachthundertdreiunddreißigundsechshundert`

### Important Notes:

`It is necessary to clone and install the IntegerToWordService dependency into Maven repository`

### [Dependency Clone URL](https://github.com/des2412/IntToWordService.git)

`IntToWordService converts any positive number in the range [0-Integer.MAX] for a lang in the set [NL, UK, DE, FR]`


