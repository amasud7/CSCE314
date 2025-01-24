{-Ayad Masud
    1/24/25
-}

{- Write the functions sumList
                       doubleSum
                       sumEvenNumbers
                       SumDoubleEvenNumbers
Then, use ghci to test each function
When ready, compile this program and upload
your code and paste your output to the questions below. -}

sumList :: [Int] -> Int
sumList [] = 0
sumList (x : xs) = x + sum xs

doubleSum :: [Int] -> Int
doubleSum [] = 0
doubleSum xs = 2 * sumList xs

sumEvenNumbers :: [Int] -> Int
sumEvenNumbers [] = 0
sumEvenNumbers xs = sumList (filter even xs)

sumDoubleEvenNumbers :: [Int] -> Int
sumDoubleEvenNumbers [] = 0
sumDoubleEvenNumbers xs = doubleSum (filter even xs)

main :: IO ()
main = do
  let numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  print (sumList numbers)               -- Output: 55
  print (doubleSum numbers)             -- Output: 110
  print (sumEvenNumbers numbers)        -- Output: 30
  print (sumDoubleEvenNumbers numbers)  -- Output: 60