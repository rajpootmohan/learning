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
end

def mirror_tree root
	return if root==nil
	mirror_tree root.left
	mirror_tree root.right
	temp = root.left
	root.left = root.right
	root.right = temp
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

root.inorder root
puts ""
mirror_tree root
root.inorder root

# 4 2 5 1 6 8 3 7 9
#  9 7 3 8 6 1 5 2 4[Finished in 0.1s]