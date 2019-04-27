class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
end

def is_identical? root1, root2
	return true if root1 == nil && root2 == nil
	if (root1 && root2) 
		a = (root1.value == root2.value)
		b = is_identical? root1.left, root2.left
		c = is_identical? root1.right, root2.right
		(a && b && c) ? true : false
	end
end

root = Tree.new 1
root.left = Tree.new 2
root.right = Tree.new 3
root.left.left = Tree.new 4
root.left.right = Tree.new 5
root.right.left = Tree.new 6
root.right.right = Tree.new 7
root.right.left.right = Tree.new 8
root.right.right.right = Tree.new 9


puts is_identical? root,root.right.right.right

