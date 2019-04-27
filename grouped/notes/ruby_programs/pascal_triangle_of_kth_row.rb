k=3
i=1
array = [1]
while i<=k 
	j=1
	while j < i
		array[j] = array[j].to_i + array[j-1].to_i
		j+=1 
	end
	array << 1
	print array
	i+=1
end
