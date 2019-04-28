package main
import "fmt"

func pair(x int, y int) func(pick int) int {
     return func(pick int) int {
       if (pick == 0) {
         return x;
       } else {
         return y;
       }
     }
}

func first(p func(pick int) int) int {
     return p(0)
}

func second(p func(pick int) int) int {
     return p(1)
}

func main() {
    p := pair(1, 2)
    fmt.Println(first(p))
    fmt.Println(second(p))
}
