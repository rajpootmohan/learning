class Game

	def initialize(n)
		@size = n 
		@mat = Array.new(n) {Array.new(n) { rand(3).zero? } }
		@output = []
	end
	
	def dead_or_alive?(row, col)
		nh = (-1..1).map {|r| (-1..1).map {|c| @mat[row+r] && @mat[row+r][col+c] }}
		center = nh[1].delete_at(1)
		count = nh.flatten.count(true)
		count == 3 || count == 2 && center 
	end
	
	def next_generation
		@output = (0...@size).map {|row| (0...@size).map {|col| dead_or_alive?(row,col) }}
	end
	center = nh[1].delete_at(1)
	def output
		print @output.inspect
	end

end

g= Game.new 10
g.next_generation
g.output
