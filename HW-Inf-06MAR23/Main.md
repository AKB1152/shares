# HW-INF-06MAR23

## PG89/2

→ Postorderdurchlauf: LRN ([→ Wikipedia](https://en.wikipedia.org/wiki/Tree_traversal#Post-order,_LRN))
  1. Linker Teilbaum
  2. Rechter Teilbaum
  3. Knoten Selbst

````ini
[a1] 
[a2] 
call 
[a3] 
[a4] 
care
[a5]
[a6] 
cave 
cat 
car
[a7] 
[a8] 
[a9] 
cook 
coin
[aA]
[aB]
crow
[aC]
cube
crab 
clip 
````

## Preorder

```java
public class Tree<T> implements Iterable<T> {

	/* […] */
    
    public void printStructure () {
		System.out.println (this);
    }
	
    @Override 
    public String toString() {
        return root.dataString();
    }
  
	/* […] */
    
    public static class Node<T> {
        
		/* […] */
        
        String dataString () {
            return dataString(0);
        }

        String dataString (int tabs) {
            return String.format("val: %s\n%s%s", data, 
            ((left () != null)? 
                    String.format("%s→ %s", "\t".repeat(tabs+1),  left().dataString(tabs+1)) :
                    ""), 
            ((right () != null)?
                    String.format("%s→ %s", "\t".repeat(tabs+1), right().dataString(tabs+1)) :
                    ""));
		}

		/* […] */
    }
}
```