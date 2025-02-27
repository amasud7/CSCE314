{-Ayad Masud
  2/26/25
  (by entering your name and date you certify this is your
  own work and not that of any other person or service.
  Comment your functions for clarity.-}

import System.IO
import Data.Maybe
import Control.Monad

-- Book structure: (Title, Author, Availability)
type Book = (String, String, Bool)

-- Function to read books from the file
readBooks :: FilePath -> IO [Book]
readBooks fileName = do
    contents <- readFile fileName
    return $ map parseBook (lines contents)
  where
    parseBook line = let [title, author, available] = wordsWhen (== ';') line
                     in (title, author, read available :: Bool)

-- Function to display books that are available
listAvailableBooks :: [Book] -> IO ()
listAvailableBooks books = do
    putStrLn "Available Books:"
    forM_ books $ \(title, author, available) -> 
        when available $ putStrLn $ title ++ " by " ++ author

-- Function to check out a book (set availability to False)
checkOutBook :: String -> [Book] -> IO [Book]
checkOutBook title books = do
    let (found, rest) = partition (\(t, _, _) -> t == title) books
    if null found
        then putStrLn "Book not found!" >> return books
        else do
            let (book:_) = found
            putStrLn $ "Checked out: " ++ title
            return $ map (\book' -> if book' == book then (fst3 book', snd3 book', False) else book') books

-- Function to return a book (set availability to True)
returnBook :: String -> [Book] -> IO [Book]
returnBook title books = do
    let (found, rest) = partition (\(t, _, _) -> t == title) books
    if null found
        then putStrLn "Book not found!" >> return books
        else do
            let (book:_) = found
            putStrLn $ "Returned: " ++ title
            return $ map (\book' -> if book' == book then (fst3 book', snd3 book', True) else book') books

-- Utility function to extract the first element of a tuple
fst3 :: (a, b, c) -> a
fst3 (x, _, _) = x

-- Utility function to extract the second element of a tuple
snd3 :: (a, b, c) -> b
snd3 (_, x, _) = x

-- Utility function to split a string by a delimiter
wordsWhen :: (Char -> Bool) -> String -> [String]
wordsWhen p s = case dropWhile p s of
    "" -> []
    s' -> w : wordsWhen p s''
          where (w, s'') = break p s'

{-
ToDo:
Complete any functions,
Write any additional functions,
Complete the Main
-}