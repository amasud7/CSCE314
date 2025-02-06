{-
examples of recursion and pattern matching
-}


sumRecursive :: Int -> Int
sumRecursive 0 = 0
sumRecursive n = n + sumRecursive (n - 1)

sumTailRecursive :: Int -> Int
sumTailRecursive n = sumHelper n 0 -- tail recursive

sumHelper :: Int -> Int -> Int
sumHelper 0 acc = acc -- base case
sumHelper n acc = sumHelper (n - 1) (n + acc)

-- pattern matching to describe a list
describeList :: [a] -> String
describeList [] = "This is an empty list."
describeList [_] = "This is a single-element list."
describeList [_, _] = "This is a 2 element list."
describeList (_:_:xs) = "This is a longer list."

indexList :: [a] -> [(Int, a)]
indexList xs = zip [1..] xs

mergeSentences :: [String] -> [String] -> [String]
mergeSentences subject actions = [s ++ " " ++ a | (s, a) <- zip subject actions]


main :: IO ()
main = do
    -- putStrLn " start non tail sum "
    -- print (sumRecursive 1000000)

    -- putStrLn " start tail sum "
    -- print (sumTailRecursive 1000000)

    print $ describeList []
    print $ describeList [1]
    print $ describeList [1, 2]
    print $ describeList [1, 2, 3]
    print $ describeList [1, 2, 3, 4]

    print $ indexList ["apples", "oranges", "mangoes"]
    print $ mergeSentences ["Ayad", "Masud"] ["Runs", "Swims"]