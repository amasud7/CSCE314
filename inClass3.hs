{-
Ayad Masud
2/7/25

fill in the functions below, there is no need to change main.
-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use foldr" #-}

import System.IO

-- Define the type for family data  (done for you)
type Family = (String, String, Int)

-- Read the file and parse it into a list of Family tuples  (done for you)
parseFamilyData :: String -> [Family]
parseFamilyData content =
    map parseLine (lines content)
    where
        parseLine line = let parts = split ',' line
                         in (trim (parts !! 0), trim (parts !! 1), read (trim (parts !! 2)) :: Int)

-- Utility function to split a string by a delimiter (Done for you -used to read in file)
split :: Char -> String -> [String]
split _ [] = []
split delim str =
    let (first, rest) = break (== delim) str
    in first : case rest of
        [] -> []
        (_:xs) -> split delim xs

-- Trim whitespace from a string (Done for you -used to read in file)
trim :: String -> String
trim = unwords . words

-- 1. Count the number of families
countFamilies :: [Family] -> Int
-- Write this function
countFamilies [] = 0
countFamilies (x:xs) = 1 + countFamilies xs

-- 2. Compute the total number of family members
totalMembers :: [Family] -> Int
--write this function
totalMembers [] = 0
totalMembers (x:xs) = (\ (_,_,z) -> z) x + totalMembers xs

-- 3. Find the family with the most members
largestFamily :: [Family] -> (String, Int)
--write this function
largestFamily [] = ("", 0)
largestFamily families = foldl maxFamily ("", 0) families
    where
        maxFamily :: (String, Int) -> Family -> (String, Int)
        maxFamily (maxName, maxSize) (name, _, size) = 
            if size > maxSize then (name, size) else (maxName, maxSize) 


-- 4. Filter families with more than a given number of members
filterLargeFamilies :: Int -> [Family] -> [Family]
--write this function
filterLargeFamilies _ [] = []
filterLargeFamilies n (x:xs) = if (\ (_,_,z) -> z) x > n then x : filterLargeFamilies n xs else filterLargeFamilies n xs

-- 5. Extract and format addresses
formatAddresses :: [Family] -> [String]
--write this function
formatAddresses [] = []
formatAddresses xs = map (\ (x,y,_) -> x ++ " " ++ y) xs

-- Main function to read the file and execute the functions
main :: IO ()
main = do
    handle <- openFile "families.txt" ReadMode
    content <- hGetContents handle
    let familyData = parseFamilyData content

    putStrLn $ "Number of families: " ++ show (countFamilies familyData)
    putStrLn $ "Total number of people: " ++ show (totalMembers familyData)
    putStrLn $ "Largest family: " ++ show (largestFamily familyData)
    putStrLn $ "Families with more than 3 members: " ++ show (filterLargeFamilies 3 familyData)
    putStrLn $ "Addresses: " ++ show (formatAddresses familyData)

    hClose handle