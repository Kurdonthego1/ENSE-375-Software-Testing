This provides a brief overview of the testing methods and strategies we used to validate the functionality of our project. Techniques such as Path Testing, Data Flow Testing, Boundary Value Analysis, Equivalence Class Testing, Decision-Based Testing, State Transition Testing, and Integration Testing

##### Testing Techniques:
- Path Testing
- Data Flow Testing
- Boundary Value Analysis
- Equivalence Class Testing
- Decision-Based Testing
- State Transition Testing
- Integration Testing
***

### Path Testing
* ![Paths](Images/pathTesting.png)
* ![Control Flow Diagram](Images/cfg.png)
When choosing a function to implement path testing, we decided it would be best to use our withdrawal function. We made sure all possible routes were evaluated, whether that be successful or unsuccessful paths. 
### Use Case Testing
* ![Case 1](Images/UseCase1.png)

* ![Case 2](Images/UseCase2.png)

As seen above, we explored and simulated two main flows the user could potentially go through with our application. This included success paths and exception conditions. The goal of this testing strategy was to validate that the operations worked in sequence, and that error handling would work as intended and provide user with feedback.