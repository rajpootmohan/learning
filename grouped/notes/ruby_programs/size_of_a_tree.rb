class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
end

def size_of_a_tree root
	return 0 if root == nil 
	return (1 + size_of_a_tree(root.left) + size_of_a_tree(root.right))
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


puts size_of_a_tree root

