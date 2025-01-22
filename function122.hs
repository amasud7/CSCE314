double :: Integer -> Integer
double n = n + n


quadruple :: Integer -> Integer
quadruple n = double n + double n

square :: Int -> Int
square n = n * n

getc :: (Double, Double) -> Double
getc (a, b) = sqrt(a^2 + b^2)

inRange :: (Int, Int, Int) -> Bool -- alternate way of writing Int -> Int -> Int -> Bool
inRange (min, max, x) = x >= min && x <= max -- inRange min max x

sumList :: [Int] -> Int
sumList [] = 0
sumList (x : xs) = x + sum xs
-- Integer > Int
