class Tree
	
	attr_accessor :value, :left, :right

	def initialize(value)
		@value = value
	end

	def inorder root
		return if root == nil 
		inorder root.left
		print " #{root.value}"
		inorder root.right
	end

	def preorder root
		return if root == nil 
		print " #{root.value}"
		inorder root.left
		inorder root.right
	end

	def postorder root
		return if root == nil 
		inorder root.left
		inorder root.right
		print " #{root.value}"
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

puts root.preorder root
puts root.inorder root
puts root.postorder root

