{-Ayad Masud
  2/26/25
  (by entering your name and date you certify this is your
  own work and not that of any other person or service.
  Comment your functions for clarity.-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use lambda-case" #-}

import System.IO
import Data.Maybe
import Data.List (partition)
import Control.Monad

-- Book structure: (Title, Author, Availability)
type Book = (String, String, Bool)

-- Function to read books from the filew
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

-- Utility function to extract the third element of a tuple
thd3 :: (a, b, c) -> c
thd3 (_, _, x) = x

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
main :: IO ()
main = do
    putStrLn "Welcome to the library system!"
    bookList <- readBooks "./library.txt"

    let loop books = do
            putStrLn "\n1. List Available Books"
            putStrLn "2. Check Out a Book"
            putStrLn "3. Return a Book"
            putStrLn "4. Exit"
            putStrLn "Enter your choice: "
            choice <- getLine
            case choice of
                "1" -> do
                    listAvailableBooks books
                    loop books
                "2" -> do
                    putStrLn "\nEnter the title of the book you want to check out: "
                    title <- getLine
                    let (found, rest) = partition (\(t, _, _) -> t == title) books
                    if null found
                        then putStrLn "\nBook not found!" >> loop books
                        else do
                            let (book:_) = found
                            if not (thd3 book)
                                then putStrLn "\nBook is already checked out!" >> loop books
                                else do
                                    newBookList <- checkOutBook title books
                                    loop newBookList
                "3" -> do
                    putStrLn "\nEnter the title of the book you want to return: "
                    title <- getLine
                    let (found, rest) = partition (\(t, _, _) -> t == title) books
                    if null found
                        then putStrLn "\nBook not found!" >> loop books
                        else do
                            let (book:_) = found
                            if thd3 book
                                then putStrLn "\nBook is already returned!" >> loop books
                                else do
                                    newBookList <- returnBook title books
                                    loop newBookList
                "4" -> putStrLn "\nGoodbye!"
                _   -> do
                    putStrLn "\nInvalid choice, please try again."
                    loop books
    loop bookList

