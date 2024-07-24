# Live Football World Cup Scoreboard

## Overview
The Live Football World Cup Scoreboard is a simple library that provides functionality to manage live football matches. It allows starting new matches, updating scores, finishing matches, and retrieving a summary of ongoing matches ordered by their total score and start time.

## Features
- **Start a new match:** Initialize a match with two teams and an initial score of 0-0.
- **Update score:** Update the score of an ongoing match with values for home and away teams.
- **Finish match:** Finish an ongoing match and remove it from the scoreboard.
- **Get summary:** Retrieve a summary of ongoing matches ordered by their total score. Matches with the same total score are ordered by the most recently started match.

## Design and Implementation
- **In-memory storage:** The library uses in-memory storage (HashSet) to manage active matches.
- **Object-Oriented Design:** Adheres to clean code principles and aims to follow SOLID principles where applicable.
- **Interfaces:** Interfaces are not used in this implementation to keep the solution simple and focused on the current requirements. This decision is based on the current project scope. Interfaces should be added in future iterations when the codebase is planned to grow and more complex functionality is needed.
- **Thread Safety:** Thread safety is not implemented due to the lack of requirements specifying concurrent access. Future iterations should consider adding thread safety if needed.
- **Test Driven Development:** The library is developed using Test Driven Development (TDD) to ensure that the code is testable and reliable.

## Installation
1. **Clone the repository:**
    ```bash
    git clone https://github.com/vburmus/scoreboard-sportradar
    ```
2. **Navigate to the project directory:**
    ```bash
    cd scoreboard-sportradar
    ```

3. **Build the project:** The Maven Wrapper is included in the project to provide a consistent build environment. You can use it to build the project without requiring a global Maven installation.
    ```bash
    ./mvnw clean install
    ```
   This command will build the project, run tests and package it as a JAR file, which can be found in the `target` directory.
4. **Include JAR as a library:**
    If needed, you can include the generated JAR file as a library in your project to use the Live Football World Cup Scoreboard functionality.
