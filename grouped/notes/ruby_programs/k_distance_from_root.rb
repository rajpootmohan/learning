class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
end

def inorder root
	return if root==nil
	inorder root.left
	print " #{root.value}"
	inorder root.right
end

def k_distance_from_root root, k
	return if root==nil
	puts " #{root.value}" if k==0
	k_distance_from_root root.left, k-1
	k_distance_from_root root.right, k-1
end


root = Tree.new 50
root.left = Tree.new 7
root.right = Tree.new 2
# root.left.left = Tree.new 3
root.left.right = Tree.new 5
root.right.left = Tree.new 1
root.right.right = Tree.new 30
inorder root; puts;
k_distance_from_root root,2
inorder root