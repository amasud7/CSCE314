{-
time and foldl' example
-}

import Data.List (foldl')
import Data.Time (getCurrentTime)

-- lazy product (default Haskell behavior)
factorialLazy :: Integer -> Integer
factorialLazy n = product [1..n]

-- strict product using foldl
factorialStrict :: Integer -> Integer
factorialStrict n = foldl' (*) 1 [1..n]


main :: IO ()
main = do
    putStrLn "Enter a number to compute its factorial"
    input <- getLine
    let num = read input :: Integer
    currentTime <- getCurrentTime
    putStrLn $ "CUrrent system time: " ++ show currentTime

    putStrLn $ "Lazy factorial of " ++ show num ++ " is: " ++ show (factorialLazy num)

    currentTime <- getCurrentTime
    putStrLn $ "CUrrent system time: " ++ show currentTime

    putStrLn $ "Strict factorial of " ++ show num ++ " is: " ++ show (factorialStrict num)

    currentTime <- getCurrentTime
    putStrLn $ "CUrrent system time: " ++ show currentTime