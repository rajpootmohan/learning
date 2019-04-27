  array =     [ [1, 10], [2, 6], [3, 5], [7, 9] ]
  array.sort!{|a,b| a.first<=>b.first}
  output_array = []
  i=0
  n=array.length
  while i<n
	elem1 = array[i]
	elem2 = array[i+1]
	if !elem2
	  output_array << elem1
	  break
	end
	if elem2.first > elem1.last
	  output_array<< elem1 
	  i+=1
	else
	  while elem2 && elem2.first <= elem1.last && i<n-1
		elem1 = [elem1.first, [elem1.last, elem2.last].max]
		i+=1
		elem2 = array[i+1]
	  end
	  output_array<<elem1
	  i+=1
	end
  end
  print output_array.inspect
