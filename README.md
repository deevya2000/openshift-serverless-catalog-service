# catalog-service 
Catalog service is part of *Product Catalog* application that maintains an updated information on various Product categories and Products.

This project uses Quarkus, the Supersonic Subatomic Java Framework (Reference: https://quarkus.io/), REST Easy for APIs and Kafka as messaging service. The data is persisted in postgres database. 

## Application Architecture

Catalog service holds information of Products and Product Categories. When price of an item in catalog service is updated the data is updated in postgresql database. This also sends a message to kafka topic `product-price-updated`. Notification-service, which is subscribed to this topic, sends an email notification to users interested in the product via mailer service.

<img src="/src/images/serverless-architecture2.png" alt="application architecure">


## Deploying the application to openshift
Please refer to openshift-pipeline folder for jenkins file and readme text file with instructions in order to deploy the service on openshift.

Prerequisites:
-------------
1. Create a namespace 'serverless-demo' on openshift cluster, if not already present.
2. Verify that jenkins is available in the namespace.
  If unavailable, install jenkins with the below command in the namespace, and wait a few moments for it to be up and running.
  
  ````oc new-app jenkins-persistent -p ENABLE_OAUTH=false -e JENKINS_PASSWORD=openshiftpipelines -n serverless-demo````

3. Ensure postgresql database is available and configured. Details of database can be found in applications.properties of this repo.
Database can be added on openshift cluster topology of developer view.

4. Kafka Operators (RedHat Integration AMQ) is installed and configured on the cluster. Create a kafka cluster with name my-cluster and default configuration. Once Kafka cluster is available create a topic `product-price-updated` at this kafka instance.

Pipeline steps
--------------
Step 1: Create a new applicaion with name openshift-serverless-catalog-service in namespace serverless-demo with following command.
````oc new-app java~https://github.ibm.com/100mc/openshift-serverless-catalog-service.git -n serverless-demo --as-deployment-config=true````

Step 2: This creates a route to access the service externally
````oc expose service openshift-serverless-catalog-service -n serverless-demo````

Step 3: Refer to pipeline configuration file at openshift-pipeline folder of this repo `catalog-service-pipeline.yaml`. This file will create a build config named 'catalog-service-pipeline'which would be triggered during at subsequent code commits. This file has embedded jenkins file which builds and deploys new code. 
````oc create -f catalog-service-pipeline.yaml````

Step 4: Once jenkins build is created on openshift cluster, go to Build Webhook in catalog-service-pipeline.
Select Generic: and copy webhook with secret. Paste this content in github> project > settings> webhook of your github repository.

Step 5: As you make code comits to the repository linked to pipeline above, build will be triggered.
Verify whether changes are reflect after pipeline is completed.			


## Related guides
Please refer to playbook of referece implementation for more details
https://pages.github.ibm.com/100mc/foundational-offering-playbook/ref_impl_serverless_with_quarkus.html
