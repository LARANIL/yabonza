# yabonza
Assignment from Yabonza

This code base contains following REST API endpoints.

1. Creating a new dog breed record
2. Retrieving an existing dog breed record , given the identity
3. Deleting an existing dog breed record , given the identity
4. Search a dog breed record based on the given breed name
5. Find all the existing dog breeds , page by page


## Pre-requisites
This project runs of SpringBoot , hence JAVA 1.8 must be used 
to run the project

## How To Build
This is a generic Maven project. Clone the source code and 
navigate to the root folder. Execute following commands for
building the application

1. mvn clean - For cleaning the project
2. mvn package - for packaging the project 
3. mvn spring-boot:run - for running the project during the development

mvn package - This command would package the project as a 
runnable jar file. Once the jar file is created execute the following 
command to run the project.

java -jar petdogs-0.0.1-SNAPSHOT.jar 
[make sure the path to the file name is accurate]


It must be noted that when the project is started following environmental
variables must be provided in order to access AWS S3 bucket.

export AWS_SECRET_ACCESS_KEY="[secret access key]"
export AWS_ACCESS_KEY_ID="[access key id]"


## Creating a new dog breed record

> Api Endpoing signature : `http://[host]:[port]/petdogs/dogs`
> Supported HTTP method : `Only PUT`
>
> Example Response:
>
> {
>    "message": "A new random dog breed is created.",
>    "status": "success",
>    "id": 11,
>    "breed": "schnauzer-giant",
>    "createdOn": "2019-01-21T14:05:31.621+11:00[Australia/Sydney]",
>    "accessUrl": "https://s3.amazonaws.com/petdog-bucket/3a2a36a4-65bc-4617-8cb6-c321eb52ce09.jpg"
> }


## Retrieve an existing dog breed record based on the ID

> Api Endpoing signature : `http://[host]:[port]/petdogs/dogs/{id}`
> Supported HTTP method : `Only GET`
>
> Example Response:
>
> {
>    "message": "Dog breed found.",
>    "status": "success",
>    "id": 2,
>    "breed": "dalmatian",
>    "createdOn": "2019-01-21T13:37:51+11:00[Australia/Sydney]",
>    "accessUrl": "https://s3.amazonaws.com/petdog-bucket/3ce2b612-5e39-4330-9e86-58947ceedc37.jpg"
> }

## Delete an existing dog breed record based on the ID

> Api Endpoing signature : `http://[host]:[port]/petdogs/dogs/{id}`
> Supported HTTP method : `Only DELETE`
>
> Example Response:
>
>
> {
>     "message": "Delete request is successfully executed",
>     "status": "success"
> }


## Search an existing dog breed by the breed name

> Api Endpoing signature : `http://[host]:[port]/petdogs/dogs?breedName=[breed name]`
> Supported HTTP method : `Only GET`
>
> Example Response:
>
> {
>    "message": "Found dogs for the given breed name",
>    "status": "success",
>    "dogs": [
>        {
>            "message": "Dog breed found.",
>            "status": "success",
>            "id": 3,
>            "breed": "dalmatian",
>            "createdOn": "2019-01-21T13:38:02+11:00[Australia/Sydney]",
>            "accessUrl": "https://s3.amazonaws.com/petdog-bucket/375005ce-c593-4e81-93c0-5432507cf287.jpg"
>        }
>    ]
>}


## Find all existing dog breeds , paged

> Api Endpoing signature : `http://[host]:[port]/petdogs/dogs?size=[pagesize]&page=[pagenumber]`
> Supported HTTP method : `Only GET`
>
> Note that page number starts with 0
>
> Example Response:
> 
> {
>    "message": "Dog breed names found",
>    "status": "success",
>    "breeds": [
>        "keeshond",
>        "dalmatian",
>        "bullterrier-staffordshire",
>        "elkhound-norwegian",
>        "kuvasz",
>        "hound-english"
>    ]
> }
