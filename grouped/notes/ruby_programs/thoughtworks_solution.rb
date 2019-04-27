class ThoughtWorks

	attr_accessor :output_array, :fixed_hash, :calculated_hash
	
	def initialize
		@output_array = []
		@fixed_hash = {}
		@calculated_hash = {}
	end

	def get_roman_value roman_char
		case roman_char
		when 'I';	return 1
		when 'V';	return 5
		when 'X';	return 10
		when 'L';	return 50
		when 'C';	return 100
		when 'D';	return 500
		when 'M';	return 1000
		else;		return 0
		end
	end

	def roman_to_decimal roman
		decimal = multiple = current = last_add = last_sub = 0
		digits = []
		roman.each_char{|char| digits.push get_roman_value(char) }
		while digits.length > 0
			current = digits.pop
			return -1 if multiple > 2
			if(digits.length > 0 && current > digits.last)
				return -1 if (multiple > 0 || last_add == digits.last)
				return -1 if (digits.last == 5 || digits.last == 50 || digits.last == 500)
				return -1 if ((current == 5 || current == 10) && digits.last != 1)
				return -1 if ((current == 50 || current == 100) && digits.last != 10)
				return -1 if ((current == 500 || current == 1000) && digits.last != 100)
				decimal += (current-digits.last);
				last_sub = digits.pop; 
			elsif(digits.length > 0 && current == digits.last)
				return -1 if (current == 5 || current == 50 || current == 500)
				decimal += current
				multiple += 1
			else
				return -1 if last_sub == current
				decimal += current
				multiple = 0
				last_add = current
			end	
		end
		decimal
	end
	
	def save_to_output_file output_file
		file = File.open(output_file, "w")
		@output_array.each do |line|
			file.puts line
		end
		file.close
	end
	

	def evaluate_and_save_to_output_file input_file, output_file
		error_message = "I have no idea what you are talking about"
		File.open(input_file).each do |line|
			roman_decimal = 0.0	
			output_line = ""
			roman_string = ""
			is_valid = true
			line.chomp!
			line_array = line.split(" ")
			line_length = line_array.length
			if line_length < 3
				@output_array.push(error_message)
				next
			end
			if (line_array[1] == "is" && line_array.last != "?")
				@fixed_hash[line_array[0]] = line_array[2]
			elsif (line_array.last == "?")
				if (!(@fixed_hash.key? line_array[line_length-2]) && !(@calculated_hash.key? line_array[line_length-2])) 
					@output_array.push(error_message)
					next
				end
				index = 0
				while index < line_length
					break unless is_valid 
					if(!(@fixed_hash.key? line_array[index]) && !(@calculated_hash.key? line_array[index]))
						index += 1
						next
					end
					if @fixed_hash.key? line_array[index]
						roman_string.concat(@fixed_hash[line_array[index]])
						output_line.concat("#{line_array[index]} ")
					elsif @calculated_hash.key? line_array[index]
						roman_decimal = roman_to_decimal roman_string
						if roman_decimal == -1
							@output_array.push(error_message)
							is_valid = false
							next
						end	
						output_line.concat("is ")
						output_line.concat((roman_decimal*@calculated_hash[line_array[index]]).to_i.to_s)
						output_line.concat(" Credits")
						@output_array.push(output_line)
					end
					index += 1	
				end
				if (roman_decimal == 0 && is_valid)
					roman_decimal = roman_to_decimal roman_string
					if roman_decimal == -1
						@output_array.push(error_message)
						next
					end
					output_line.concat("is ")
					output_line.concat(roman_decimal.to_i.to_s)
					@output_array.push(output_line)
				end
			else
				roman_string = ""
				index = 0
				while @fixed_hash.key? line_array[index]
					roman_string.concat(@fixed_hash[line_array[index]])
					index += 1
				end
				roman_decimal = roman_to_decimal roman_string
				@calculated_hash[line_array[index]] = (line_array[index+2].to_f / roman_decimal.to_f) if line_array[index]
			end	
		end
		save_to_output_file output_file
	end
end

tw = ThoughtWorks.new
tw.evaluate_and_save_to_output_file "input.txt", "output.txt"


