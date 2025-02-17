-- Ayad Masud
-- 733009045
--2/15/25
import System.Random

-- generating random dice roll
generateDiceRoll :: IO Int
generateDiceRoll = do 
    roll <- randomRIO (1, 6)
    putStrLn $ "The dice rolled: " ++ show roll
    return roll

-- scoring logic
scoreLogic :: Int -> Int -> Int
scoreLogic roll guess
    | guess == roll = 5
    | guess == roll - 1 || guess == roll + 1 = 1
    | otherwise = 0

-- entering game
startingGame :: IO ()
startingGame = do
    putStrLn "Starting new game...\n"

-- exiting game
exitingGame :: IO ()
exitingGame = do
    putStrLn ""

-- reading user input per round
readUserInput :: Int -> IO ()
readUserInput n = do
    putStrLn ("--- Round " ++ show n ++ " ---")
    putStrLn "Guess the dice roll (1-6): "

-- looping through rounds (round #, total score, highest score)
gameLoop :: Int -> Int -> Int -> IO ()
gameLoop 6 totalScore highestScore = do -- block of code for when 5 rounds are done
    putStrLn $ "Game Over! Your total score is: " ++ show totalScore
    putStrLn $ "Highest score so far: " ++ show highestScore
    putStrLn "Do you want to play again? (y/n): "
    answer <- getLine
    startingGame
    playAgain answer highestScore
gameLoop n totalScore highestScore = do -- what to do when 5 rounds haven't been done yet
    
    -- get user input
    readUserInput n
    guess <- getLine
    -- roll dice
    roll <- generateDiceRoll

    -- scoring
    let score = scoreLogic roll (read guess :: Int)
    putStrLn $ "You earned " ++ show score ++ " points!\n"

    -- recursive call
    gameLoop (n + 1) (totalScore + score) (max highestScore (totalScore + score))

-- asking if you want to play again
playAgain :: String -> Int -> IO ()
playAgain answer highestScore -- passing highestscore so we can keep track across all rounds
    | answer == "y" = gameLoop 1 0 highestScore
    | otherwise = putStrLn "Thanks for playing! Goodbye!"

-- main function
main :: IO ()
main = do
    putStrLn "Welcome to the Dice Guessing Game\n"
    startingGame
    -- starting the game loop
    gameLoop 1 0 0

