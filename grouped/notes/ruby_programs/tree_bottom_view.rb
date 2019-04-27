class Tree
	
	attr_accessor :value, :left, :right

	def initialize(value)
		@value = value
	end

	def bottom_view root, h
		return if root==nil
		ar = []
		hd = 0
		ar.push [root, 0]
		while ar.size > 0
			popped_ar = ar.pop;
			hd = popped_ar.last
			h[hd] = popped_ar.first.value
			ar.push [popped_ar.first.left, hd-1] if popped_ar.first.left
			ar.push [popped_ar.first.right, hd+1] if popped_ar.first.right
		end
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

h={}
root.bottom_view root, h
puts h.inspect

