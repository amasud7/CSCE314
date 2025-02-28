{- Friday 2/28 inclass assignment.
CSCE 314
Spring 2025
-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use >=>" #-}

import Control.Monad (when)
import Text.Read (readMaybe)

------------------------------------------------------------
-- Section 1: Maybe Monad
------------------------------------------------------------
-- This function takes a string input, attempts to convert it
-- to an integer, and then adds 10. Students should implement
-- the computation using the Maybe monad with >>= bindings.
--
-- TODO: Complete or modify the function using do-notation or (>>=)
maybeCompute :: String -> Maybe Int
maybeCompute str =
    -- Using >>= binding:
    readMaybe str >>= (\n -> Just (n + 10))

------------------------------------------------------------
-- Section 2: Either Monad
------------------------------------------------------------
-- This function takes two string inputs, converts them to integers,
-- and divides the first by the second. Proper error messages must be
-- returned if the conversion fails or if there is a division by zero.
--
-- TODO: Complete the function using the Either monad with proper error handling
eitherCompute :: String -> String -> Either String Int
eitherCompute s1 s2 = do
    x <- case readMaybe s1 of
            Just n  -> Right n
            Nothing -> Left "First input is not a valid number"
    y <- case readMaybe s2 of
            Just n  -> Right n
            Nothing -> Left "Second input is not a valid number"
    if y == 0
      then Left "Division by zero error"
      else Right (x `div` y)

------------------------------------------------------------
-- Section 3: IO Monad and Sequencing
------------------------------------------------------------
-- This interactive section uses IO monad sequencing and >>= binding.
-- Students will see how to chain IO actions and use the bind operator.
--
-- TODO: Modify or extend this function to demonstrate more IO monad operations
ioProgram :: IO ()
ioProgram = do

    -- updating the following to use Monad sequencing and >>= binding

    putStrLn "Enter a number for the Maybe computation (it will add 10):" >>
        getLine >>= \input1 ->
            case maybeCompute input1 of
                Just maybeResult -> putStrLn $ "Maybe result: " ++ show maybeResult
                Nothing -> putStrLn "Invalid input for Maybe computation"

    -- update to use Monad sequencing and >>= binding

    putStrLn "\nEnter two numbers for the Either computation (first divided by second):" >>
        putStrLn "Enter first number:" >> 
            getLine >>= \input2 ->
                putStrLn "Enter second number:" >>
                    getLine >>= \input3 ->
                        case eitherCompute input2 input3 of
                            Right eitherResult -> putStrLn $ "Either result: " ++ show eitherResult
                            Left error -> putStrLn error

    -- Demonstrate explicit usage of >>= in IO actions:
    putStrLn "\nType anything to see a demonstration of >>= binding in IO:" >>
        getLine >>= (\line -> putStrLn $ "You entered: " ++ line)

------------------------------------------------------------
-- Main Function
------------------------------------------------------------
-- TODO: Modify main if needed and ensure proper sequencing of operations.
main :: IO ()
main = do
    -- update to use Monad IO sequencing
    putStrLn "Starting Monads Assignment Program" >>
        ioProgram >> 
            putStrLn "Program Finished."