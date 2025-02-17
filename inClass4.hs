{-
Ayad Masud
2/14/25
-}

-- Define an ADT for arithmetic expressions
data Expr 
    = Lit Int              -- Integer literals
    | Add Expr Expr        -- Addition
    | Mul Expr Expr        -- Multiplication
    | Pwr Expr Expr                       -- TODO: add an ADT for Power (Pwr)
    | Var String           -- Variable lookup
    deriving (Show, Read)

-- Function to evaluate expressions
eval :: Expr -> [(String, Int)] -> Maybe Int
eval (Lit n) _ = Just n  -- Integer literals evaluate to themselves
eval (Add e1 e2) env = do
    v1 <- eval e1 env
    v2 <- eval e2 env
    return (v1 + v2)
eval (Mul e1 e2) env = do
    v1 <- eval e1 env
    v2 <- eval e2 env
    return (v1 * v2)  -- TODO: Complete this

eval (Pwr e1 e2) env = do
    v1 <- eval e1 env
    v2 <- eval e2 env
    return (v1 ^ v2)                         -- TODO: add an eval of Pwr
eval (Var x) env = do -- TODO: Lookup variable in env
      lookup x env

-- Sample expressions (Do not modify)
expr1 :: Expr
expr1 = Add (Lit 3) (Mul (Lit 2) (Var "x"))  -- 3 + (2 * x)

expr2 :: Expr
expr2 = Mul (Var "y") (Add (Var "x") (Lit 5))  -- y * (x + 5)

--TODO add expr3 for evaluating  10 + (2 ^ x)
expr3 :: Expr
expr3 = Add (Lit 10) (Pwr (Lit 2) (Var "x"))

-- Test cases
main :: IO ()
main = do
    let env = [("x", 4), ("y", 3)]
    
    putStrLn "Evaluating expr1: 3 + (2 * x)"
    print (eval expr1 env)  -- Expected: Just 11 (3 + (2 * 4))

    putStrLn "Evaluating expr2: y * (x + 5)"
    print (eval expr2 env)  -- Expected: Just 27 (3 * (4 + 5))

    putStrLn "Evaluating expr with undefined variable 'z':"
    print (eval (Var "z") env)  -- Expected: Nothing

    -- ToDo: add env2 so that x will = 6
    -- then print out "Evaluating expr3: 10 + ( 2 ^ x)"
    -- then print out the evaluation of your equation -- expect 74
    let env2 = [("x", 6)]
    putStrLn "Evaluating expr3: 10 + (2 ^ x)"
    print (eval expr3 env2)