{-
CSCE 314 Inclass assignment 2/20/2025
-}


import Data.Maybe (fromMaybe)

-- | An algebraic data type (ADT) for our simple expression language.
data Expr
    = Const Int           -- ^ Integer constant
    | Add Expr Expr       -- ^ Addition
    | Mul Expr Expr       -- ^ Multiplication
    | Var String          -- ^ Variable
    | Lam String Expr     -- ^ Lambda abstraction (function)
    | App Expr Expr       -- ^ Function application
    deriving (Show)

-- | ADT for values produced by evaluation.
data Value
    = NumVal Int                -- ^ Numeric value
    | FunVal Env String Expr    -- ^ A function value (closure)

-- | Custom show instance for pretty printing values.
instance Show Value where
    show (NumVal n)      = show n
    show (FunVal _ x b)  = "<fun " ++ x ++ " -> " ++ show b ++ ">"

-- | Environment mapping variable names to values.
type Env = [(String, Value)]

-- | The evaluator: recursively evaluates an expression within an environment.
eval :: Env -> Expr -> Value
eval _   (Const n)     = NumVal n
eval env (Add e1 e2)   =
    let NumVal n1 = eval env e1
        NumVal n2 = eval env e2
    in NumVal (n1 + n2)
eval env (Mul e1 e2)   =
    let NumVal n1 = eval env e1
        NumVal n2 = eval env e2
    in NumVal (n1 * n2)
eval env (Var x)       =
    fromMaybe (error ("Unbound variable: " ++ x)) (lookup x env)
eval env (Lam x body)  = FunVal env x body
eval env (App e1 e2)   =
    let fun = eval env e1
        arg = eval env e2
    in case fun of
         FunVal env' x body -> eval ((x, arg) : env') body
         _ -> error "Attempting to apply a non-function!"

-- | A type class for polymorphic pretty printing.
class Pretty a where
    pretty :: a -> String

instance Pretty Expr where
    pretty (Const n)    = show n
    pretty (Add e1 e2)  = "(" ++ pretty e1 ++ " + " ++ pretty e2 ++ ")"
    pretty (Mul e1 e2)  = "(" ++ pretty e1 ++ " * " ++ pretty e2 ++ ")"
    pretty (Var x)      = x
    pretty (Lam x e)    = "(\\" ++ x ++ " -> " ++ pretty e ++ ")"
    pretty (App e1 e2)  = "(" ++ pretty e1 ++ " " ++ pretty e2 ++ ")"

instance Pretty Value where
    pretty (NumVal n)      = show n
    pretty (FunVal _ x e)  = "(\\" ++ x ++ " -> " ++ pretty e ++ ")"

-- | A demo function that creates and evaluates several expressions.
demo :: IO ()
demo = do
    let expr1 = Add (Const 3) (Mul (Const 4) (Const 5))
    putStrLn $ "Expression 1: " ++ pretty expr1
    putStrLn $ "Evaluated: " ++ pretty (eval [] expr1)

    let expr2 = App (Lam "x" (Add (Var "x") (Const 1))) (Const 5)
    putStrLn $ "\nExpression 2: " ++ pretty expr2
    putStrLn $ "Evaluated: " ++ pretty (eval [] expr2)

    let expr3 = App (App (Lam "x" (Lam "y" (Add (Var "x") (Var "y")))) (Const 10)) (Const 20)
    putStrLn $ "\nExpression 3: " ++ pretty expr3
    putStrLn $ "Evaluated: " ++ pretty (eval [] expr3)

-- | A higher-order transformation function that recursively applies a given function
-- to every node in an expression tree.
transform :: (Expr -> Expr) -> Expr -> Expr
transform f expr = case expr of
    Const _    -> f expr
    Var _      -> f expr
    Add e1 e2  -> f (Add (transform f e1) (transform f e2))
    Mul e1 e2  -> f (Mul (transform f e1) (transform f e2))
    Lam x body -> f (Lam x (transform f body))
    App e1 e2  -> f (App (transform f e1) (transform f e2))

-- | Example transformation using a lambda function: increment every constant by 1.
incrementConstants :: Expr -> Expr
incrementConstants = transform (\e ->
    case e of
      Const n -> Const (n + 1)
      _       -> e)

-- | Demonstrate the transformation.
demoTransform :: IO ()
demoTransform = do
    let expr = Add (Const 1) (Mul (Const 2) (Const 3))
    putStrLn $ "\nOriginal Expression: " ++ pretty expr
    let transformedExpr = incrementConstants expr
    putStrLn $ "After Incrementing Constants: " ++ pretty transformedExpr
    putStrLn $ "Evaluated: " ++ pretty (eval [] transformedExpr)

-- | Main function that runs the demos.
main :: IO ()
main = do
    putStrLn "Functional Programming Debugging Assignment\n"
    demo
    demoTransform