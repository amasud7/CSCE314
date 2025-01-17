double :: Int -> Int
double n = n + n

square :: Int -> Int
square n = n * n

cubed :: Int -> Int
cubed n = n * n * n

main = do
    putStrLn "5 doubled is: "
    print(double 5)

    putStrLn "5 squared is: "
    print(square 5)

    putStrLn "5 cubed is: "
    print(cubed 5)