# UPS Module

The Axon Ivy [UPS connector](https://developer.ups.com/catalog) enables users to integrate UPS services seamlessly into any business process. 

This connector:

- Empowers you with full access to the OpenAPI UPS API Catalog
- Features everyday use cases such as tracking packages, obtain shipping rates, and validating addresses

Explore the [API catalog](https://developer.ups.com/catalog) to identify which APIs align best with your business needs.

## Demo
### Get tracking information
This service is used to retrieve package information.
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Get Tracking**

![Alt text](images/image-1.png)

3. Fill in your tracking number

![Alt text](images/image-2.png)

4. Click **Search** button to get all information of the package

![Alt text](images/image-3.png)

### Pickup Creation
This service is used to schedule pickups.
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Pickup Creation**

![Alt text](images/image-5.png)

3. Fill in required fields

![Alt text](images/image-4.png)

4. Click **Process** button to schedule pickups

![Alt text](images/image-12.png)

### Pickup cancel
This service is used to cancel previously scheduled pickups.
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Pickup Creation**

![Alt text](images/image-8.png)

3. Fill in required fields

![Alt text](images/image-7.png)

4. Click **Cancel** button to finish

### Address validation
This service is utilized to verify addresses against the United States Postal Service database of valid addresses in the U.S.
1. Login to the Axon Ivy Portal
2. On the Process List page, click on **Validate Address**

![Alt text](images/image-9.png)

3. Fill in address info

![Alt text](images/image-10.png)

4. Click **Validate** button

## Setup
1. Go to https://developer.ups.com, login with your user or create a new UPS account.
2. Create an application on UPS
3. Once your application is created, the **Client ID** and **Client Secret** are generated and can be used to obtain an access token for authorizing your API requests
4. Configure the following variables in your project:
```
@variables.yaml@
```
> [!NOTE]
> This variable path `ups-connector` is renamed to `upsConnector` from 13.1.
