{-
deepseq example
-}
import Control.DeepSeq(deepseq)

-- a function that simulates expensive computation
expFun :: Int -> Int
expFun x = x * 2

-- lazy evaluation
lazyExample :: [Int]
lazyExample = map expFun [1..5]

-- using seq for WHNF 
seqExample :: Int
seqExample = 
    let nums = map expFun [1..5]
    in nums `seq` length nums

deepseqExample :: Int
deepseqExample = 
    let nums = map expFun [1..5]
    in nums `deepseq` length nums -- fully evaluates all elements


main :: IO ()
main = do
    putStrLn "Lazy evaluation (thunks remain until needed)"
    print lazyExample -- only computed when printed

    putStrLn "\nUsing seq (forcing list strcuture but not elements)"
    print seqExample -- shows list is created 

    putStrLn "\nUsing deepseq"
    print deepseqExample -- fully evaluates list before printing list