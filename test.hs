main :: IO ()
main = do
    let lst = [1, 3, 5, 7, 9, 10, 15, 20]
    let lst2 = [x | x <- lst, x `mod` 3 == 0]
    print $ lst2