# customer-loans-API
This API provides information about a customer’s loan status. You must first authenticate in order to use it. After the user has been authenticated successfully, they can query for a customer's loans using the customer's account number as a parameter. The account number is validated first (against a list of account numbers).
If it is invalid, an appropriate error response is returned. If it is valid, the customer’s loan status is ascertained by checking a list of current loans.
If a customer has one or more outstanding loans, a response containing the details of these outstanding loans is retruned. If a customer has no outstanding loans, the response is a simple “no loan found” message with an appropriate response code.

# Getting started
# Prerequisites
  •	Java 8 or later
  •	Git
# Clone the repository
git clone https://github.com/yourusername/rank-based-command-execution.git 
# Run the application
  1.	Open a terminal and navigate to the cloned repository.
  2.	Compile the Java classes:
      **javac *.java** 
  3.	Start the server:
    **java CommandController** 
    This will start the server on port 9090.
# Sending commands
You can send commands to the server using a TCP client, such as telnet or nc. The commands should be in the following format:
    `<rank>: <command>` 
For example, to send a command with rank 2, you can run the following command in a separate terminal window:
    **echo "2: my command" | nc localhost 9090** 
This will send the command my command to the server, and it will be executed by the client with rank 2 if there are no higher ranked clients.
# Stopping the server
To stop the server, you can send the following command:
    **stop** 
This will stop the server and terminate the application.
## License
This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details.





