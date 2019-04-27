class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
end

def root_to_leaf root, ar, len
	return if root==nil
	ar[len] = root.value
	len +=1
	print ar if(root.left == nil && root.right == nil)
	root_to_leaf root.left, ar, len
	root_to_leaf root.right, ar,len
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

ar = []
root_to_leaf root, ar, 0