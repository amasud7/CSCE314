import Data.Maybe (fromMaybe)

-- Define shape ADT with constructor
data Shape = Circle Double
    | Rectangle Double Double
    | Triangle Double Double Double 
    deriving (Show, Read)

-- function to validate triangle
isValidTriangle :: Double -> Double -> Double -> Bool
isValidTriangle a b c = a + b > c && a + c > b && b + c > a

-- define a function to calculate the area of a shape --> maybe / just is like try catch --> maybe we got data if we did just do somethingm, else do something else
area :: Shape -> Maybe Double
area (Circle r) = Just (pi * r * r)
area (Rectangle l w) = Just (l * w)
area (Triangle a b c) 
    | isValidTriangle a b c = Just (let s = (a + b + c) / 2 in sqrt(s * (s-a) * (s-b) * (s-c)))
    | otherwise = Nothing

-- format maybe result into user friendly string
formatResult :: Maybe Double -> String
formatResult = maybe "invalid shape" show


-- get shape from user input
getShapeFromUser :: IO Shape
getShapeFromUser = do
    putStrLn "Choose a shape: (1) Circle (2) Rectangle (3) Triangle"
    shapeType <- getLine
    case shapeType of
        "1" -> do
            putStrLn "Enter radius: "
            Circle <$> readLn
        "2" -> do
            putStrLn "Enter width: "
            width <- readLn
            putStrLn "Enter length: "
            Rectangle width <$> readLn
        "3" -> do
            putStrLn "Enter side a: "
            a <- readLn
            putStrLn "Enter side b: "
            b <- readLn
            putStrLn "Enter side c: "
            Triangle a b <$> readLn
        _ -> do
            putStrLn "Invalid choice, defaulting to Circle with r = 1"
            return (Circle 1.0)

-- main function to handle user input and display results
main :: IO ()
main = do
    putStrLn "Welcome to the Shape Calculator"
    shape <- getShapeFromUser
    putStrLn $ "\nShape: " ++ show shape
    putStrLn $ "Area: " ++ formatResult (area shape)