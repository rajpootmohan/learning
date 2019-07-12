def jumpingOnClouds(c)
  i=0;count=0;n=c.length;
  while i<n do
    if (((c[i+2] == 1) && (i+2 < n)) || i+2 == n )
      i = i + 1;
    else 
      i = i + 2;
    end
    count = count + 1;
    puts "i: #{i}, count: #{count}";
  end
  return count-1;
end

a = jumpingOnClouds [0, 1, 0, 0, 0, 1, 0]
puts a