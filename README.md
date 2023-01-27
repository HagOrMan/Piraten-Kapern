# A1 - Piraten Karpen

  * Author: Kyle Hagerman
  * Email: hagermak@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode, use a trace value of 0 if you want trace disabled, 1 if enabled.
  * Choose 'combo' or 'random' for the player arguments:
    * `mvn -q exec:java -Dexec.args="trace player1 player2"` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery, use a trace value of 0 if you want trace disabled, 1 if enabled.
  * Choose 'combo' or 'random' for the player arguments:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar trace player1 player2` 


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
| x   | F02 | Roll eight dices  |  D (F01) | 1/22/23  | 1/22/23 |
| x   | F03 | Play 42 games as a simulation  | D | 1/22/23  | 1/22/23 |
| x   | F04 | end of turn with three skulls | D | 1/22/23 | 1/22/23 |
| x   | F05 | Player keeping random dice at their turn | D (F02) | 1/22/23 |  1/22/23 |
| x   | F06 | Score points: count number of gold coins and diamonds, multiply by 100 | D (F04) | 1/22/23 | 1/22/23 |
| x   | F07 | Two Players who use the same strategy | D (F05) | 1/22/23 | 1/22/23 |
| x   | F08 | At the end of a simulation, print percentage of wins per player | D (all) | 1/22/23 | 1/22/23 |
|     | F09 | Combo multiple cards of a kind for scoring points | D | 1/25/23 | 1/25/23 |
|     | F10 | User can choose what kind of strategy to use | D | 1/25/23 | 1/25/23 |
|     | F11 | User can choose to play as a random or combo player | D | 1/25/23 | 1/25/23 |
|     | F12 | User will draw a card at the start of each turn | D | 1/26/23 | 1/27/23 |
|     | F13 | User will have card effects applied to their turn | P |  |  |
|     | F14 | User choosing combo player will have them prioritize sea battles when drawn | P |  |  |
| ... | ... | ... | ... | ... | ... |

