class Tree
	attr_accessor :value, :left, :right
	def initialize(value)
		@value = value
	end
end

def children_sum_property root
	ldata = rdata = 0
	return true if root==nil || (root.left == nil && root.right == nil)
	ldata = root.left.value if root.left != nil
	rdata = root.right.value if root.right != nil
	if ((root.value == ldata+rdata) && (children_sum_property root.left) && (children_sum_property root.right))	
		return true
	else
		return false
	end
end


root = Tree.new 10
root.left = Tree.new 8
root.right = Tree.new 2
root.left.left = Tree.new 3
root.left.right = Tree.new 5
root.right.left = Tree.new 2
# root.left.right.right = Tree.new 14

puts children_sum_property root
