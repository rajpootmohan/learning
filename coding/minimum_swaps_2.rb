def newYearChaos(arr)
  i = arr.length-1
  count = 0; tag=0;
  while  i >= 0 do
  	if arr[i] == i+1
  		# do nothing
  	elsif arr[i-1] == i+1
  		count = count + 1;
  		temp = arr[i-1];
  		arr[i-1] = arr[i];
  		arr[i] = temp;
  	elsif arr[i-2] == i+1	
  		count = count + 2;
  		temp = arr[i-2];
  		arr[i-2] = arr[i-1];
  		arr[i-1] = arr[i];
  		arr[i] = temp;
  	else
  		tag = 1;
  		break;
  	end
  	i = i - 1;
  end
  puts "Too chaotic" if tag == 1
  puts count if tag == 0
end

newYearChaos [2,5,1,3,4]
#[1, 2, 5, 3, 7, 8, 6, 4] #[2,1,5,3,4]
