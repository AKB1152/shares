import org.jetbrains.annotations.NotNull;

import java.lang.Object;
import java.lang.Object;
import java.util.*;

public class Tree<T> implements Iterable<T> {

	/** used to check if (arg1 < arg2) **/
	private final LessThan<T> lambda;
	/** root node **/
	private Node<T> root;

	/** Standard Lambda-Expressions for different data-types*/
	@SuppressWarnings("unused") public static final LessThan<String>    LAMBDA_STRING     = (t1, t2)->t1.compareToIgnoreCase(t2)<0;
	@SuppressWarnings("unused") public static final LessThan<Integer>   LAMBDA_INTEGER    = (i1, i2)->i1<i2;
	@SuppressWarnings("unused") public static final LessThan<Double>    LAMBDA_DOUBLE     = (d1, d2)->d1<d2;
	@SuppressWarnings("unused") public static final LessThan<Float>     LAMBDA_FLOAT      = (d1, d2)->d1<d2;
	@SuppressWarnings("unused") public static final LessThan<Character> LAMBDA_CHARACTER  = (c1, c2)->c1<c2, LAMBDA_CHAR = LAMBDA_CHARACTER;

	@FunctionalInterface
	public static interface LessThan<T> {
		boolean lessThan (T t1, T t2);
	}

	public Tree (@NotNull LessThan<T> lt) {
		this.lambda = lt;
	}

	/**
	 * adds a new Node to the Tree
	 * @param data the data to be stored
	 */
	public void add (@NotNull  T data) {
		if (root != null) root.add(data);
		else root = new Node<> (data, lambda);
	}

	/**
	 * @return String representation of this Tree
	 */
	public String toString() {
		return root.dataString();
	}

	/**
	 * @return root node
	 */
	protected Node<T> root() {
		return root;
	}

	@SuppressWarnings("unused")
	/**
	 * @see Node#search(java.lang.Object)
	 */
	public int search (T data) {
		return root.search(data);
	}

	@SuppressWarnings("unused")
	/**
	 * checks whether the tree contains certain data
	 * @parameter data the data to be searched for
	 */
	public boolean contains (T data) {
		return root.search(data) != -1;
	}

	/**
	 * @return the size of the tree
	 */
	public int size() {
		return root.toInOrder().length;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 * @return an Iterator.
	 */
	@NotNull
	@Override
	public Iterator<T> iterator() {
		return new Iterator<>() {

			private int current = 0;
			//
			private final Node<T>[] list = root().toPreOrder();

			/**
			 * Returns {@code true} if the iteration has more elements.
			 * (In other words, returns {@code true} if {@link #next} would
			 * return an element rather than throwing an exception.)
			 *
			 * @return {@code true} if the iteration has more elements
			 */
			@Override
			public boolean hasNext() {
				return (current < list.length);
			}

			/**
			 * Returns the next element in the iteration.
			 *
			 * @return the next element in the iteration
			 * @throws NoSuchElementException if the iteration has no more elements
			 */
			@Override
			public T next() {
				return list[current++].data();
			}
		};
	}



	public static class Node<T> {

		private Node<T> parent;
		private Node<T> child_left, child_right;
		private final LessThan<T> lt;

		private final T data;

		public Node (T data, LessThan<T> lt) {
			this.data = data;
			this.lt = lt;
		}

		public T data() {
			return data;
		}
		public Node<T> parent() {
			return parent;
		}
		public Node<T> left () {
			return child_left;
		}
		public Node<T> right() {
			return child_right;
		}
		public Node<T> root() {
			return (parent != null)? parent().root() : this;
		}

		public String toString() {
			return "(root): "+ id();
		}

		public String id() {
			return (this == root())? "r" : parent().id() + ((parent.left() == this)? "0" : "1");
		}

		public void add (T data) {
			if (this.data() == data);
			else if (lt.lessThan (data, this.data()))
				if (left() == null) left(new Node<>(data, lt));
				else left().add(data);
			else
			if (right() == null) right(new Node<>(data, lt));
			else right().add(data);
		}

		private void left(Node<T> child_left) {
			this.child_left = child_left;
			this.child_left.parent(this);
		}
		private void right(Node<T> child_right) {
			this.child_right = child_right;
			this.child_right.parent(this) ;
		}
		private void parent(Node<T> parent) {
			this.parent = parent;
		}

		String dataString () {
			return dataString(0);
		}

		private String tabs (int tabs) {
			return "\t".repeat(tabs);
		}

		public Node<T> resolve (String id) {
			return (Objects.equals(id, "r")) ?
					root() :
					(id.length() == 0)?
							this :
							id.startsWith ("0")?
									(left () != null)? left ().resolve(id.substring(1)) : null :
									(right() != null)? right().resolve(id.substring(1)) : null ;
		}

		public int search (T data) {
			return (data == null)? -1 : (data == this.data)? Integer.parseInt(id(), 2) : (lt.lessThan(data, this.data()))? (left () != null)? left ().search(data) : -1 : (right() != null)? right().search(data) : -1 ;
		}

		String dataString (int tabs) {
			return String.format("val: %s\n%s%s", data,  ((left () != null)? String.format("%s→ %s", tabs(tabs+1), left().dataString(tabs+1)) : ""),  ((right () != null)? String.format("%s→ %s", tabs(tabs+1), right().dataString(tabs+1)) : ""));
		}

		@SuppressWarnings({"unchecked", "rawtype"})
		public Node<T>[] toInOrder() {
			HashMap<Integer, Node<T>> h = new HashMap<>();

			//Inorder
			if (left () != null) {
				var l = left ().toInOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}

			// how 'bout a lil threat to the compiler?
			else;

			h.put(h.size(), this);

			if (right() != null) {
				var l = right().toInOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}

			return (Node<T>[]) h.values().toArray(new Node<?>[]{this});
		}

		@SuppressWarnings({"unchecked", "rawtype"})
		public Node<T>[] toPreOrder() {
			HashMap<Integer, Node<T>> h = new HashMap<>();

			h.put(0, this);

			//Inorder
			if (left () != null) {
				var l = left ().toPreOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}
			else;
			// how 'bout a lil threat to the compiler?
			if (right() != null) {
				var l = right().toPreOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}

			return (Node<T>[]) h.values().toArray(new Node<?>[]{this});
		}

		@SuppressWarnings({"unchecked", "rawtype"})
		public Node<T>[] toPostOrder() {
			HashMap<Integer, Node<T>> h = new HashMap<>();

			//Inorder
			if (left () != null) {
				var l = left ().toPostOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}

			// how 'bout a lil threat to the compiler?
			else;

			if (right() != null) {
				var l = right().toPostOrder();
				for (Node<T> n : l)
					h.put(h.size(), n);
			}

			h.put(h.size(), this);

			return (Node<T>[]) h.values().toArray(new Node<?>[]{this});
		}

	}
}
