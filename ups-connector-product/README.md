# UPS Modules

## Description
This is a UPS modules made in Axon Ivy framework. It allows users to quickly and easily integrate with UPS Service. With this App, users can effortlessly manage their packages from UPS.
With this connector:
- Gives you full power to the OpenAPI UPS services.
- Supports you with an easy-to-copy demo implementation to reduce your integration effort.

UPS modules contains:
- ups-connector
- ups-oauth-feature
- ups-connector-demo
- ups-connector-product
- ups-connector-test

Common use cases for UPS APIs:
- Shipping and tracking packages for your business
- Obtaining shipping rates and time in transit for ecommerce
- Creating visibility applications for your customers on your platform

View the [API catalog](https://developer.ups.com/catalog) to identify which APIs meet your business needs.
We hope you enjoy and we look forward to your contributions!

## Set up

1. Go to https://developer.ups.com, login or create a new UPS account. 
2. Create an Application on UPS 
3. After creating your application, the **Client ID** and **Client Secret** are generated and can be used to obtain an access token to authorize your API requests
4. Configure the following variables in your project: 
![Alt text](image-11.png)



## Demo
### Get tracking information
This service is used to retrieve package information
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Get Tracking**
![Alt text](image-1.png)
3. Fill in your tracking number
![Alt text](image-2.png)
4. Click **Search** button to get all information of the package
![Alt text](image-3.png)

### Pickup Creation
This service is used to schedule pickups
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Pickup Creation**
![Alt text](image-5.png)
3. Fill in required fields
![Alt text](image-4.png)
4. Click **Process** button to schedule pickups
![Alt text](image-12.png)



### Pickup cancel
This service is used to cancel previously scheduled pickups
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Pickup Creation**
![Alt text](image-8.png)
3. Fill in required fields
![Alt text](image-7.png)
4. Click **Cancel** button to finish


### Address validation
This service is used to check addresses against the United States Postal Service database of valid addresses in the U.S
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Validate Address**
![Alt text](image-9.png)
3. Fill in address info
![Alt text](image-10.png)
4. Click **Validate** button
