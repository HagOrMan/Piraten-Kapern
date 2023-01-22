# A1 - Piraten Karpen

  * Author: Kyle Hagerman
  * Email: hagermak@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * A feature is done if it properly aligns with the business logic, correctly following its description while using the rulebook for specifics on how that feature should work. The code should be commented, and run and compile properly on maven too. It should pass testing if applicable. 

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  D | 01/01/23 | 1/21/23 |
| x   | F02 | Roll eight dices  |  D (F01) | 1/22/23  | 1/22/23
| x   | F03 | Play 42 games as a simulation  |  P  |   |
| x   | F04 | end of turn with three skulls | P | |
| x   | F05 | Player keeping random dice at their turn | B (F02) | | 
| x   | F06 | Score points: count number of gold coins and diamonds, multiply by 100 | B (F04) | | 
|     | F07 | Two Players who use the same strategy | B (F05) | |
|     | F08 | At the end of a simulation, print percentage of wins per player | B (all) | | 
| ... | ... | ... |

