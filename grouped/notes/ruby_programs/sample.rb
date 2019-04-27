# print all permutations of "abc"

def permutation(str, l, r)
	if l==r 
		puts str 
	else
		i=l
		while i <= r 
			str[i],str[l] = str[l],str[i]
			permutation str, l+1, r
			str[i],str[l] = str[l],str[i]
			i +=1
		end
	end	
end

permutation "abcd", 0, 3
