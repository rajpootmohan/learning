class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
	def inorder root
		return if root==nil
		inorder root.left
		print " #{root.value}"
		inorder root.right
	end
end

def diameter root
	return 0 if root==nil
	lheight = height root.left
	rheight = height root.right

	ldiameter = diameter root.left
	rdiameter = diameter root.right

	return [1+lheight+rheight, ldiameter, rdiameter].max

end

def height node
	return 0 if node==nil
	return (1+[height(node.left), height(node.right)].max)
end



root = Tree.new 50
root.left = Tree.new 7
root.right = Tree.new 2
root.left.left = Tree.new 3
root.left.right = Tree.new 5
root.right.left = Tree.new 1
root.right.right = Tree.new 30

puts diameter root