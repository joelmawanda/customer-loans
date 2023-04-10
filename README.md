# Customer-Loans-API
This API provides information about a customer’s loan status. The user must first authenticate in order to use it. After the user has been authenticated successfully, they can query for a customer's loans using the customer's account number as a parameter. The account number is validated first (against a list of account numbers).
If it is invalid, an appropriate error response is returned. If it is valid, the customer’s loan status is ascertained by checking a list of the current loans.
If a customer has one or more outstanding loans, a response containing the details of these outstanding loans is retruned. If a customer has no outstanding loans, the response is a simple “no loan found” message with an appropriate response code.

# Getting started
This API is developed in spring boot and the reason is because spring boot has an emebedded Tomcat server therefore no need to configure one, It is opinionated thus helping developers add and customise starter dependencies saving thus developers' time, it also allows for dependency injections, it also has a provision to run jars independently, presence of spring security.
# Prerequisites
  •	Docker
# Clone the repository
git clone https://github.com/joelmawanda/customer-loans.git
# Run the application
  1. **Docker build -t app .**
  2. **Docker run -p 8080:8080 app**
# Verify the application is running:
Once the container is running, verify that the application is running by opening a web browser and navigating to **`http://localhost:8080`** or by using a tool such     as `curl`

## License
This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details.





