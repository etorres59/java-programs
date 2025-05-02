package randomNumberGuessingGame.java;

// Evan Torres
import java.util.Random;
import java.util.Scanner;

public class RandomNumberGuessingGame {
	private static int _randomNumber = 0;
	private static boolean _isGuessCorrect = false;
	private static int numOfGuesses = 0;

	// gets the users name
	public static String getUserName(Scanner input) {
		// prompts User for their name
		System.out.print("Please enter your first name: ");
		// set next string input to the variable playerName
		String playerName = input.nextLine();
		return playerName;
	}

	// Asks the user if they want hints
	public static boolean askForHints(Scanner input) {
		// prompts for hint activation
		System.out.print("Would you like to activate hints for this game? (yes or no): ");
		// set the user input to variable activateHints
		String activateHints = input.nextLine().trim().toLowerCase();
		// returns true if user enters yes or y
		return activateHints.equals("yes") || activateHints.equals("y");
	}

	// Decides what hint to give based on distance from the computers number
	public static void giveHint(int userGuess, int computersNumber, String playerName) {
		// Sets difference variable to equal the absolute (non negative) difference between
		// the Users guess and the computers number
		int difference = Math.abs(userGuess - computersNumber);
		// tiers of hints based on difference
		if (difference == 0) {
			// Do nothing if guess is correct
		} else if (difference <= 2) {
			System.out.printf("%s, you're Hot! 🔥\n", playerName);
		} else if (difference <= 5) {
			System.out.printf("%s, you're Warm. 🌡️\n", playerName);
		} else if (difference <= 10) {
			System.out.printf("%s, you're Cold. 😐\n", playerName);
		} else if (difference <= 20) {
			System.out.printf("%s, you're Freezing! 🥶\n", playerName);
		} else {
			System.out.printf("%s, not even close... 🤣\n", playerName);
		}
	}

	// creates an int method to generate a random number from 1-100
	public static int getComputersNumber() {
		// initializes the int variable computersNumber
		int computersNumber;
		// creates a new random
		Random num = new Random();
		// sets computersNumber to the randomly generated number (1-100)
		computersNumber = (int) (num.nextInt(100) + 1);
		_randomNumber = computersNumber;
		return computersNumber;
	}

	// Regular Expression to validate the number input from 1 to 100
	public static int getValidIntegerInput(Scanner input, String playerName) {
		String numberRange = "^(100|[1-9][0-9]?)$"; // Regular Expression (regex) for matches from 1-100 only
		String userInput;

		while (true) {
			userInput = input.nextLine();
			if (userInput.matches(numberRange)) {
				return Integer.parseInt(userInput);
			} else {
				System.out.printf("Invalid Input, %s. Please input an integer from 1-100: ", playerName);
			}
		}
	}

	// Handles the comparison between the guessed number and the computer number and returns boolean
	public static boolean guessedCorrectly(int guess) {
		if (guess == _randomNumber) {
			return true;
		} else {
			return false;
		}
	}

	// Handle the game results announcement (win or lose)
	public static void displayGameResults(boolean isCorrect, String playerName) {
		if (isCorrect) {
			System.out.printf("Congratulations! You win! The number was %d.\n", _randomNumber);
		} else {
			System.out.printf("Sorry %s, you're out of guesses. The number was %d. You lose!\n", playerName, _randomNumber);
		}
	}

	// Method to Play again or quit
	public static boolean playAgain(Scanner input, String playerName) {
		System.out.printf("\nWould you like to play again, %s? (yes or no): ", playerName);
		String answer = input.nextLine().trim().toLowerCase();
		// returns true if user enters yes or y
		return answer.equals("yes") || answer.equals("y");
	}

	public static void main(String[] args) {
		// Creates a keyboard user input
		Scanner input = new Scanner(System.in);
		boolean replayGame;

		do {
			// sets computersNumber variable to the number returned by the getComputersNumber method
			getComputersNumber();

			// Get player name
			String playerName = getUserName(input);

			// Ask to enable hints
			boolean hintsEnabled = askForHints(input);

			// prompt user to pick a number and assigns it to the userGuess variable
			System.out.printf("%s, please pick a number from 1-100 (You have 10 guesses): ", playerName);
			// Sets user guess to the next input and calls the getValidIntegerInput method
			int userGuess = getValidIntegerInput(input, playerName);

			// if player name is Evan (not case sensitive) the computers number = the number you entered
			if (playerName.equalsIgnoreCase("Evan")) {
				_randomNumber = userGuess;
			}

			// sets counter to 9 (for guess tracking)
			int counter = 9;

			// while the counter is greater than or equal to 0
			while (counter >= 0) {
				// set number of guesses to whatever counter is
				numOfGuesses = counter;

				// checks if the numbers match and assigns a boolean to _isGuessCorrect
				_isGuessCorrect = guessedCorrectly(userGuess);

				// if the numbers match or the user uses all the guesses break out of while loop
				if (_isGuessCorrect || numOfGuesses == 0) {
					break;
				}

				// if those conditions aren't met, give hint if enabled
				if (hintsEnabled) {
					giveHint(userGuess, _randomNumber, playerName);
				}

				// prompt again
				System.out.printf("Enter another number (%d guess[es] left): ", numOfGuesses);
				userGuess = getValidIntegerInput(input, playerName);

				// at the end of each loop remove one guess
				counter--;
			}

			// game results
			displayGameResults(_isGuessCorrect, playerName);

			// ask to play again
			replayGame = playAgain(input, playerName);

		} while (replayGame);

		System.out.println("Thanks for playing! 👋");
		input.close(); // stops resource leak
	}
}
