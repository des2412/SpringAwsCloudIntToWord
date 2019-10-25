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

### Test

Create a test file, <test_file> with exemplary contents

```{"number": 23873636, "lang": "DE"} ```

### Send the request 
```
aws lambda invoke --function-name <function_name> --payload fileb://<test_file> response.txt
```
response.txt will have the computation result.
