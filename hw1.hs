import System.IO
import Text.Printf


{-Ayad Masud
  2/6/25
  (by entering your name and date you certify this is your
  own work and not that of any other person or service.
  Comment your functions for clarity.-}

{-Here the image size is defined as a 600 x 600 pixel image-}
-- Define image size
mySize :: Int
mySize = 600

{-Here I have defined 2 rgb colors for a given pixel. -}
-- Define colors  
whiteR, whiteG, whiteB :: Int
whiteR = 150
whiteG = 150
whiteB = 150

brownR, brownG, brownB :: Int
brownR = 150
brownG = 75
brownB = 0

greenR, greenG, greenB :: Int
greenR = 0
greenG = 255
greenB = 0

redR, redG, redB :: Int
redR = 255
redG = 0
redB = 0

blueR, blueG, blueB :: Int 
blueR = 0
blueG = 0
blueB = 255

orangeR, orangeG, orangeB :: Int
orangeR = 255
orangeG = 165
orangeB = 0

blackR, blackG, blackB :: Int
blackR = 0
blackG = 0
blackB = 0


{-define other colors here, or create them as you move, like gradients-}

generateImage :: [[(Int, Int, Int)]]
generateImage = [[pixelColor i j | j <- [0..mySize-1]] | i <- [0..mySize-1]]
-- where allows you to define varibles or functions that are only allowed in the scope of the function where it is declared.
-- layering matters, so order of the if statements matter.
   where
    pixelColor i j
        | (i-175)^2 + (j-175)^2 < 100^2 && i < 150 = (blackR, blackG, blackB) -- semi circle
        | (i-150)^2 + (j-150)^2 < 100^2 = (orangeR, orangeG, orangeB) -- sun, circle
        | j > 162 && j < 262 && i > 450 && i < 550 = (greenR, greenG, greenB) -- window, square
        | j > 312 && j < 412 && i > 450 && i < 550 = (redR, redG, redB) -- window, square
        | j > 150 && j < 450 && i > 400 && i < 600 = (brownR, brownG, brownB) -- house, rectangle
        | j > 150 && j < 450 && i > 200 && i < 400 && i > 400 - (j - 150) `div` 2 && i > 400 - (450 - j) `div` 2 = (blueR, blueG, blueB) -- roof, triangle
        | otherwise = (whiteR, whiteG, whiteB)



 --Update this. use at least 5 different colors and 4 different shapes.

 

-- Write the PPM file
writePPM :: FilePath -> [[(Int, Int, Int)]] -> IO ()
writePPM filename pixels = do
    let header = "P3\n" ++ show mySize ++ " " ++ show mySize ++ "\n255\n"
        body = unlines [unwords [printf "%d %d %d" r g b | (r, g, b) <- row] | row <- pixels]
    writeFile filename (header ++ body)

main :: IO ()
main = do
    let image = generateImage
    writePPM "output.ppm" image
    putStrLn "PPM file 'output.ppm' created successfully."