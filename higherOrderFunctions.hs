
double :: Int -> Int
double n = n + n

triple :: Int -> Int
triple x = x + x + x

-- this is the higher order function that applies a function to a number
applyFunction :: (Int -> Int) -> Int -> Int -- accepts as its first input a funtion (Int->Int) and second input an Int and returns an Int
applyFunction x = x

-- define a function that returns another function
multiplyBy :: Int -> (Int -> Int)
multiplyBy n x = n * x -- n is the first input given to the function (multiplyBy 6), x is the second input given to the function

-- composition function
compisitionFunction :: Int -> Int
compisitionFunction = triple.double -- this will do double then triple on wtv input to compisitionFunction

-- A curried function
greet :: String -> String -> String
greet title name = "Howdy, " ++ title ++ " " ++ name ++ "!"

main :: IO ()
main = do

    -- higher order function applies a function to an int
    print(applyFunction double 77)
    print(applyFunction triple 33)

    -- an example of a function that returns a function
    let multiplyBy6 = multiplyBy 6
    print(multiplyBy6 4)

    -- composition function
    print(compisitionFunction 5)

    -- lambda functions/expression --> good to use if u dont need a fully built out function for it and only want to use it a couple times
    let square x = x * x -- basically an inline built function
    print(square 5)

    -- lambda function applied to a list
    let numbers = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
    let squares = map (\x -> x*x) numbers -- in the () is the lambda function
    print squares

    let filterEven = filter even
    print(filterEven numbers)

    -- Application of curried function (you can see how it applies to part of the string and then you can add the rest)
    let greetMr = greet "Mr."
    print(greetMr "Masud")
    print(greet "Mr." "Ayad")

