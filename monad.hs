{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use >=>" #-}
import Data.Maybe (fromMaybe)

{- Monad Examples -}

-- sequencing IO action
greetUser :: IO ()
greetUser = 
    putStr "Enter your name: " >>
    getLine >>= \name ->
    putStrLn ("Hello, " ++ name ++ "!")

-- sequencing computations with maybe monad
safeDivide :: Double -> Double -> Maybe Double
safeDivide _ 0 = Nothing
safeDivide x y = Just (x / y)

computeDivision :: Double -> Double -> Double -> Maybe Double
computeDivision x y z = do
    result1 <- safeDivide x y
    safeDivide result1 z

-- encapsulating error handling with Either monad
safeDivide' :: Double -> Double -> Either String Double -- (either uses Left and Right, Left being error, right being the other action)
safeDivide' _ 0 = Left "Division by zero error."
safeDivide' x y = Right (x / y)

compute :: Double -> Double -> Double -> Either String Double
compute x y z = do
    result <- safeDivide' x y
    safeDivide' result z

safeSqrt :: Double -> Maybe Double
safeSqrt x | x < 0 = Nothing -- for x when x < 0
           | otherwise = Just (sqrt x)

complexOperation :: Double -> Double -> Double -> Maybe Double
complexOperation a b c = 
    safeDivide a b >>= \divResult -> -- bind safeDivied a b to variable divResult and send it to
    safeSqrt divResult >>= \sqrtResult ->
    safeDivide sqrtResult c >>= \finalResult ->
    return finalResult

printResult :: Maybe Double -> IO ()
printResult result = putStrLn $ "Result: " ++ show (fromMaybe 0 result)

main :: IO ()
main = do
    greetUser

    putStrLn "Compute"
    print $ computeDivision 100 20 5
    print (computeDivision 100 0 5)

    putStrLn "Complex Operation"
    print $ complexOperation 100 20 2
    print $ complexOperation 40 (-50) 20
    print $ complexOperation 40 50 0
    print $ complexOperation 0 (-40) 50
    print $ complexOperation 50 35 2

    putStrLn "Complex Operation without Just"
    printResult $ complexOperation 100 20 2
    print $ complexOperation 40 (-50) 20
    print $ complexOperation 40 50 0



