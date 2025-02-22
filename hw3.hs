{-Ayad Masud
  2/20/25
  (by entering your name and date you certify this is your
  own work and not that of any other person or service.
  Comment your functions for clarity.-}

--ToDo  Define an ADT (Abstract Data Type) for Shape.
--Implement polymorphism using Haskell's type classes.
--Implement methods for calculating the area and perimeter of various shapes.
--Use recursion and higher-order functions to manage a collection of shapes.
--add functionality to calculate volume


-- adt definition
data Shape = Circle Double | Rectangle Double Double Double | Triangle Double Double Double Double| Pentagon Double Double | Hexagon Double Double

-- function to validate triangle
isValidTriangle :: Double -> Double -> Double -> Bool
isValidTriangle a b c = a + b > c && a + c > b && b + c > a

-- function for area
area :: Shape -> Maybe Double
area (Circle r) = Just (pi * r * r)
area (Rectangle l w 0) = Just (l * w)
area (Triangle a b c 0)
    | isValidTriangle a b c = Just (let s = (a + b + c) / 2 in sqrt(s * (s-a) * (s-b) * (s-c)))
    | otherwise = Nothing
area (Pentagon x _) = Just (1/4 * sqrt(5*(5+(2*sqrt 5))) * x^2)
area (Hexagon x _) = Just (((3*sqrt 3)/2) * x^2)


-- function for perimeter
perimeter :: Shape -> Maybe Double
perimeter (Circle r) = Just (2 * pi * r)
perimeter (Rectangle l w 0) = Just (2*(l+w))
perimeter (Triangle a b c 0) = Just (a + b + c)
perimeter (Pentagon x _) = Just (x * 5)
perimeter (Hexagon x _) = Just (x * 6)

-- volume function
volume :: Shape -> Maybe Double
volume (Circle r) = Just ((4/3) * pi * (r^3))
volume (Rectangle l w h) = Just (l * w * h)
volume (Triangle a b c h) = case area (Triangle a b c 0) of
    Just a -> Just (a * h)
volume (Pentagon x h) = case area (Pentagon x 0) of
    Just a -> Just (a * h)
volume (Hexagon x h) = case area (Hexagon x h) of
    Just a -> Just (a * h)

-- recursive functions for total
totalArea :: [Shape] -> Double
totalArea [] = 0
totalArea (x:xs) = case area x of Just a -> a + totalArea xs

totalPerimeter :: [Shape] -> Double
totalPerimeter [] = 0
totalPerimeter (x:xs) = case perimeter x of Just a -> a + totalPerimeter xs

totalVolume :: [Shape] -> Double
totalVolume [] = 0
totalVolume (x:xs) = case volume x of Just a -> a + totalVolume xs

-- Create a simple main program
main :: IO ()
main = do
    -- Hardcoded list of shapes
    let shapes = [Circle 10, Rectangle 5 8 0, Triangle 3 4 5 0, Pentagon 5 0, Hexagon 6 0]
    
    putStrLn "\nPlease add the length for a rectangular prism: "
    rect <- getLine
    putStrLn "\nPlease add the length of the triangular prism: "
    tri <- getLine
    putStrLn "\nPlease add the length of the pentagonal prism: "
    pent <- getLine
    putStrLn "\nPlease add the length of the hexagonal prism: "
    hex <- getLine

    let shapes' = [Circle 10, Rectangle 5 8 (read rect), Triangle 3 4 5 (read tri), Pentagon 5 (read pent), Hexagon 6 (read hex)]

    -- Print out the area and perimeter of each shape
    putStrLn "\nShape Areas:"
   -- go through your list and print all of them with one line of code.
    mapM_ (\x -> print (area x)) shapes

    putStrLn "\nShape Perimeters:"
   -- go through your list and print all of them with one line of code.=
    mapM_ (\x -> print (perimeter x)) shapes


    putStrLn "\nShape Volumes:"
   -- go through your list and print all of them with one line of code.
    mapM_ (\x -> print (volume x)) shapes'

    
    -- Print out the total area and perimeter of all shapes
    putStrLn "\nTotal Area of All Shapes:"
    print (totalArea shapes)
    
    putStrLn "\nTotal Perimeter of All Shapes:"
    print (totalPerimeter shapes)

    putStrLn "\nTotal volume of All Shapes:"
    print (totalVolume shapes)