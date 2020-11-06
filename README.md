# README

### Introduction

Created is a simple Java project that can read request from files.
The request reads from wo files that are located in src/resources.
One files is auth.txt that is used the header of the request and contain the authorization key, the other file  sale_transaction_ok.json contains the body of the request.

### Setup

After the repository is cloned and install we issue the following command to start it.
```
$ bundle exec rails s -p 3001
```

### Starting the Project

Clone the repository and load it in any IDE as Java project.
Once the project is build you can start it from the main directory .
After the start you will be prompt to enter the file names for the authorization and the body that are located in the src/resources folder
also you should add the expected response code from the operation. 

##### Example command:

```
payment -auth auth.txt -body sale_transaction_ok.json -response 200
```
 
If the test is successful you will receive the following message:

```
Your test is SUCCESSFUL!
```

If the test is not successful you will receive the following message:

```
Your test is WRONG!
```


Also there is validation that check validity of the command and if the command is incorrect you will receive the following message:

```
Invalid command parameters, please try again!
``` 
 