# authentication-service
To run this application following steps need to be followed:
1. Create environment variables on your terminal or intellij:
export AWS_ACCESS_KEY=<AWS Access Key>
export AWS_ACCESS_SECRET_KEY=<AWS Access Secret>
export AWS_REGION=eu-west-2

2. Create a secret in AWS Secrets Manager and name it "secret/demo"

3. Under secret/demo, create various secrets such as:
    a. userPoolId
    b. clientId
    c. clientSecret
    
4. Start the App by executing the following command on command line:
    mvn spring-boot:run
    
5. Do a post using url: http://localhost:8080/rest/users/login
    
    
    SAMPLE payload(not the real values):
    {
        "userName": "User1",
        "password": "Password1#"
    }

    SAMPLE response:
    
    {
        "accessToken": <jwt token>
        "refreshToken": <jwt token>
    }
    
    